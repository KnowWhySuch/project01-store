package com.cy.store.service.ex;

/**
 *
 */
public class CatNotFoundException extends ServiceException{
    public CatNotFoundException() {
        super();
    }

    public CatNotFoundException(String message) {
        super(message);
    }

    public CatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatNotFoundException(Throwable cause) {
        super(cause);
    }

    public CatNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
