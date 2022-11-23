package uz.nt.firstspring.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.entity.ProductTypes;
import uz.nt.firstspring.service.impl.ProductTypesServiceImpl;
import uz.nt.firstspring.service.mapper.MapperProductType;
import uz.nt.firstspring.service.mapper.UnitMapper;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product-type")
@RequiredArgsConstructor
@Schema(name = "№3. Product types")
public class ProductTypesController {

    private final ProductTypesServiceImpl productTypesService;
    private final UnitMapper unitMapper;

    @PostMapping
    @Operation(summary = "№3.1 Add new Product Type")
    public @ResponseBody
    ResponseDto<String> add(@RequestBody ProductTypeDto productTypeDto){
        return productTypesService.addProduct(productTypeDto);
    }

    @GetMapping
    @Operation(summary = "№3.2 Get all Product Types")
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
    @Hidden
    public @ResponseBody
    String lazy(){
        return productTypesService.getWithLazy();
    }

    @GetMapping("/{id}")
    @Operation(summary = "№3.3 Get one Product Type")
    public @ResponseBody
    ResponseDto<ProductTypeDto> getOne(@PathVariable Integer id){
        return productTypesService.getOne(id);
    }
}
