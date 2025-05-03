package com.adi.gab.domain.exception;

public class OrderExceptions {
    private OrderExceptions(){}

    private static final String ARGUMENT = "Argument: ";

    public static class NullOrderArgumentException extends DomainException {
        public NullOrderArgumentException(String object) {
            super(ARGUMENT + object + " Cannot be null.");
        }
    }

    public static class LenghtOrderArgumentException extends DomainException {
        public LenghtOrderArgumentException(String object, int lenght) {
            super(ARGUMENT + object + " Does not match the length: " + lenght);
        }
    }
}
