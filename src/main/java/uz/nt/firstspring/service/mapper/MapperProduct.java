package uz.nt.firstspring.service.mapper;

import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.entity.Product;

public class MapperProduct {

    public static ProductDto toDto(Product product, UnitMapper unitMapper){
        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .type( MapperProductType.NoProductToDto(product.getType(), unitMapper) )
                .amount(product.getAmount())
                .price(product.getPrice())
                .build();
    }
    public static ProductDto NoTypeToDto(Product product){
        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .build();
    }
}
