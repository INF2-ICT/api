package com.quintor.api.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfigUtil {

    //Disabled until more information available
//    @Bean
//    public SecurityFilterChain configuration(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable();
//
//        return http.build();
//    }

    public static boolean checkApiKey(String apikey) {
       return apikey.equals("test123"); //"test123"(api key) needs to be put in environment file.
    }
}
