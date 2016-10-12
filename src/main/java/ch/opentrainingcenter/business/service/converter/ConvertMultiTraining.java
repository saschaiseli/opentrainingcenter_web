package ch.opentrainingcenter.business.service.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.opentrainingcenter.business.domain.Training;
import ch.opentrainingcenter.gui.model.GMultiTraining;
import ch.opentrainingcenter.gui.model.GTraining;
import ch.opentrainingcenter.gui.service.DistanceHelper;
import ch.opentrainingcenter.gui.service.TimeHelper;

public final class ConvertMultiTraining {

    private ConvertMultiTraining() {
        // do not create instance
    }

    public static GMultiTraining convert(final Collection<Training> trainings) {
        final List<GTraining> ts = new ArrayList<>();
        trainings.forEach((final Training t) -> ts.add(new GTraining(t)));
        long dist = 0;
        long dauer = 0;
        long avgHeart = 0;
        int maxHeart = 0;
        for (final Training training : trainings) {
            dist += training.getLaengeInMeter();
            dauer += training.getDauer();
            avgHeart = training.getAverageHeartBeat() * training.getLaengeInMeter();
            maxHeart = maxHeart < training.getMaxHeartBeat() ? training.getMaxHeartBeat() : maxHeart;
        }
        final String pace = DistanceHelper.calculatePace(dist, dauer);
        final String dauerTotal = TimeHelper.convertSecondsToHumanReadableZeit(dauer);
        final String distTotal = DistanceHelper.roundDistanceFromMeterToKm(dist);
        final String avgHeartGenormt = String.valueOf(avgHeart / dist);
        return new GMultiTraining(ts, dauerTotal, distTotal, pace, avgHeartGenormt, String.valueOf(maxHeart));
    }
}
