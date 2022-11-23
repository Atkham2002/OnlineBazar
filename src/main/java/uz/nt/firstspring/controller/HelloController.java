package uz.nt.firstspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping("/hello")
@Schema(name = "№1. Hello")
@RequiredArgsConstructor
public class HelloController {

    private final MessageSource messageSource;

    @Tag(name="External", description = "Returns list of products by page and size")
    @Operation(summary = "№1.1 Hello by name")
    @GetMapping(value = "/by-name")
    public String hello(HttpServletRequest req, @RequestParam(value = "ism", required = true) String name,
                        @RequestParam Integer age){
        Locale locale = req.getLocale();
        if (name.equals("Sardor"))
            return "Hello, theSardor";

        return messageSource.getMessage("hello.", new String[]{"Sardor"}, "Default message", locale);
    }

//    @PostMapping
//    @Operation(summary = "№1.2 Post hello")
//    public String helloPost(){
//        hello("", 1);
//        return "hello post";
//    }

}
