package uz.nt.firstspring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.firstspring.configuration.FeignConfiguration;
import uz.nt.firstspring.dto.MyPage;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;

@FeignClient(value = "remote", url = "http://192.168.6.184:8080/api", configuration = {FeignConfiguration.class})
public interface RestClient {

    @GetMapping("/product")
//    @Headers("Authorization: {token}")
    ResponseDto<MyPage<ProductDto>> getProducts(@RequestParam Integer page,
                                                @RequestParam Integer size);
}