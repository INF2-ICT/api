package com.quintor.api.interfaces;

public interface Validatable {
    /**
     * Function to validate input with a schema
     * @param schemaFileName name + extension of schema file
     * @param input String input that needs to be validated
     * @return boolean if validate is successful, false if not
     * @throws Exception if error
     */
    boolean validate(String schemaFileName, String input) throws Exception;
}
