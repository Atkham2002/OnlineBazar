package uz.nt.firstspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.entity.ProductTypes;
import uz.nt.firstspring.service.ProductTypesService;
import uz.nt.firstspring.service.impl.ProductTypesServiceImpl;
import uz.nt.firstspring.service.mapper.MapperProduct;
import uz.nt.firstspring.service.mapper.MapperProductType;
import uz.nt.firstspring.service.mapper.UnitMapper;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product-type")
@RequiredArgsConstructor
public class ProductTypesController {

    private final ProductTypesServiceImpl productTypesService;
    private final UnitMapper unitMapper;

    @PostMapping
    public @ResponseBody
    ResponseDto<String> add(@RequestBody ProductTypeDto productTypeDto){
        return productTypesService.addProduct(productTypeDto);
    }

    @GetMapping
    public @ResponseBody
    ResponseDto<List<ProductTypeDto>> getAll(){
        List<ProductTypes> productTypes = productTypesService.getProductTypes();

        List<ProductTypeDto> productTypeDto = productTypes.stream()
                .map(m -> MapperProductType.toDto(m, unitMapper))
                .collect(Collectors.toList());

        return ResponseDto.<List<ProductTypeDto>>builder().message("OK").success(true).data(productTypeDto).build();
//        return productTypesService.getAll();
    }

    @GetMapping("/check")
    public @ResponseBody
    String lazy(){
        return productTypesService.getWithLazy();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseDto<ProductTypeDto> getOne(@PathVariable Integer id){
        return productTypesService.getOne(id);
    }
}
