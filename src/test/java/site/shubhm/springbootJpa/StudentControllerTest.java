package com.jpa.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.test.entities.Student;
import com.jpa.test.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1);
        student.setStudentName("Test Student");
        student.setStudentCity("Test City");
        student.setAbout("About Test Student");
    }

    @Test
    void testGetStudents_ReturnsListOfStudents() throws Exception {
        // Given: The service will return a list containing our test student
        given(studentService.getAllStudents()).willReturn(Collections.singletonList(student));

        // When & Then: Perform a GET request and validate the response
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].studentName").value("Test Student"));
    }

    @Test
    void testGetStudentById_WhenStudentExists_ReturnsStudent() throws Exception {
        // Given: The service will return our test student for a given ID
        given(studentService.getStudent(1)).willReturn(student);

        // When & Then: Perform a GET request with the ID and validate the response
        mockMvc.perform(get("/students/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Test Student"))
                .andExpect(jsonPath("$.studentCity").value("Test City"));
    }

    @Test
    void testGetStudentById_WhenStudentDoesNotExist_ReturnsNotFound() throws Exception {
        // Given: The service will return null for a non-existent ID
        given(studentService.getStudent(99)).willReturn(null);

        // When & Then: Perform a GET request and expect a 404 Not Found status
        mockMvc.perform(get("/students/{id}", 99))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddStudent_CreatesAndReturnsStudent() throws Exception {
        // Given: The service will return the created student
        given(studentService.addStudent(any(Student.class))).willReturn(student);

        // When & Then: Perform a POST request with the student JSON and validate the response
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value("Test Student"));
    }

    @Test
    void testDeleteStudent_DeletesStudentAndReturnsNoContent() throws Exception {
        // When & Then: Perform a DELETE request and expect a 204 No Content status
        mockMvc.perform(delete("/students/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
