package uz.nt.firstspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonIgnoreProperties(value = {"password"}, allowSetters = true, ignoreUnknown = true)
@ToString
public class LoginDTO {
    private String username;
    private String password;
}
