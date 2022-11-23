package uz.nt.firstspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.service.impl.RestService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/remote")
@Schema(name = "№4. Remote")
public class RestControllerC {

    @Autowired
    private RestService restService;

    @Operation(summary = "№4.1 Get remote products")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<ProductDto>>> getProducts(){
       return restService.getAllProducts();
    } ;

    @GetMapping("{id}")
    @Operation(summary = "№4.2 Get remote products")
    public ResponseEntity<ResponseDto> getById(@PathVariable Integer id){
        return restService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> add(@RequestBody ProductDto productDto){
        return restService.postProduct(productDto);
    }
}
