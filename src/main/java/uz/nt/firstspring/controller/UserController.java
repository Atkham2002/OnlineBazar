package uz.nt.firstspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.JWTResponseDTO;
import uz.nt.firstspring.dto.LoginDTO;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.service.impl.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseDto<String> add(@RequestBody UserInfoDto userInfoDto){
        return userService.addUser(userInfoDto);
    }

    @PostMapping("login")
    public ResponseDto<JWTResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("check")
    public ResponseDto<UserInfoDto> checkToken(@RequestParam String token){
        return userService.checkToken(token);
    }
}
