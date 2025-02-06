package exception;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class ErrorDetails {
	
	private LocalTime localTime;
	private String message;
	private String request;
	
	ErrorDetails(LocalTime localTime, String message, String request) {
		this.localTime = localTime;
		this.message = message;
		this.request = request;
		
	}

}
