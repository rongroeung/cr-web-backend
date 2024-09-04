package kh.com.acledabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAsync
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}