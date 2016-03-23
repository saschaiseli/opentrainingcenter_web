package ch.opentrainingcenter.service.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

public final class TimeHelper {

    private static final String DATE_FILE_FORMAT_PATTERN = "yyyyMMddHHmmssSSS"; //$NON-NLS-1$
    private static final String DATE_TIME_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss"; //$NON-NLS-1$
    private static final String TIME_FORMAT_PATTERN = "HH:mm:ss"; //$NON-NLS-1$
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy"; //$NON-NLS-1$
    private static final String UNKNOWN_DATE = "--:--:--"; //$NON-NLS-1$

    private TimeHelper() {

    }

    /**
     * Konvertiert Sekunden in HH:MM:ss
     *
     * @param sec
     *            sekunden
     * @return Zeit im Format HH:MM:ss. Wenn Zeit negativ ist, wird --:--:--
     *         zurückgegeben.
     */
    public static String convertSecondsToHumanReadableZeit(final double sec) {
        if (sec < 0) {
            return UNKNOWN_DATE;
        }
        return convertTimeToString((long) (sec * 1000));
    }

    /**
     * konvertiere ein {@link Date} in ein lesbareres format.
     *
     * wenn der parameter withDay true ist wird der wochentag noch mit
     * ausgegeben
     *
     * @param datum
     *            {@link Date}
     * @param withDay
     *            flag ob der wochenag auch mitgegeben werden soll.
     * @return das datum '13.01.2013 00:00:00' und wenn das flag with Day ist
     *         wird noch der ausgeschreibene wochentag vorangestellt.
     */
    public static String convertDateToString(final Date datum, final boolean withDay) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
        if (withDay) {
            return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " " + format.format(datum); //$NON-NLS-1$
        } else {
            return format.format(datum);
        }
    }

    /**
     * konvertiere ein {@link Date} in ein lesbareres format.
     *
     * wenn der parameter withDay true ist wird der wochentag noch mit
     * ausgegeben
     *
     * @param datum
     *            {@link Date}
     * @param withDay
     *            flag ob der wochenag auch mitgegeben werden soll.
     * @return das datum '13.01.2013 00:00:00' und wenn das flag with Day ist
     *         wird noch der ausgeschreibene wochentag vorangestellt.
     */
    public static String convertDateToString(final Long datum, final boolean withDay) {
        return convertDateToString(new Date(datum), withDay);
    }

    /**
     * konvertiere ein {@link Date} in ein lesbareres format.
     *
     * wenn der parameter withDay true ist wird der wochentag noch mit
     * ausgegeben
     *
     * @param datum
     *            {@link Date}
     * @return das datum '201011231423'.
     */
    public static String convertDateToFileName(final Date datum) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FILE_FORMAT_PATTERN);
        return format.format(datum);
    }

    /**
     * @return datum als String in der Form dd.mm.yyyy
     */
    public static String convertDateToString(final Date datum) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(datum);
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return format.format(datum);
    }

    /**
     * @return datum als String in der Form dd.mm.yyyy
     */
    public static String convertDateToString(final Long millis) {
        return convertDateToString(new Date(millis));
    }

    /**
     * @return zeit als String in der Form HH:mm:ss
     */
    public static String convertTimeToString(final long timeInMillis) {
        final Calendar calendar = Calendar.getInstance(Locale.GERMAN);
        calendar.setTimeInMillis(timeInMillis);
        final SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        format.setTimeZone(TimeZone.getTimeZone("GMT+0")); //$NON-NLS-1$
        return format.format(calendar.getTime());
    }

    /**
     * @return zeit als String in der Form HH:mm:ss bei 23:59:59 wird aber
     *         weitergezaehlt > 28:22:11,...
     */
    public static String convertTimeToStringHourUnlimited(final long timeInMillis) {
        if (timeInMillis >= 86400000) {
            final int days = (int) timeInMillis / 86400000;
            final long rest = timeInMillis % 86400000L;
            final DateTimeZone dtz = DateTimeZone.UTC;
            final DateTime dt = new DateTime(rest, dtz);

            final int hourOfDay = dt.getHourOfDay() + days * 24;
            return String.format("%s:%s:%s", hourOfDay, addLeadingZero(dt.getMinuteOfHour()), addLeadingZero(dt.getSecondOfMinute())); //$NON-NLS-1$
        } else {
            return convertTimeToString(timeInMillis);
        }
    }

    private static String addLeadingZero(final int value) {
        if (value < 10) {
            return "0" + value; //$NON-NLS-1$
        } else {
            return String.valueOf(value);
        }
    }

    public static int getKalenderWoche(final Date date, final Locale locale) {
        final Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Gibt das Jahr zurück. Allerdings gibt zum Beispiel der 1. Januar 2012
     * nicht 2012 zurück sondern 2011. Das Jahr bezieht sich immer auf die Woche
     * in der der Tag ist.
     *
     */
    public static int getJahr(final Date date, final Locale locale) {
        final Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week == 52 && month == 0) {
            // so zum beispiel am 1. januar 2012 --> KW52
            year--;
        }
        return year;
    }

    /**
     * Berechnet den Montag, sowie den Sonntag der angegebenen Kalenderwoche in
     * dem entsprechenden Jahr. </br>
     * Für KW 44 im 2012 wäre dies 29. Oktober <--> 4. November
     *
     * @param jahr
     *            Das Jahr
     * @param kalenderwoche
     *            Die Kalenderwoche
     * @return Start und Enddatum --> Montag und Sonntag der Kalenderwoche
     */
    public static Interval getInterval(final int jahr, final int kalenderwoche) {
        Interval result = null;
        final DateTime dt = new DateTime(jahr, 1, 1, 0, 0);
        DateTime day = null;
        // etwas gewagt, aber das scheiss ding will einfach nicht anders
        for (int i = kalenderwoche - 1; i <= kalenderwoche + 1; i++) {
            day = dt.weekOfWeekyear().addToCopy(i);
            if (day.getWeekOfWeekyear() == kalenderwoche && day.getYear() == jahr) {
                break;
            }
        }
        if (day != null) {
            final int dayOfWeek = day.getDayOfWeek();
            final DateTime start = day.minusDays(dayOfWeek - 1);
            final DateTime end = day.plusDays(7 - dayOfWeek);
            result = new Interval(start.getMillis(), end.getMillis());
        }
        return result;
    }

    /**
     * Gibt den Montag 00:00:00 Uhr zurück von der Woche mit dem angegebenen
     * Datum.
     *
     * Zum Beispiel: Sonntag 15. Dezember 2013 09:11:00 --> gibt Montag 9.
     * Dezember 2013 00:00:00 zurück.
     */
    public static DateTime getFirstDayOfWeek(final DateTime now) {
        DateTime result = now.minusDays(now.getDayOfWeek() - 1);
        result = result.minusHours(now.getHourOfDay());
        result = result.minusMinutes(now.getMinuteOfHour());
        result = result.minusSeconds(now.getSecondOfMinute());
        result = result.minusMillis(now.getMillisOfSecond());
        return result;
    }

    /**
     * Gibt den ersten Tag mit 00:00:00 Uhr vom Monat zurück.
     *
     * Zum Beispiel: Sonntag 15. Dezember 2013 09:11:00 --> gibt Sonntag 1.
     * Dezember 2013 00:00:00 zurück.
     */
    public static DateTime getFirstDayOfMonth(final DateTime now) {
        DateTime result = now.minusDays(now.getDayOfMonth() - 1);
        result = result.minusHours(now.getHourOfDay());
        result = result.minusMinutes(now.getMinuteOfHour());
        result = result.minusSeconds(now.getSecondOfMinute());
        result = result.minusMillis(now.getMillisOfSecond());
        return result;
    }

    /**
     * Macht aus Jahr, Monat, Tag ein Date mit Zeit 00:00:00
     *
     * @param year
     *            Jahr
     * @param month
     *            Monat 0...11
     * @param day
     *            Tag
     * @return
     */
    public static Date getDate(final int year, final int month, final int day) {
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

}
