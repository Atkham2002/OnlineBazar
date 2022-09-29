package uz.nt.firstspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;
    @NotBlank(message = "Bo'sh maydon")
    private String name;
    @NotNull(message = "Bo'sh maydon")
    private ProductTypeDto type;
    @NotNull(message = "Bo'sh maydon")
    @Min(value = 0, message = "Manfiy qiymat")
    private Integer amount;
    @NotNull(message = "Bo'sh maydon")
    @Min(value = 0, message = "Manfiy qiymat")
    private Double price;
}
