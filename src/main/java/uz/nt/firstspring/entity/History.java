package uz.nt.firstspring.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_seq")
    @SequenceGenerator(name = "history_id_seq", sequenceName = "history_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String url;
    private String remoteAddress;
    private Long userId;
    private Date date;
    @Length(max = 5000)
    @Column(length = 5000)
    private String originalData;
    @Length(max = 50000)
    @Column(length = 50000)
    private String responseData;
    @Length(max = 500)
    @Column(length = 500)
    private String error;
    private String requestMethod;
}
