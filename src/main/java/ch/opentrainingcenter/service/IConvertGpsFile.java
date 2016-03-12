package ch.opentrainingcenter.service;

import java.io.InputStream;

import ch.opentrainingcenter.model.Training;

public interface IConvertGpsFile {
    /**
     * Read a file and convert it to a {@link Training}
     *
     * @throws RuntimeException
     */
    Training convert(final InputStream inputStream);

    /**
     * @return the prefix of the file
     */
    String getFilePrefix();
}
