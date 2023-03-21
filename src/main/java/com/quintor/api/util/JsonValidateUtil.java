package com.quintor.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import com.quintor.api.interfaces.Validatable;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;

public class JsonValidateUtil implements Validatable {
    @Override
    public boolean validate(String schemaFileName, String jsonToValidate) throws JsonProcessingException {
        System.out.println("Given schemafile: " + schemaFileName);
        System.out.println("Given JSON: " + jsonToValidate);

        InputStream schemaAsStream = JsonValidateUtil.class.getClassLoader().getResourceAsStream("schemas/" + schemaFileName);

        //Read all lines from inputstream
//        Scanner s = new Scanner(schemaAsStream).useDelimiter("\\A");
//        String result = s.hasNext() ? s.next() : "";
//        System.out.println("check file: \n" + result);

        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);

        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        JsonNode jsonNode = om.readTree(jsonToValidate);

        Set<ValidationMessage> errors = schema.validate(jsonNode);
        String errorsCombined = "";
        for (ValidationMessage error : errors) {
            System.out.println("Validation error: " + error);
            errorsCombined += error.toString() + "\n";
        }

        return errors.size() == 0;
    }
}
