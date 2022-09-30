package uz.nt.firstspring.service;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;

import java.util.List;

public interface ProductService {

    ResponseDto<String> addProduct(ProductDto dto);

    ResponseDto<ProductDto> findById(Integer id);

    ResponseDto<Page<ProductDto>> getAll(Integer page, Integer size);

    ResponseDto<Page<ProductDto>> search(String name, Integer page, Integer size);

    ResponseDto<Page<ProductDto>> byParams(MultiValueMap<String, String> params);

    void exportLessThanLimit();
}
