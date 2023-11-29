package com.mcdonalds.ordermachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OrderMachineApplication {

	//DON'T FORGET TO SWITCH FROM H2 TO THE OTHER DB!!!
	public static void main(String[] args) {
		SpringApplication.run(OrderMachineApplication.class, args);
	}

}
