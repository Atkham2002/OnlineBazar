package uz.nt.firstspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ValidatorDto;
import uz.nt.firstspring.service.ValidatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {
    private final ResourceBundle bundle;

    @Override
    public List<ValidatorDto> validateProduct(ProductDto productDto) {
        List<ValidatorDto> errors = new ArrayList<>();

        if (!StringUtils.hasText(productDto.getName())){
            errors.add(new ValidatorDto("name", bundle.getString("response.empty_field")));
        }

        if (productDto.getAmount() == null){
            errors.add(new ValidatorDto("amount", bundle.getString("response.empty_field")));
        }else if (productDto.getAmount() < 0){
            errors.add(new ValidatorDto("amount", bundle.getString("response.negative_value")));
        }

        if (productDto.getPrice() == null){
            errors.add(new ValidatorDto("price", bundle.getString("response.empty_field")));
        }else if (productDto.getPrice() < 0){
            errors.add(new ValidatorDto("price", bundle.getString("response.negative_value")));
        }

        if(productDto.getType() == null || productDto.getType().getId() == null){
            errors.add(new ValidatorDto("type", bundle.getString("response.empty_field")));
        }

        return errors;
    }
}
