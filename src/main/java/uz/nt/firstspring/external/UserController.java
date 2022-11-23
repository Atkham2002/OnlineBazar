package uz.nt.firstspring.external;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.JWTResponseDTO;
import uz.nt.firstspring.dto.LoginDTO;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.service.impl.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Registration user.
     * @return ResponseDto<String> with message.
     * If there is any problem, returns ResponseDto with success is false and failed message
     */
    @Operation(summary = "Registration user")
    @PostMapping(params = "version=1")
    public ResponseDto<String> add(@RequestBody UserInfoDto userInfoDto){
        return userService.addUser(userInfoDto);
    }

    /**
     * Getting JWT with logging in.
     * If there is any problem, returns failed message
     */
    @SecurityRequirements
    @Operation(description = "Getting JWT with logging in")
    @PostMapping(value = "login", produces = "application/xml", headers = "X-API-VERSION=1")
    public ResponseDto<JWTResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        ResponseDto<JWTResponseDTO> login = userService.login(loginDTO);
        return login;
//        response.setContentType("application/xml");
//
//        XmlMapper mapper = new XmlMapper();
//        ObjectWriter writer = mapper.writerFor(new TypeReference<ResponseDto<JWTResponseDTO>>() {});
//        writer.writeValue(response.getOutputStream(), login);
//        response.getOutputStream().close();
    }

    /**
     * Checking JWT.
     * @return ResponseDto<UserInfoDto> with message.
     * If there is any problem, returns ResponseDto with success is false and failed message
     */
    @Operation(summary = "Checking token")
    @GetMapping("check")
    public MappingJacksonValue checkToken(@RequestParam String token){
        ResponseDto<UserInfoDto> userInfoDtoResponseDto = userService.checkToken(token);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userInfoDtoResponseDto);

//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password", "username","id");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept( "username","id");

        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("UserInfoFilter", filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
