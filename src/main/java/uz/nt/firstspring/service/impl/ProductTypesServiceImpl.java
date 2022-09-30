package uz.nt.firstspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ProductTypeDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.entity.ProductTypes;
import uz.nt.firstspring.repository.ProductTypesRepository;
import uz.nt.firstspring.repository.impl.ProductTypeRepositoryImpl;
import uz.nt.firstspring.service.ProductTypesService;
import uz.nt.firstspring.service.mapper.MapperProductType;
import uz.nt.firstspring.service.mapper.ProductTypesMapper;
import uz.nt.firstspring.service.mapper.UnitMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTypesServiceImpl implements ProductTypesService {

    private final ProductTypesRepository repository;
    private final ProductTypesMapper mapper;
    private final ProductTypeRepositoryImpl productTypeRepositoryImpl;
    private final UnitMapper unitMapper;

    @Override
    public ResponseDto<String> addProduct(ProductTypeDto dto) {
        try {
            repository.save(mapper.toEntity(dto));
            return ResponseDto.<String>builder().success(true).message("Muvaffaqiyatli saqlandi").build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Transactional
    public List<ProductTypes> getProductTypes() {
        return repository.findAll();
    }

    @Override
    public ResponseDto<List<ProductTypeDto>> getAll() {
        List<ProductTypes> productTypes = repository.findAll();
        List<ProductTypeDto> productTypeDto = productTypes.stream()
                .map(m -> MapperProductType.toDto(m, unitMapper))
                .collect(Collectors.toList());

        return ResponseDto.<List<ProductTypeDto>>builder().message("OK").success(true).data(productTypeDto).build();
    }

    public String getWithLazy(){
        List<ProductTypes> productTypes = repository.findAll();
        System.out.println(productTypes.get(0).getName());

        return "ok";
    }

    @Override
    public ResponseDto<ProductTypeDto> getOne(Integer id) {
        Optional<ProductTypes> _productTypes = repository.findById(id);
        if (_productTypes.isEmpty()){
            return ResponseDto.<ProductTypeDto>builder().message("NOT FOUND").data(null).success(false).build();
        }

        return ResponseDto.<ProductTypeDto>builder()
                .message("OK")
                .success(true)
                .data(_productTypes.map(m -> MapperProductType.toDto(m, unitMapper)).get())
                .build();
    }
}
