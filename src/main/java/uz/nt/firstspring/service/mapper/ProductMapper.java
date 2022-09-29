package uz.nt.firstspring.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
}
