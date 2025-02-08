package com.syuk27.springboot.pt_restful_hibernate.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	//Path Parameters
	// /user/{id}/todos/{id}
	
	@GetMapping("/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	//GET => select
	//POST => create
	//PUT => update
	//PATCH => update part of
	//DELETE => delete
	
	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized() {
		
		//messages_ko.properties => header에 Accept-Language ko 추가 하여 호출 
		
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		
		return message;
	}
}
