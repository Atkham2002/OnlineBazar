package uz.nt.firstspring.service;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProductService {

    ResponseDto<String> addProduct(ProductDto dto);

    ResponseDto<ProductDto> findById(Integer id);

    /**
     * Get all product list by pagination.
     * @param page Number of page
     * @param size Size of elements in one page
     * @return ResponseDto with ProductDto page.
     * If there is any problem, returns ResponseDto with success is false and data is null
     */
    ResponseDto<Page<ProductDto>> getAll(Integer page, Integer size);

    ResponseDto<Page<ProductDto>> search(String name, Integer page, Integer size);

    ResponseDto<Page<ProductDto>> byParams(MultiValueMap<String, String> params);

    void export(HttpServletRequest request, HttpServletResponse response);

    void exportLessThanLimit();
}
