package uz.nt.firstspring.service;

import uz.nt.firstspring.dto.ProductDto;

import java.util.List;

public interface ExcelService {

    void exportLessProducts(List<ProductDto> products);

}
