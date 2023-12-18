package org.crmweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan(basePackages = "org.ultimacrm.models")
@ComponentScan(basePackages = {"org.ultimacrm", "org.ultimacrm.config"})
public class CrmSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmSpringApplication.class, args);
	}

}
