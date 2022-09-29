package uz.nt.firstspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnitDto {
    private Integer id;
    private String name;
    private String shortName;
}
