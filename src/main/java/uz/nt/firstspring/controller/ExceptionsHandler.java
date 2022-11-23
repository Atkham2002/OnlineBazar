package uz.nt.firstspring.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import uz.nt.firstspring.dto.ResponseDto;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseDto<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));

        return ResponseDto.<Map<String, String>>builder().data(errors).code(-3).message("Validation error").success(false).build();
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseDto<String> unauthorized(){
        return new ResponseDto<>(-3, false, "Avtorizatsiyadan o'tishda xatolik", null, null);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseDto<String> usernameNotFound(UsernameNotFoundException exception){
        return new ResponseDto<>(-3, false, exception.getMessage(), null, null);
    }
}
