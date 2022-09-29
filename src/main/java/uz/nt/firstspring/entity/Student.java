package uz.nt.firstspring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Student")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "findAll", query = "select t from Student t"),
        @NamedQuery(name = "findAllById", query = "select t from Student t where t.id = :id")
})
public class Student {

    @Id
    @GeneratedValue(generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "course")
    private String course;
}
