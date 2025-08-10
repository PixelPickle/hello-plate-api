package com.squirrelly_app.hello_plate_api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EnvironmentVariables {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

}
