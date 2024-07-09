package ru.mts.homework;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableProcessApplication("ProcessApplication")
public class Main {

    public static void main(final String... args) throws Exception {
        SpringApplication.run(Main.class, args);
    }}