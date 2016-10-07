package com.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Mir on 04/10/2016.
 */

@SpringBootApplication
@ComponentScan ({"com.acme"})
public class DemoApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApp.class, args);
    }
}