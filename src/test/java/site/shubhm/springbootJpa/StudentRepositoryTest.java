package com.jpa.test.dao;

import com.jpa.test.entities.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setUp() {
        // Given: a student object to be used in tests
        student = new Student();
        student.setStudentName("John Doe");
        student.setStudentCity("New York");
        student.setAbout("A test student");
        studentRepository.save(student);
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        studentRepository.deleteAll();
        student = null;
    }

    @Test
    void testSaveStudent_Success() {
        // When: a new student is saved
        Student newStudent = new Student();
        newStudent.setStudentName("Jane Doe");
        newStudent.setStudentCity("London");
        newStudent.setAbout("Another test student");
        Student savedStudent = studentRepository.save(newStudent);

        // Then: the saved student should have a non-null ID and match the input data
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getStudentId()).isPositive();
        assertThat(savedStudent.getStudentName()).isEqualTo("Jane Doe");
    }

    @Test
    void testFindById_Success() {
        // When: searching for a student by their ID
        Optional<Student> foundStudent = studentRepository.findById(student.getStudentId());

        // Then: the student should be found
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getStudentName()).isEqualTo("John Doe");
    }

    @Test
    void testFindById_NotFound() {
        // When: searching for a student with a non-existent ID
        Optional<Student> foundStudent = studentRepository.findById(999);

        // Then: the optional should be empty
        assertThat(foundStudent).isNotPresent();
    }

    @Test
    void testFindAllStudents_Success() {
        // Given: another student
        Student student2 = new Student();
        student2.setStudentName("Peter Jones");
        student2.setStudentCity("Paris");
        student2.setAbout("A third test student");
        studentRepository.save(student2);

        // When: fetching all students
        List<Student> students = studentRepository.findAll();

        // Then: the list should contain two students
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
    }

    @Test
    void testDeleteStudent_Success() {
        // When: the student is deleted
        studentRepository.deleteById(student.getStudentId());

        // Then: the student should no longer be in the database
        Optional<Student> deletedStudent = studentRepository.findById(student.getStudentId());
        assertThat(deletedStudent).isNotPresent();
    }
}
