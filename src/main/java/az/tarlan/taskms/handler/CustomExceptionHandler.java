package az.tarlan.taskms.handler;

import az.tarlan.taskms.dto.response.ErrorResponseDto;
import az.tarlan.taskms.exception.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .status(status.toString())
                .message(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage())
                .errorTime(LocalDateTime.now()).build();
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleRecordNotFoundException(RecordNotFoundException ex) {
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(ex.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
