package com.adi.gab.domain.exception;

public class UserExceptions {
    private UserExceptions(){}

    private static final String ARGUMENT = "Argument: ";

    public static class NullUserArgumentException extends DomainException {
        public NullUserArgumentException(String object) {
            super(ARGUMENT + object + " Cannot be null.");
        }
    }

    public static class LenghtUserArgumentException extends DomainException {
        public LenghtUserArgumentException(String object, int lenght) {
            super(ARGUMENT + object + " Does not match the length: " + lenght);
        }
    }
}
