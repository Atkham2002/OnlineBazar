package uz.nt.firstspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.firstspring.dto.LoginDTO;
import uz.nt.firstspring.dto.NewLoginDto;

@RestController
@RequestMapping("/version")
public class VersioningController {


    //URL
    @GetMapping("/v1/login")
    public LoginDTO loginDefault(){
        return new LoginDTO("Atkham Abdulkhaev", "123");
    }

    @GetMapping("v2/login")
    public NewLoginDto newLogin(){
        return new NewLoginDto("Atkham", "Abdulkhaev", "123");
    }

    //Parameter
    @GetMapping(value = "login", params = "version=1")
    public LoginDTO loginWithParam(){
        return new LoginDTO("Atkham Abdulkhaev", "123");
    }

    @GetMapping(value = "login", params = "version=2")
    public NewLoginDto loginWithParamVersion2(){
        return new NewLoginDto("Atkham", "Abdulkhaev", "123");
    }

    //Header
    @GetMapping(value = "login", headers = "X-API-VERSION=1")
    public LoginDTO loginWithHeader(){
        return new LoginDTO("Atkham Abdulkhaev", "123");
    }

    @GetMapping(value = "login", headers = "X-API-VERSION=2")
    public NewLoginDto loginWithHeaderVersion2(){
        return new NewLoginDto("Atkham", "Abdulkhaev", "123");
    }

    //Media type
    @GetMapping(value = "login", produces = "application/vnd.company.app-v1+json")
    public LoginDTO loginWithMediaType(){
        return new LoginDTO("Atkham Abdulkhaev", "123");
    }

    @GetMapping(value = "login", produces = "application/vnd.company.app-v2+json")
    public NewLoginDto loginWithMediaTypeVersion2(){
        return new NewLoginDto("Atkham", "Abdulkhaev", "123");
    }
}