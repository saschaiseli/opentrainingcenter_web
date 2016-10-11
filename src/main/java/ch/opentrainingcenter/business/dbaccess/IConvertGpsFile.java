package ch.opentrainingcenter.business.dbaccess;

import java.io.InputStream;

import ch.opentrainingcenter.business.domain.Training;

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
