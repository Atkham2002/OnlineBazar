package uz.nt.firstspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseDto<String> addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @GetMapping
    public ResponseDto<Page<ProductDto>> getAll(@RequestParam Integer page, @RequestParam Integer size){
        return productService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseDto<ProductDto> findById(@PathVariable Integer id){
        return productService.findById(id);
    }

    @GetMapping("/search")
    public ResponseDto<Page<ProductDto>> search(@RequestParam String name, @RequestParam Integer page, @RequestParam Integer size, @RequestParam Double price){
        return productService.search(name, page, size);
    }

    @GetMapping("/by-param")
    public ResponseDto<Page<ProductDto>> byParams(@RequestParam MultiValueMap<String, String> params){
        return productService.byParams(params);
    }
}
