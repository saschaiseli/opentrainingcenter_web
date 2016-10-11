package ch.opentrainingcenter.business.domain;


public enum Sport {
    RUNNING(0, "RUNNING", "Messages.Sport_JOGGEN", "icons/16_16/running_16_16.png"), //$NON-NLS-1$ //$NON-NLS-2$

    BIKING(1, "BIKE", "Messages.Sport_BIKE", "icons/16_16/bike_16_16.png"), //$NON-NLS-1$ //$NON-NLS-2$

    OTHER(2, "OTHER", "Messages.Sport_UNBEKANNT", "icons/man_u_32_32.png"); //$NON-NLS-1$ //$NON-NLS-2$

    private final int index;
    private final String message;
    private final String translated;
    private final String imageIcon;

    private Sport(final int index, final String message, final String translated, final String imageIcon) {
        this.index = index;
        this.message = message;
        this.translated = translated;
        this.imageIcon = imageIcon;
    }

    public int getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }

    public String getTranslated() {
        return translated;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    /**
     * @return String Array mit allen ChartFilter Namen.
     */
    public static String[] items() {
        final String[] result = new String[Sport.values().length];
        for (final Sport item : Sport.values()) {
            result[item.index] = item.getTranslated();
        }
        return result;
    }

    public static Sport getByIndex(final int sportIndex) {
        for (final Sport sport : Sport.values()) {
            if (sport.getIndex() == sportIndex) {
                return sport;
            }
        }
        throw new IllegalArgumentException(String.format("Mit dem Index '%s' kann kein Sport gefunden werden. '%s'", sportIndex, Sport.values())); //$NON-NLS-1$
    }
}
