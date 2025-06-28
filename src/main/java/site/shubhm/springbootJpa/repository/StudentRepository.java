package site.shubhm.springbootJpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.shubhm.springbootJpa.entities.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
