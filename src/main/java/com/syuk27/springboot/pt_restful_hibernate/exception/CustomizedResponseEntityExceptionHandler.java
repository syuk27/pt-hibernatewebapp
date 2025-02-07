package com.syuk27.springboot.pt_restful_hibernate.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.syuk27.springboot.pt_restful_hibernate.user.UserNotFoundException;

@ControllerAdvice 
//@ControllerAdvice => 모든 controller 찾음, 기본적으로 자신이 속한 패키지 및 그 하위 패키지에만 적용
//@RestControllerAdvice는 @ControllerAdvice + @ResponseBody의 조합으로, JSON 형태로 응답을 보내는 경우 적절
// 이외의 bean는 적용x 컨트롤러에 던저서 처리하면 
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
//	request.getDescription() → false이면 클라이언트 정보(IP, 세션 ID)를 포함하지 않고 URI 정보를 반환
//	request.getDescription() → true이면 클라이언트 정보(IP, 세션 ID 포함)
//	결과 예시
//	request.getDescription(false) → uri=/api/users
//	request.getDescription(true) → uri=/api/users;client=192.168.1.1;session=ABC123
}
