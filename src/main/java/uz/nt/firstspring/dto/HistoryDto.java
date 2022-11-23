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
public class HistoryDto {
    private Long id;
    private String url;
    private String remoteAddress;
    private Long userId;
    private Date date;
    private String originalData;
    private String responseData;
    private String error;
    private String requestMethod;
}
