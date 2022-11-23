package uz.nt.firstspring.service.mapper;

import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.entity.ProductTypes;

import java.util.List;
import java.util.stream.Collectors;

public class MapperProductType {
    public static ProductTypeDto toDto(ProductTypes types, UnitMapper unitMapper){

        List<ProductDto> ls=types.getProducts().stream().map(MapperProduct::NoTypeToDto).collect(Collectors.toList());

       return ProductTypeDto.builder()
                .id(types.getId())
                .products(ls)
                .limit(types.getLimit())
                .barcode(types.getBarcode())
                .name(types.getName()).unitId(unitMapper.toDto(types.getUnitId())).build();

    }
    public static  ProductTypeDto NoProductToDto(ProductTypes types, UnitMapper unitMapper){
        return  ProductTypeDto.builder()
                .id(types.getId())
                .limit(types.getLimit())
                .barcode(types.getBarcode())
                .name(types.getName()).unitId(unitMapper.toDto(types.getUnitId())).build();

    }

}
