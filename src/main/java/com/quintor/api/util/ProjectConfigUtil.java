package com.quintor.api.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfigUtil {
    public static boolean checkApiKey(String apikey) {
       return apikey.equals("test123"); //"test123"(api key) needs to be put in environment file.
    }
}
