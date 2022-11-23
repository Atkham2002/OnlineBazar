package uz.nt.firstspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Schema(name = "№2. In")
public class InController {
    @GetMapping("/hello-page")
    @Operation(summary = "№2.1 Index")
    public String hello(){
        return "index.html";
    }
}
