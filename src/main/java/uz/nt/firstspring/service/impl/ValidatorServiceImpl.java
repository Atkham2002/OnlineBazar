package uz.nt.firstspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ValidatorDto;
import uz.nt.firstspring.repository.ProductTypesRepository;
import uz.nt.firstspring.repository.impl.ProductTypeRepositoryImpl;
import uz.nt.firstspring.service.ValidatorService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {

    @Override
    public List<ValidatorDto> validateProduct(ProductDto productDto) {
        List<ValidatorDto> errors = new ArrayList<>();

        if (!StringUtils.hasText(productDto.getName())){
            errors.add(new ValidatorDto("name", "bo'sh maydon"));
        }

        if (productDto.getAmount() == null){
            errors.add(new ValidatorDto("amount", "bo'sh maydon"));
        }else if (productDto.getAmount() < 0){
            errors.add(new ValidatorDto("amount", "manfiy qiymat"));
        }

        if (productDto.getPrice() == null){
            errors.add(new ValidatorDto("price", "bo'sh maydon"));
        }else if (productDto.getPrice() < 0){
            errors.add(new ValidatorDto("price", "manfiy qiymat"));
        }

        if(productDto.getType() == null || productDto.getType().getId() == null){
            errors.add(new ValidatorDto("type", "bo'sh maydon"));
        }

        return errors;
    }
}
