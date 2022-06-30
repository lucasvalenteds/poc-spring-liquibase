package com.example.country;

import java.io.Serial;

public final class CountryNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7682806060704262598L;

    public CountryNotFoundException(String code) {
        super("None country found with code " + code);
    }
}
