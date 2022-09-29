package uz.nt.firstspring.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_id_seq")
    @SequenceGenerator(name = "auth_id_seq", sequenceName = "auth_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    private String name;
}
