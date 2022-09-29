package uz.nt.firstspring.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.ValidatorDto;

import java.util.List;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<List<ValidatorDto>> validatorExceptions(MethodArgumentNotValidException exception){
        List<ValidatorDto> errors = exception.getBindingResult().getFieldErrors().stream().map(fieldError -> new ValidatorDto(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return ResponseDto.<List<ValidatorDto>>builder().code(-3).success(false).message("validator error").errors(errors).build();
    }

}

