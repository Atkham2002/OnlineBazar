package uz.nt.firstspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.firstspring.entity.ProductTypes;

@Repository
public interface ProductTypesRepository extends JpaRepository<ProductTypes, Integer> {
}
