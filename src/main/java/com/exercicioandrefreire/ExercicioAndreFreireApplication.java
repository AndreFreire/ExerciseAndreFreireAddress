package com.exercicioandrefreire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExercicioAndreFreireApplication {
	
	private static final String PATH = "/errors";
	
	public static void main(String[] args) {

		SpringApplication.run(ExercicioAndreFreireApplication.class, args);
	}
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
      return (container -> {
   	   //route all errors towards /error .
   	   final ErrorPage errorPage=new ErrorPage(PATH);
   	   container.addErrorPages(errorPage);
      });
   }
}