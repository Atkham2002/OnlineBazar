package uz.nt.firstspring.service;

import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ValidatorDto;

import java.util.List;

public interface ValidatorService {

    List<ValidatorDto> validateProduct(ProductDto productDto);
}
