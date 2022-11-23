package uz.nt.firstspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductDto", description = "Form for accept and return value of Products")
public class ProductDto extends RepresentationModel<ProductDto>{

    @Schema(name = "id", description = "Identification number of product")
    private Integer id;

    @Schema(name = "name", description = "Name of Product")
    @NotBlank(message = "Bo'sh maydon")
    private String name;

    @Schema(name = "type", description = "Type of product in JSON")
    @NotNull(message = "Bo'sh maydon")
    private ProductTypeDto type;

    @Schema(name = "amount", description = "Amount of product in unit", minimum = "0")
    @NotNull(message = "Bo'sh maydon")
    @Min(value = 0, message = "Manfiy qiymat")
    private Integer amount;

    @Schema(name = "price", description = "Price of product for one unit", minimum = "0")
    @NotNull(message = "Bo'sh maydon")
    @Min(value = 0, message = "Manfiy qiymat")
    private Double price;
}
