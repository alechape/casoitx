package com.capitole.casoitx.domain.exception;

public class InvalidPriceParametersException extends BadRequestException {
    public InvalidPriceParametersException(String message) {
        super(message);
    }
}