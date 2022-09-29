package uz.nt.firstspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.firstspring.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
