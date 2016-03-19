package ch.opentrainingcenter.service.fileconverter.fit;

import java.io.InputStream;

import javax.ejb.Stateless;

import com.garmin.fit.Decode;

import ch.opentrainingcenter.model.Training;

/**
 * Converts FIT Files to a {@link Training}
 */
@Stateless
public class ConvertFitEJB {

    public Training convert(final InputStream inputStream) {
        final TrainingListener listener = new TrainingListener();
        final Decode decode = new Decode();
        decode.read(inputStream, listener);
        return listener.getTraining();
    }

    public String getFilePrefix() {
        return "fit";
    }

}
