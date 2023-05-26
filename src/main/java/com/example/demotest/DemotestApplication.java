package com.example.demotest;

import com.example.demotest.configuration.MyConfig;
import com.example.demotest.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemotestApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);

        List<User> users = communication.getAllUsers();
        System.out.println(users);

        User user = new User("Alex", "Lesli", (byte) 20);
        user.setId(3);
        communication.saveUser(user);

        user.setId(3);
        user.setAge((byte) 40);
        communication.updateUser(user);

        communication.deleteUser(3);
    }

}
