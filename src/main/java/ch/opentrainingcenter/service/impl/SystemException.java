package ch.opentrainingcenter.service.impl;

public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SystemException() {
        super();
    }

    public SystemException(final String message) {
        super(message);
    }
}
