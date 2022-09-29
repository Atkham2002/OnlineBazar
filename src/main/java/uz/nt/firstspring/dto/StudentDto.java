package uz.nt.firstspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Integer id;
    private Integer age;
    private String name;
    private Date birthDate;
    private String course;

    public String toString(){
        return "Student id : " + id + "\n" +
                "Student age : " + age + "\n" +
                "Student name : " + name + "\n" +
                "Student birthdate : " + birthDate + "\n" +
                "Student course : " + course + "\n";
    }
}
