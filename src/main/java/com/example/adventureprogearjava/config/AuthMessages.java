package com.example.adventureprogearjava.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "auth.message")
@Getter
@Setter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthMessages {
    String confirmed;
    String invalidToken;
    String expired;
    String tokenNotFound;
}
