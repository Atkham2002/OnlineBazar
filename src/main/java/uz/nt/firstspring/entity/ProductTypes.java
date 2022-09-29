package uz.nt.firstspring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_types")
public class ProductTypes {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Product> products;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "limit_amount")
    private Double limit;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unitId;
}
