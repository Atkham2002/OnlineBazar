package uz.nt.firstspring.service;

import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.StudentDto;

import java.util.List;

public interface StudentService {
    /**
     * Add new Student to list
     * @param studentDto Info about student
     * @return ResponseDto - code -1 when exception occurs, 0 when all is OK.
     */
    ResponseDto addStudent(StudentDto studentDto);

    List<StudentDto> getAll();

    ResponseDto updateStudent(StudentDto studentDto);

    ResponseDto deleteStudent(Integer id);
}
//SOLID
/*
* S - Single responsibility
* O - Open closed principle
* L - Liskov substitution
* I - Interface aggregation
* D - Dependency inversion
* */
