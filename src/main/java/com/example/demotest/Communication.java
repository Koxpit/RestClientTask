package com.example.demotest;

import com.example.demotest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {
    @Autowired
    private RestTemplate restTemplate;
    private HttpEntity<Object> entity;
    private HttpHeaders headers = new HttpHeaders();

    private final String URL = "http://94.198.50.185:7081/api/users";

    public List<User> getAllUsers() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET,
                entity == null ? null : entity, new ParameterizedTypeReference<>() {});

        if (entity != null) {
            List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
            headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
            System.out.println(cookies.stream().collect(Collectors.joining(";")));
            entity = new HttpEntity<>(headers);
        }

        return responseEntity.getBody();
    }

    public void saveUser(User user) {
        entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println("Added new User:");
        System.out.println("POST BODY: " + responseEntity.getBody());
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        System.out.println(cookies.stream().collect(Collectors.joining(";")));
    }

    public void updateUser(User user) {
        entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println("User with ID " + user.getId() + " was updated");
        System.out.println("PUT BODY: " + responseEntity.getBody());
    }

    public void deleteUser(int id) {
        entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.println("User with ID " + id + " was deleted");
        System.out.println("PUT BODY: " + responseEntity.getBody());
    }
}
