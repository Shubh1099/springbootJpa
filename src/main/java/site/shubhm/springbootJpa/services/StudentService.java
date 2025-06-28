package site.shubhm.springbootJpa.services;

import org.springframework.stereotype.Service;
import site.shubhm.springbootJpa.entities.Student;

import java.util.List;

@Service
public interface StudentService {

    public boolean addStudentDetails(Student std);
    public List<Student> getAllStudentDetails();
    public Student getStudentById(Long Id);
    public boolean updateDetails(Long Id, String password);
    public boolean deleteStudent(Long Id);
}
