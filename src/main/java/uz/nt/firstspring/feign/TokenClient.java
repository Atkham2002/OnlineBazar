package uz.nt.firstspring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.nt.firstspring.dto.JWTResponseDTO;
import uz.nt.firstspring.dto.LoginDTO;
import uz.nt.firstspring.dto.ResponseDto;

@FeignClient(value = "token", url = "http://192.168.6.184:8080/api")
public interface TokenClient {

    @PostMapping("/user/login")
    ResponseDto<JWTResponseDTO> getToken(@RequestBody LoginDTO loginDTO);

}
