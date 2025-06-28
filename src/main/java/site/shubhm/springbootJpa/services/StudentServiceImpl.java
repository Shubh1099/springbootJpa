package site.shubhm.springbootJpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.shubhm.springbootJpa.entities.Student;
import site.shubhm.springbootJpa.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public boolean addStudentDetails(Student std) {

        boolean status = false;

        try {
            studentRepository.save(std);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    @Override
    public List<Student> getAllStudentDetails() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {

        Optional<Student> optional = studentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }

    }

    @Override
    public boolean updateDetails(Long Id, String password) {
        Student std = getStudentById(Id);
        if (std != null) {
            std.setPassword(password);
            studentRepository.save(std);
            return true;
        } else {
            return false;

        }

    }

    @Override
    public boolean deleteStudent(Long Id) {

        boolean status = false;
        try {
            studentRepository.deleteById(Id);
            status= true;
        } catch (Exception e) {
            e.printStackTrace();
            status=false;
        }
        return status;
    }
}
