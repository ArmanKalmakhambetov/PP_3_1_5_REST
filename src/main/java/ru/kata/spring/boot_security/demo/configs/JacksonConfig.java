package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.serializer.UserDeserializer;

@Configuration
public class JacksonConfig {
    @Bean
    public UserDeserializer userDeserializer() {
        return new UserDeserializer();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJson() {
        return builder -> builder.deserializerByType(User.class, userDeserializer());
    }
}

