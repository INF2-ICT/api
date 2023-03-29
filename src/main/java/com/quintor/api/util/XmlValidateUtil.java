package com.quintor.api.util;

import com.quintor.api.interfaces.Validatable;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.File;
import java.io.StringReader;

public class XmlValidateUtil implements Validatable {
    @Override
    public boolean validate(String schemaFileName, String xmlToValidate) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/main/resources/schemas/" + schemaFileName));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlToValidate)));

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
