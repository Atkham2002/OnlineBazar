package uz.nt.firstspring.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductTypeDto {

    private Integer id;
    private String name;
    private String barcode;
    private List<ProductDto> products;
    private UnitDto unitId;
    private Double limit;
}
