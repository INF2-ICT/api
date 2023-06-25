package com.quintor.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import com.networknt.schema.ValidationMessage;
import com.quintor.api.interfaces.Validatable;

import java.io.InputStream;
import java.util.Set;

public class JsonValidateUtil implements Validatable {
    @Override
    public boolean validate(String schemaFileName, String jsonToValidate) throws JsonProcessingException {
        try {
            InputStream schemaAsStream = JsonValidateUtil.class.getClassLoader().getResourceAsStream("schemas/" + schemaFileName);

            JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);

            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(jsonToValidate);

            Set<ValidationMessage> errors = schema.validate(jsonNode);

            return errors.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
