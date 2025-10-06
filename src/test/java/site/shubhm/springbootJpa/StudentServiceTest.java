package site.shubhm.springbootJpa;

import site.shubhm.springbootJpa.repository.StudentRepository;
import site.shubhm.springbootJpa.entities.Student;
import site.shubhm.springbootJpa.services.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Using Mockito extension for JUnit 5
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    // Mock the repository dependency
    @Mock
    private StudentRepository studentRepository;

    // Inject the mocks into the service
    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        // Create sample student objects for tests
        student1 = new Student();
        student1.setStudentId(1);
        student1.setStudentName("Alice");
        student1.setStudentCity("Wonderland");
        student1.setAbout("Curious adventurer");

        student2 = new Student();
        student2.setStudentId(2);
        student2.setStudentName("Bob");
        student2.setStudentCity("Builderland");
        student2.setAbout("Can fix it");
    }

    @AfterEach
    void tearDown() {
        student1 = null;
        student2 = null;
    }

    @Test
    void testGetStudentById_Found() {
        // Given: The repository will return a student when findById is called
        when(studentRepository.findById(1)).thenReturn(Optional.of(student1));

        // When: The service method is called
        Student found = studentService.getStudent(1);

        // Then: The correct student is returned
        assertThat(found).isNotNull();
        assertThat(found.getStudentName()).isEqualTo("Alice");
        // Verify that the repository method was called exactly once
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void testGetStudentById_NotFound() {
        // Given: The repository will return an empty Optional
        when(studentRepository.findById(99)).thenReturn(Optional.empty());

        // When: The service method is called with an invalid ID
        Student found = studentService.getStudent(99);

        // Then: The result should be null
        assertThat(found).isNull();
        verify(studentRepository, times(1)).findById(99);
    }


    @Test
    void testGetAllStudents() {
        // Given: The repository will return a list of students
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        // When: The service method is called
        List<Student> students = studentService.getAllStudents();

        // Then: The list should contain the correct students
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
        assertThat(students.get(0).getStudentName()).isEqualTo("Alice");
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testAddStudent() {
        // Given: The repository will return the saved student when save is called
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        // When: The service method is called to add a student
        Student created = studentService.addStudent(student1);

        // Then: The returned student should match the input
        assertThat(created).isNotNull();
        assertThat(created.getStudentName()).isEqualTo("Alice");
        verify(studentRepository, times(1)).save(student1);
    }

    @Test
    void testDeleteStudent() {
        // Given: An ID of a student to delete
        int studentId = 1;
        // Mock the behavior of deleteById - it doesn't return anything
        doNothing().when(studentRepository).deleteById(studentId);

        // When: The service method is called
        studentService.deleteStudent(studentId);

        // Then: Verify that the repository's deleteById method was called with the correct ID
        verify(studentRepository, times(1)).deleteById(studentId);
    }
}
