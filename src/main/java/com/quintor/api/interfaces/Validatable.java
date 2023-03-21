package com.quintor.api.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface Validatable {
    boolean validate(String schemaFileName, String input) throws JsonProcessingException;
}
