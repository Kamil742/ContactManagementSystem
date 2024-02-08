package com.application.contactmanagementsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.Basic;

@SpringBootApplication
public class ContactManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactManagementSystemApplication.class, args);
	}

}
