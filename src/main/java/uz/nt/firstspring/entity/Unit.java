package uz.nt.firstspring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Unit {
    
    @Id
    @GeneratedValue(generator = "unit_id_seq")
    @SequenceGenerator(sequenceName = "unit_id_seq", name = "unit_id_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    private String name;

    @Column(name = "short_name")
    private String shortName;

}
