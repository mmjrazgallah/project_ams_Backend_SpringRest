package com.sip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sip.entities.Provider;
import com.sip.repositories.ProviderRepository;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



import java.io.IOException;

@SpringBootApplication
public class CampSpringApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(CampSpringApplication.class, args);

		 	
	}

}
