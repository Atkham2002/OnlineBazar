package uz.nt.firstspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.firstspring.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByAge(Integer age);

    /**
     * Returns a list of all students between the start and end ages
     * Reminder :
     *      In order to avoid logical errors,
     *      startAge and endAge should work according
     *      to this principle -> (startAge < endAge)
     * @param startAge
     * @param endAge
     * @return
     */
    List<Student> findAllByAgeBetween(Integer startAge, Integer endAge);

    /**
     * return student list via (like course)
     * Reminder :
     *      To avoid logical errors,
     *      put (%) at the beginning, end, or both sides of the course
     * @param course
     * @return
     */
    List<Student> findAllByCourseLike(String course);

    /**
     * return list of students through course
     * Here it is checked that the incoming information
     * is exactly the same as the original
     *  -> this.course.equals(course)
     * @param course
     * @return
     */
    List<Student> findAllByCourse(String course);

    /**
     * Delete student by id
     * Returns ResponseDto
     * Reminder :
     *      ResponseDto class
     *      fields code, success, message.
     *
     * Success if the code returns 1
     * otherwise error
     * @param id
     * @return
     */
    void deleteAllById(Integer id);

    @Query(value = "SELECT COUNT(*) FROM Student", nativeQuery = true)
    Integer countAllStudents();

    @Modifying
    @Query("UPDATE Student SET course = :sd_course WHERE id = :sd_id")
    Student updateCourseById(@Param("sd_course") String course, @Param("sd_id") Integer id);

    @Query("SELECT s FROM Student s WHERE s.course = ?1 AND s.age BETWEEN ?2 AND ?3")
    List<Student> findAllByCourseAndAgeBetween(String course, Integer startAge, Integer endAge);

}
