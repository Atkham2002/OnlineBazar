package uz.nt.firstspring.service;

import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.entity.ProductTypes;

import java.util.List;

public interface ProductTypesService {
    ResponseDto<String> addProduct(ProductTypeDto dto);

    ResponseDto<List<ProductTypeDto>> getAll();

    ResponseDto<ProductTypeDto> getOne(Integer id);
}
