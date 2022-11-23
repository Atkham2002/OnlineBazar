package uz.nt.firstspring.external;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.ProductDto;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/product")
@PreAuthorize("hasAnyAuthority('READ')")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private HashMap<String, Integer> addresses = new HashMap<>();

    private final ProductService productService;

    /**
     * Add product.
     * @return ResponseDto<String> with message.
     * If there is any problem, returns ResponseDto with success is false and failed message
     */
    @Operation(summary = "Add product with validation")
    @PostMapping("/api/v1")
//    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseDto<String> addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.addProduct(productDto);
    }

    /**
     * Get all product list by pagination.
     * @param page Number of page
     * @param size Size of elements in one page
     * @return ResponseDto with ProductDto page.
     * If there is any problem, returns ResponseDto with success is false and data is null
     */
    @Tag(name="External", description = "Returns list of products by page and size")
    @Operation(summary = "Get all products by page and size", description = "Returns all products by page and size")
    @GetMapping
    public ResponseDto<Page<ProductDto>> getAll(@RequestParam Integer page, @RequestParam Integer size) throws NoSuchMethodException {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page + 1);
        params.put("size", size);

        Method getAll = ProductController.class.getMethod("getAll", Integer.class, Integer.class);

        Link link = linkTo(getAll)
                .withRel("Keyingi ma'lumotlar")
                .expand(params);

        ResponseDto<Page<ProductDto>> all = productService.getAll(page, size);
//        all.add(link);

        all.getData().stream().forEach(p -> {
            try {
                p.add(
                        linkTo(ProductController.class.getMethod("findById", Integer.class))
//                                .slash(p.getId())
                                .withRel(IanaLinkRelations.ITEM)
                                .expand(p.getId())
                );
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        return all;
    }

    /**
     * Get all product list by pagination.
     * @param id Id of product
     * @return ResponseDto with ProductDto.
     * If there is any problem, returns ResponseDto with success is false and data is null
     */
    @Tag(name="External", description = "Returns list of products by page and size")
    @Operation(summary = "Find product by id", description = "Returns product by id")
    @GetMapping("/api/v1/{id}")
    public ResponseDto<ProductDto> findById(@PathVariable Integer id){
        Link link = Link.of("/api/product?page=0&size=10");

//        Map<String, Integer> params = new HashMap<>();
//        params.put("page", 0);
//        params.put("size", 10);
//
//        link = link.expand(params);

        System.out.println(link.getHref());

        return productService.findById(id);
    }

    /**
     * Search all products by params, which are already known.
     * @param name Name of product
     * @param page Number of page
     * @param size Size of elements in one page
     * @param price Price of product
     * @return ResponseDto with ProductDto page.
     * If there is any problem, returns ResponseDto with success is false and data is null
     */
    @Operation(summary = "search by parameters", description = "Search products by name and price and returns products with pagination")
    @GetMapping("/api/v1/search")
    public ResponseDto<Page<ProductDto>> search(@RequestParam String name, @RequestParam Integer page, @RequestParam Integer size, @RequestParam Double price){
        return productService.search(name, page, size);
    }

    /**
     * Search all products by params.
     * @param params Params is universal parameters. You need to enter page and size of products. You can enter any parameter of product for search. For example, product name, price, amount, etc.
     * @return ResponseDto with ProductDto page.
     * If there is any problem, returns ResponseDto with success is false and data is null
     */
    @Operation(summary = "Search by param", description = "Returns products page. You need to enter page and size of products. You can enter any parameter of product for search. For example, product name, price, amount, etc.")
    @GetMapping(value = "/api/v1/by-param", produces = "application/json")
    public ResponseDto<Page<ProductDto>> byParams(@RequestParam MultiValueMap<String, String> params){
        return productService.byParams(params);
    }

    /**
     * Export to excel file
     * @return List of products in excel file.
     * If there is any problem, returns error
     */
    @Operation(summary = "Export excel file", description = "Returns excel file with list products")
    @GetMapping(value = "/api/v1/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public void export(HttpServletRequest req, HttpServletResponse response){
        productService.export(req, response);
    }
}
