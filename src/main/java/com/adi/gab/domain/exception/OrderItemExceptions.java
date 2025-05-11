package com.adi.gab.domain.exception;

public class OrderItemExceptions {
    private OrderItemExceptions(){}

    private static final String ARGUMENT = "Argument: ";

    public static class NullOrderItemArgumentException extends DomainException {
        public NullOrderItemArgumentException(String object) {
            super(ARGUMENT + object + " Cannot be null.");
        }
    }

    public static class LenghtOrderItemArgumentException extends DomainException {
        public LenghtOrderItemArgumentException(String object, int lenght) {
            super(ARGUMENT + object + " Does not match the length: " + lenght);
        }
    }
}
