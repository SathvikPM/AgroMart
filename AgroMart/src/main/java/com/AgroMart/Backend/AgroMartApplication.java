package com.AgroMart.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AgroMartApplication {

	public static void main(String[] args) {


		ApplicationContext context=SpringApplication.run(AgroMartApplication.class, args);
//		String[] beans=context.getBeanDefinitionNames();
//		for(String bean:beans){
//			System.out.println(bean);
//		}
	}

}
