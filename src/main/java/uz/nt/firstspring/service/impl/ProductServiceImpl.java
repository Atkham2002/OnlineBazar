package uz.nt.firstspring.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import uz.nt.firstspring.repository.ProductRepository;
import uz.nt.firstspring.repository.ProductTypesRepository;
import uz.nt.firstspring.repository.impl.ProductRepositoryImpl;
import uz.nt.firstspring.service.mapper.MapperProduct;
import uz.nt.firstspring.service.mapper.ProductMapper;
import uz.nt.firstspring.service.mapper.UnitMapper;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.ValidatorDto;
import uz.nt.firstspring.entity.Product;
import uz.nt.firstspring.service.HistoryService;
import uz.nt.firstspring.service.ProductService;
import uz.nt.firstspring.service.ValidatorService;
import uz.nt.firstspring.utils.ParseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ValidatorService validator;
    private final ProductTypesRepository productTypesRepository;
    private final ExcelServiceImpl excelService;
    private final UnitMapper unitMapper;
    private final ResourceBundle bundle;
    private final HistoryService historyService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductRepositoryImpl productRepositoryImpl, ValidatorService validator, ProductTypesRepository productTypesRepository, ExcelServiceImpl excelService, UnitMapper unitMapper, ResourceBundle bundle, HistoryService historyService) {
        this.productRepository = productRepository;
//        productMapper = Mappers.getMapper(ProductMapper.class);
        this.productMapper = productMapper;
        this.productRepositoryImpl = productRepositoryImpl;
        this.validator = validator;
        this.productTypesRepository = productTypesRepository;
        this.excelService = excelService;
        this.unitMapper = unitMapper;
        this.bundle = bundle;
        this.historyService = historyService;
    }

    @Override
    public ResponseDto<String> addProduct(ProductDto dto) {
        try {
            List<ValidatorDto> errors = validator.validateProduct(dto);
            if (!errors.isEmpty()){
                return ResponseDto.<String>builder().errors(errors).message(bundle.getString("response.valid_error")).code(-3).data(bundle.getString("response.failed")).build();
            }

            if (!productTypesRepository.existsById(dto.getType().getId())){
                return ResponseDto.<String>builder().data(bundle.getString("response.failed")).message("Bunday mahsulot turi mavjud emas").code(-2).build();
            }

            productRepository.save(productMapper.toEntity(dto));
            return ResponseDto.<String>builder().success(true).message(bundle.getString("response.success")).build();
        }catch (Exception e){
            Marker marker = MarkerFactory.getMarker("fatal");
            log.error(marker,  "Error while adding new product to database : {}", e.getMessage());
            return ResponseDto.<String>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<ProductDto> findById(Integer id){
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (!productOptional.isPresent()){
                return ResponseDto.<ProductDto>builder().success(false).message(bundle.getString("response.not_found")).build();
            }
            Product product = productOptional.get();

            ProductDto productDto = productMapper.toDto(product);

            return ResponseDto.<ProductDto>builder().success(true).message(bundle.getString("response.success")).data(productDto).build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder().success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<Page<ProductDto>> getAll(Integer page, Integer size) {
        Long historyId = historyService.createHistory(ParseUtil.parseParameters(page, size), "/api/product/", HttpMethod.GET);

        ResponseDto<Page<ProductDto>> response;
        try {
            PageRequest request = PageRequest.of(page, size);

            Page<ProductDto> list = productRepository.findAll(request).map(m -> MapperProduct.toDto(m, unitMapper));

            response = ResponseDto.<Page<ProductDto>>builder().code(0).message(bundle.getString("response.success")).success(true).data(list).build();

            historyService.updateHistory(historyId, response);
        }catch (Exception e){
            log.error("Error while getting all product info by page and size :: {}", e.getMessage());
            response = ResponseDto.<Page<ProductDto>>builder().code(-1).message(bundle.getString("response.error")).build();

            historyService.errorHistory(historyId, response, e.getMessage());
        }

        return response;
    }

    @Override
    public ResponseDto<Page<ProductDto>> search(String name, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDto> result = productRepository.findAllByNameContainingIgnoreCase(name, pageRequest)
                .map(m -> MapperProduct.toDto(m, unitMapper));
        return ResponseDto.<Page<ProductDto>>builder().message(bundle.getString("response.success")).data(result).success(true).code(0).build();
    }

    @Override
    public ResponseDto<Page<ProductDto>> byParams(MultiValueMap<String, String> params) {
        if (!params.containsKey("page") || !params.containsKey("size")){
            return ResponseDto.<Page<ProductDto>>builder().success(false).message("Page or size is null").code(-1).build();
        }

        PageRequest pageRequest = PageRequest.of(Integer.parseInt(params.getFirst("page")), Integer.parseInt(params.getFirst("size")));

        Page<ProductDto> result = productRepositoryImpl.findAllByParam(pageRequest, params).map(m -> MapperProduct.toDto(m, unitMapper));

        return ResponseDto.<Page<ProductDto>>builder().data(result).success(true).code(0).message(bundle.getString("response.success")).build();
    }

    @SneakyThrows
    @Transactional
    @Override
    public void export(HttpServletRequest request, HttpServletResponse response) {
        Stream<Product> products = productRepository.findAllByIdLessThan(1_000_000);
        System.out.println("2131");
        Stream<ProductDto> productDtos = products.map(m -> MapperProduct.toDto(m, unitMapper));
        try {
            excelService.exportProducts(productDtos, request, response);
        }catch (IOException e){
            e.printStackTrace();
            response.sendError(500);
        }
    }

    @Override
    public void exportLessThanLimit() {
        List<Product> products = productRepository.getLessThanLimit();
        if(products.isEmpty()) return;

        List<ProductDto> productDtos = products.stream().map(p -> MapperProduct.toDto(p, unitMapper)).collect(Collectors.toList());

        excelService.exportLessProducts(productDtos);
    }


}
