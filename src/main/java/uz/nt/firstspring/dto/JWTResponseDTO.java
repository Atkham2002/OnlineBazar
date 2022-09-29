package uz.nt.firstspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponseDTO {

    private String token;
    private Date expireDate;
    private String refreshToken;
}
