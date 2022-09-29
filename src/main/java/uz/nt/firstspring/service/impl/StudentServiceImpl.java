package uz.nt.firstspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uz.nt.firstspring.components.StringHelper;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.StudentDto;
import uz.nt.firstspring.entity.Student;
import uz.nt.firstspring.repository.StudentRepository;
import uz.nt.firstspring.service.StudentService;

import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Lazy(value = false)
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private final StringHelper stringHelper;

    public StudentServiceImpl(StringHelper stringHelper) {
        this.stringHelper = stringHelper;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

//    @PostConstruct
//    public void Amirxon() throws SQLException {
//        System.out.println("Initialized service bean: " + new Date());
//        System.out.println(Shohrux);
//        System.out.println(korzinka.getSchema());
//    }

    @PreDestroy
    public void Shohruh(){
        System.out.println("Destroyed service bean: " + new Date());
    }

//    public ResponseDto<StudentDto> student(){
//        return ResponseDto.<StudentDto>builder().data(Shohrux).success(true).message("OK").build();
//    }

    @Override
    public ResponseDto addStudent(StudentDto studentDto) {

        Student student = Student.builder()
                .age(studentDto.getAge())
                .birthDate(stringHelper.dateToString(studentDto.getBirthDate()))
                .course(studentDto.getCourse())
                .name(studentDto.getName())
                .id(studentDto.getId())
                .build();

        studentRepository.save(student);

        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Successfully saved!")
                .build();
    }

    @Override
    public List<StudentDto> getAll() {
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(s -> StudentDto.builder()
                        .age(s.getAge())
                        .birthDate(stringHelper.parseToDate(s.getBirthDate()))
                        .course(s.getCourse())
                        .name(s.getName())
                        .id(s.getId())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public ResponseDto updateStudent(StudentDto studentDto) {

//
//
//            if (s.getId().equals(studentDto.getId())){
//                if (studentDto.getName() != null) s.setName(studentDto.getName());
//                if (studentDto.getAge() != null) s.setAge(studentDto.getAge());
//                if (studentDto.getCourse() != null) s.setCourse(studentDto.getCourse());
//                if (studentDto.getBirthDate() != null) s.setBirthDate(studentDto.getBirthDate());
//
//                return ResponseDto.builder()
//                        .code(0)
//                        .success(true)
//                        .message("Successfully updated!")
//                        .build();
//            }

        return ResponseDto.builder()
                .code(-1)
                .success(false)
                .message("Data not found!")
                .build();
    }

    @Override
    public ResponseDto deleteStudent(Integer id) {
//        CopyOnWriteArrayList<StudentDto> cw = new CopyOnWriteArrayList<>(students);
        boolean deleted = false;
//        for (StudentDto student : cw){
//            if (student.getId().equals(id)) {
//                students.remove(student);
//                deleted = true;
//            }
//        }

        return deleted ? ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Successfully deleted!")
                .build()
                :
                         ResponseDto.builder()
                .code(-1)
                .success(false)
                .message("Data not found!")
                .build();
    }
}
