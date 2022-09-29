package uz.nt.firstspring.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import uz.nt.firstspring.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl {

    private final EntityManager entityManager;

    public Page<Product> findAllByParam(Pageable pageRequest, MultiValueMap<String, String> params){
        StringBuilder queryParams = new StringBuilder();
        queryBuilder(params, queryParams);

        String queryStr = "SELECT * FROM PRODUCT t where 1 = 1 " + queryParams;
        String countStr = "SELECT count(1) from PRODUCT where 1=1 " + queryParams;

        Query query = entityManager.createNativeQuery(queryStr, Product.class);
        Query countQuery = entityManager.createNativeQuery(countStr, Integer.class);

        setParameter(params, query);
        setParameter(params, countQuery);

        if (pageRequest != null){
            query.setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber());
            query.setMaxResults(pageRequest.getPageSize());
        }

        Integer count = countQuery.getFirstResult();
        List<Product> result = query.getResultList();

        return new PageImpl(result, pageRequest, count);
    }

    private void queryBuilder(MultiValueMap<String, String> params, StringBuilder builder){
        if (params.containsKey("name")){
            builder.append(" AND t.name = :name");
        }
        if (params.containsKey("price")){
            builder.append(" AND t.price = :price");
        }
        if (params.containsKey("amount")){
            builder.append(" AND t.amount = :amount");
        }
    }

    private void setParameter(MultiValueMap<String, String> params, Query query){
        if (params.containsKey("name")){
            query.setParameter("name", params.getFirst("name"));
        }
        if (params.containsKey("price")){
            query.setParameter("price", Double.parseDouble(params.getFirst("price")));
        }
        if (params.containsKey("amount")){
            query.setParameter("amount", Integer.parseInt(params.getFirst("amount")));
        }
    }

}
