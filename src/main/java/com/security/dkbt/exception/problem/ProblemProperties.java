package com.security.dkbt.exception.problem;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "app.problem")
@Getter
@Setter
public class ProblemProperties {

    private boolean debug = false;

}
