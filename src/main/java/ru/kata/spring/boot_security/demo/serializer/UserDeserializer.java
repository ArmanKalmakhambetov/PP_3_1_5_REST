package ru.kata.spring.boot_security.demo.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserDeserializer extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Получение значений полей из JSON
        Long id = node.get("id") != null ? node.get("id").asLong() : null;
        String username = node.get("username") != null ? node.get("username").asText() : null;
        String password = node.get("password") != null ? node.get("password").asText() : null;
        String firstName = node.get("firstName") != null ? node.get("firstName").asText() : null;
        String lastName = node.get("lastName") != null ? node.get("lastName").asText() : null;
        String age = node.get("age") != null ? node.get("age").asText() : null;
        Set<Role> roles = new HashSet<>();
        Set<Role> authorities = new HashSet<>();

        // Получение значений полей "roles" и "authorities" из JSON
        JsonNode rolesNode = node.get("roles");
        if (rolesNode != null && rolesNode.isArray()) {
            for (JsonNode roleNode : rolesNode) {
                // Создание объекта Role и добавление в Set<Role>
                Role role = new Role();
                role.setId(roleNode.get("id").asLong());
                role.setName(roleNode.get("name") != null ? roleNode.get("name").asText() : null); // Установка значения поля "name"
                roles.add(role);
            }
        }

//        JsonNode authoritiesNode = node.get("authorities");
//        if (authoritiesNode != null && authoritiesNode.isArray()) {
//            for (JsonNode authorityNode : authoritiesNode) {
//                Role authority = new Role();
//                authority.setId(authoritiesNode.get("id").asLong());
//                authority.setName(authorityNode.get("name") != null ? authorityNode.get("name").asText() : null); // Установка значения поля "name"
//                authorities.add(authority);
//            }
//        }

        // Создание объекта User
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age != null ? Byte.valueOf(age) : null); // Установка значения поля "age"
        user.setRoles(roles);
        user.setAuthority(authorities);

        // Дополнительная логика десериализации, если требуется

        return user;
    }
}


