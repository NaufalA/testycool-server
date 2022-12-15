package com.testycool.testycoolserver.shared.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Data Not Found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
