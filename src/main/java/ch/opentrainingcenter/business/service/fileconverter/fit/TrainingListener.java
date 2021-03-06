package ch.opentrainingcenter.business.service.fileconverter.fit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.garmin.fit.LapMesg;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.SessionMesg;

import ch.opentrainingcenter.business.DistanceHelper;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;
import ch.opentrainingcenter.business.domain.HeartRate;
import ch.opentrainingcenter.business.domain.LapInfo;
import ch.opentrainingcenter.business.domain.RunData;
import ch.opentrainingcenter.business.domain.Sport;
import ch.opentrainingcenter.business.domain.Tracktrainingproperty;
import ch.opentrainingcenter.business.domain.Training;

public class TrainingListener implements MesgListener {

    private static final String RECORD = "record"; //$NON-NLS-1$
    private static final String SESSION = "session"; //$NON-NLS-1$
    private static final String LAP = "lap"; //$NON-NLS-1$

    private final List<Tracktrainingproperty> trackpoints = new ArrayList<>();
    private final List<LapInfo> lapInfos = new ArrayList<>();
    private SessionMesg session;
    private LapInfo lap = null;
    private int error = 0;
    private int valid = 0;

    @Override
    public void onMesg(final Mesg mesg) {
        final String messageName = mesg.getName();
        if (RECORD.equals(messageName)) {
            trackpoints.add(convertTrackPoint(new RecordMesg(mesg)));
        } else if (SESSION.equals(messageName)) {
            session = new SessionMesg(mesg);
        } else if (LAP.equals(messageName)) {
            final LapMesg lapMesg = new LapMesg(mesg);
            final int end = lapMesg.getTotalDistance().intValue();
            final int timeInSekunden = lapMesg.getTotalTimerTime().intValue();
            final int timeInMillis = lapMesg.getTotalTimerTime().intValue() * 1000;
            final int heartRate = lapMesg.getAvgHeartRate() != null ? lapMesg.getAvgHeartRate().shortValue() : 0;
            final String pace = DistanceHelper.calculatePace(end, timeInSekunden, Sport.RUNNING);
            final String speed = DistanceHelper.calculatePace(end, timeInSekunden, Sport.BIKING);
            if (lap == null) {
                // erste Runde
                lap = CommonTransferFactory.createLapInfo(0, 0, end, timeInMillis, heartRate, pace, speed);
            } else {
                final int lapNeu = lap.getLap() + 1;
                final int startNeu = lap.getEnd();
                lap = CommonTransferFactory.createLapInfo(lapNeu, startNeu, startNeu + end, timeInMillis, heartRate, pace, speed);
            }
            lapInfos.add(lap);
        }
        // logMessage(mesg);
    }

    private Tracktrainingproperty convertTrackPoint(final RecordMesg record) {
        final double distance = record.getDistance();
        final Integer positionLong = record.getPositionLong();
        final Integer positionLat = record.getPositionLat();
        Double longDms = null;
        Double latDms = null;
        if (positionLong != null || positionLat != null) {
            longDms = ConvertGarminSemicircles.convertSemicircleToDms(positionLong);
            latDms = ConvertGarminSemicircles.convertSemicircleToDms(positionLat);
            valid++;
        } else {
            error++;
        }

        final int heartbeat = record.getHeartRate() != null ? record.getHeartRate() : -1;
        final int altitude = record.getAltitude() != null ? record.getAltitude().intValue() : -1;
        final long time = record.getTimestamp().getDate().getTime();
        return CommonTransferFactory.createTrackPointProperty(distance, heartbeat, altitude, time, 0, longDms, latDms);
    }

    public Training getTraining() {
        final long dateOfStart = session.getStartTime().getDate().getTime();
        final long timeInSeconds = session.getTotalTimerTime().longValue();
        final long distanceInMeter = session.getTotalDistance().longValue();
        final double maxSpeed = session.getMaxSpeed();
        final RunData runData = new RunData(new Date(dateOfStart), timeInSeconds, distanceInMeter, maxSpeed);
        final int average = session.getAvgHeartRate() != null ? session.getAvgHeartRate().intValue() : -1;
        final int max = session.getMaxHeartRate() != null ? session.getMaxHeartRate().intValue() : -1;
        final HeartRate heart = new HeartRate(average, max);
        final Training training = CommonTransferFactory.createTraining(runData, heart);
        training.setTrackPoints(trackpoints);
        training.setDownMeter(session.getTotalDescent() != null ? session.getTotalDescent() : 0);
        training.setUpMeter(session.getTotalAscent() != null ? session.getTotalAscent() : 0);
        final com.garmin.fit.Sport sport = session.getSport();
        if (com.garmin.fit.Sport.RUNNING.equals(sport)) {
            training.setSport(Sport.RUNNING);
        } else if (com.garmin.fit.Sport.CYCLING.equals(sport)) {
            training.setSport(Sport.BIKING);
        } else {
            training.setSport(Sport.OTHER);
        }
        final Float totalTrainingEffect = session.getTotalTrainingEffect();
        if (totalTrainingEffect != null) {
            training.setTrainingEffect((int) (10 * totalTrainingEffect.floatValue()));
        }
        training.setLapInfos(lapInfos);
        for (final LapInfo lapInfo : lapInfos) {
            lapInfo.setTraining(training);
        }
        final int total = error + valid;
        final int fehlerInProzent = (int) (100 * (error / (float) total));
        training.setGeoQuality(fehlerInProzent);
        // logger.info(String.format("Qualität der Geodaten: '%s' [prozent]
        // fehlerhafte Geodaten", fehlerInProzent)); //$NON-NLS-1$
        return training;
    }
}
