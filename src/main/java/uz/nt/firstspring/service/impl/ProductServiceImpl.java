package uz.nt.firstspring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.ValidatorDto;
import uz.nt.firstspring.entity.Product;
import uz.nt.firstspring.repository.ProductRepository;
import uz.nt.firstspring.repository.ProductTypesRepository;
import uz.nt.firstspring.repository.impl.ProductRepositoryImpl;
import uz.nt.firstspring.service.ExcelService;
import uz.nt.firstspring.service.ProductService;
import uz.nt.firstspring.service.ValidatorService;
import uz.nt.firstspring.service.mapper.MapperProduct;
import uz.nt.firstspring.service.mapper.ProductMapper;
import uz.nt.firstspring.service.mapper.UnitMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ValidatorService validator;
    private final ProductTypesRepository productTypesRepository;
    private final UnitMapper unitMapper;
    private final ExcelService excelService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductRepositoryImpl productRepositoryImpl, ValidatorService validator, ProductTypesRepository productTypesRepository, UnitMapper unitMapper, ExcelService excelService) {
        this.productRepository = productRepository;
//        productMapper = Mappers.getMapper(ProductMapper.class);
        this.productMapper = productMapper;
        this.productRepositoryImpl = productRepositoryImpl;
        this.validator = validator;
        this.productTypesRepository = productTypesRepository;
        this.unitMapper = unitMapper;
        this.excelService = excelService;
    }

    @Override
    public ResponseDto<String> addProduct(ProductDto dto) {
        try {
            List<ValidatorDto> errors = validator.validateProduct(dto);
            if (!errors.isEmpty()){
                return ResponseDto.<String>builder().errors(errors).message("Validator error").code(-3).data("Successfully failed!").build();
            }

            if (!productTypesRepository.existsById(dto.getType().getId())){
                return ResponseDto.<String>builder().data("Successfully failed").message("Bunday mahsulot turi mavjud emas").code(-2).build();
            }

            productRepository.save(productMapper.toEntity(dto));
            return ResponseDto.<String>builder().success(true).message("Muvaffaqiyatli saqlandi").build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<ProductDto> findById(Integer id){
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isEmpty()){
                return ResponseDto.<ProductDto>builder().success(false).message("NOT FOUND").build();
            }
            Product product = productOptional.get();

            ProductDto productDto = productMapper.toDto(product);

            return ResponseDto.<ProductDto>builder().success(true).message("OK").data(productDto).build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<Page<ProductDto>> getAll(Integer page, Integer size) {
        PageRequest request = PageRequest.of(page, size);

        Page<ProductDto> list = productRepository.findAll(request).map(m -> MapperProduct.toDto(m,unitMapper));

        return ResponseDto.<Page<ProductDto>>builder().code(0).message("OK").success(true).data(list).build();
    }

    @Override
    public ResponseDto<Page<ProductDto>> search(String name, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDto> result = productRepository.findAllByNameContainingIgnoreCase(name, pageRequest)
                .map(m -> MapperProduct.toDto(m,unitMapper));
        return ResponseDto.<Page<ProductDto>>builder().message("OK").data(result).success(true).code(0).build();
    }

    @Override
    public ResponseDto<Page<ProductDto>> byParams(MultiValueMap<String, String> params) {
        if (!params.containsKey("page") || !params.containsKey("size")){
            return ResponseDto.<Page<ProductDto>>builder().success(false).message("Page or size is null").code(-1).build();
        }

        PageRequest pageRequest = PageRequest.of(Integer.parseInt(params.getFirst("page")), Integer.parseInt(params.getFirst("size")));

        Page<ProductDto> result = productRepositoryImpl.findAllByParam(pageRequest, params).map(m -> MapperProduct.toDto(m,unitMapper));

        return ResponseDto.<Page<ProductDto>>builder().data(result).success(true).code(0).message("OK").build();
    }

    @Override
    public void exportLessThanLimit() {
        List<Product> products = productRepository.getLessThanLimit();
        if(products.isEmpty()) return;

        List<ProductDto> productDtos = products.stream().map(p -> MapperProduct.toDto(p, unitMapper)).collect(Collectors.toList());

        excelService.exportLessProducts(productDtos);
    }


}
