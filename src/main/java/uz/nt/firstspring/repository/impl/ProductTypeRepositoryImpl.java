package uz.nt.firstspring.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uz.nt.firstspring.entity.ProductTypes;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductTypeRepositoryImpl {

    private final EntityManager entityManager;

    public List<ProductTypes> findAll(){
        Query query = entityManager.createQuery("select pt from ProductTypes pt join fetch pt.products");

        return query.getResultList();
    }


}
