package uz.nt.firstspring.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;

import java.util.List;

@Service
public class RestService {

    public ResponseEntity<ResponseDto> getProductById(Integer id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseDto> response = restTemplate.exchange("http://192.168.6.36:8080/api/product/" + id,
                HttpMethod.GET,
                null,
                ResponseDto.class);
        return response;
    }

    public ResponseEntity<ResponseDto<List<ProductDto>>> getAllProducts(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseDto<List<ProductDto>>> response = restTemplate.exchange("http://192.168.7.35:8080/api/product?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<List<ProductDto>>>() {});

        return response;
    }

    public ResponseEntity<ResponseDto> postProduct(ProductDto productDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ProductDto> entity = new HttpEntity<>(productDto);
//        return restTemplate.postForEntity("http://192.168.7.35:8080/api/product", productDto, ResponseDto.class);
        return restTemplate.exchange("http://192.168.7.35:8080/api/product", HttpMethod.POST, entity, ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> update(ProductDto productDto){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange("http://192.168.7.35:8080/api/product", HttpMethod.PUT, new HttpEntity<>(productDto), ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> delete(Integer id){
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete("http://192.168.7.35:8080/api/product/" + id);
            return ResponseEntity.ok(ResponseDto.builder().message("Deleted successfully!").data(null).code(0).success(true).build());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(ResponseDto.builder().message("Failed successfully!").data(null).code(-1).success(false).build());
        }
    }
}