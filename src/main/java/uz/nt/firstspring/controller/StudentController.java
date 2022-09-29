package uz.nt.firstspring.controller;

import org.springframework.web.bind.annotation.*;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.StudentDto;
import uz.nt.firstspring.entity.Student;
import uz.nt.firstspring.service.impl.PeopleServiceImpl;
import uz.nt.firstspring.service.impl.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final PeopleServiceImpl peopleService;

    public StudentController(StudentServiceImpl studentService, PeopleServiceImpl peopleService) {
        this.peopleService = peopleService;
        System.out.println("Student Controller created");
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseDto addStudent(@RequestBody StudentDto studentDto){
        return studentService.addStudent(studentDto);
    }

    @GetMapping
    public List<StudentDto> getStudents(){
        return studentService.getAll();
    }

    //Patch - Put
    @PatchMapping
    //UpdateStudent
    public ResponseDto updateStudent(@RequestBody StudentDto studentDto){
        return studentService.updateStudent(studentDto);
    }

//    @GetMapping("/check")
//    public ResponseDto<StudentDto> check(){
//        return studentService.student();
//    }

//    @PutMapping("/change")
//    public ResponseDto<StudentDto> change(@RequestParam String name){
//        return peopleService.std(name);
//    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }
}
