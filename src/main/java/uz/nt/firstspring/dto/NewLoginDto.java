package uz.nt.firstspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewLoginDto {
    private String firstName;
    private String lastName;
    private String password;
}
