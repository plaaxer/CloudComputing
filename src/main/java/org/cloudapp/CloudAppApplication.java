package org.cloudapp;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("CloudApp is starting..."); // Mensagem inicial

        char[] spinner = { '\\', '|', '/', '-' };
        int numIterations = 10;
        long delay = 400;

        for (int i = 0; i < numIterations; i++) { // Loop para a animação
            int spinnerIndex = i % spinner.length;
            System.out.print("\r" + spinner[spinnerIndex] + " running! yay");

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.print("\r");
        System.out.println("CloudApp is running! Welcome!");
    }

    public void start() {
        System.out.println("CloudApp is running! Welcome!");
        for (int i = 0; i < 10; i++) {
            System.out.println("Iteration " + i);
        }
    }
}
