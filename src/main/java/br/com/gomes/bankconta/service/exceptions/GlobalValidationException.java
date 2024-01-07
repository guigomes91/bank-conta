package br.com.gomes.bankconta.service.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.context.request.WebRequest;

import br.com.gomes.bankconta.dto.ExceptionDTO;
import br.com.gomes.bankconta.resources.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalValidationException {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionDTO> notFoundException(NotFoundException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(HttpStatus.NOT_FOUND, e.getMessage()));
	}

	@ExceptionHandler(Forbidden.class)
	public ResponseEntity<ExceptionDTO> forbiddenException(Forbidden e) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDTO(HttpStatus.FORBIDDEN, e.getMessage()));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionDTO> badRequestException(BadRequestException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionDTO> illegalArgumentException(IllegalArgumentException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
	}

	@ExceptionHandler(value = {
			org.hibernate.exception.ConstraintViolationException.class,
			org.springframework.dao.DataIntegrityViolationException.class,
			java.lang.NullPointerException.class
			}
	)
	public ResponseEntity<ExceptionDTO> dataIntegrityViolationException(Exception ex, WebRequest req) {

		log.error("Data integrity violation while processing {}", req.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionDTO(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionDTO> httpMessageNotReadableException(HttpMessageNotReadableException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
	}
}
