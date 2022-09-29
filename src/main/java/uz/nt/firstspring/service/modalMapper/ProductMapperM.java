package uz.nt.firstspring.service.modalMapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.entity.Product;

@RequiredArgsConstructor
public class ProductMapperM {
    private final ModelMapper mapper;

    public Product toEntity(ProductDto productDto){
        return mapper.map(productDto, Product.class);
    }

    public ProductDto toDto(Product product){
        return mapper.map(product, ProductDto.class);
    }
}
