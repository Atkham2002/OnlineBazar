package uz.nt.firstspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nt.firstspring.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "select t from Product t where t.amount < t.type.limit")
    List<Product> getLessThanLimit();
}
