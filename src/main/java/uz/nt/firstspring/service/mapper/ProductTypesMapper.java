package uz.nt.firstspring.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.entity.ProductTypes;

@Mapper(componentModel = "spring")
public interface ProductTypesMapper {

    ProductTypes toEntity(ProductTypeDto productTypeDto);
    ProductTypeDto toDto(ProductTypes productTypes);
}
