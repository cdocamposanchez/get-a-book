package com.adi.gab.domain.exception;

public class BookExceptions {
    private BookExceptions(){}

    private static final String ARGUMENT = "Argument: ";

    public static class NullBookArgumentException extends DomainException {
        public NullBookArgumentException(String object) {
            super(ARGUMENT + object + " Cannot be null.");
        }
    }

    public static class LenghtBookArgumentException extends DomainException {
        public LenghtBookArgumentException(String object, int lenght) {
            super(ARGUMENT + object + " Does not match the length: " + lenght);
        }
    }
}
