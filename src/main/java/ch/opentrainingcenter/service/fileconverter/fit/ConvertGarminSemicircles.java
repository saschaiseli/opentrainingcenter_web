package ch.opentrainingcenter.service.fileconverter.fit;

import java.math.BigDecimal;

/**
 * http://www.gps-forums.net/accuracy-converting-semicircles-degrees-t31488.html
 */
public class ConvertGarminSemicircles {

    private static final BigDecimal MULTIPLIKATOR = new BigDecimal(180.0d / Math.pow(2L, 31L));

    /**
     * Konvertiert Garmin semicircles nach WGS84 Grad
     *
     * http://www.gps-forums.net/accuracy-converting-semicircles-degrees-t31488.
     * html
     */
    public static BigDecimal convertSemicircleToDms(final long semicircle) {
        return new BigDecimal(semicircle).multiply(MULTIPLIKATOR);
    }

}
