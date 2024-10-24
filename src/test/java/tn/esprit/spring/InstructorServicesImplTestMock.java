package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

public class InstructorServicesImplTestMock {

    @Mock
    private IInstructorRepository instructorRepository;  // Mock the instructor repository

    @Mock
    private ICourseRepository courseRepository;  // Mock the course repository

    @InjectMocks
    private InstructorServicesImpl instructorServices;  // Inject mocks into the service

    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        instructor = new Instructor(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        course = new Course(1L, 1, TypeCourse.COLLECTIVE_CHILDREN, null, 10F, 1, Collections.emptySet());
    }

    @Test
    void testAddInstructor() {
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);  // Mock save behavior

        Instructor savedInstructor = instructorServices.addInstructor(instructor);  // Call the method to test

        assertEquals(instructor, savedInstructor);  // Verify the saved instructor
        verify(instructorRepository, times(1)).save(instructor);  // Verify that save was called once
    }

    @Test
    void testGetInstructorsByCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));  // Mock course retrieval
        instructor.setCourses(new HashSet<>(Collections.singletonList(course)));  // Assign the course to the instructor
        when(instructorRepository.findAll()).thenReturn(List.of(instructor));  // Mock instructor retrieval

        List<Instructor> instructors = instructorServices.getInstructorsByCourse(1L);  // Call the method to test

        assertEquals(1, instructors.size());  // Verify the size of the list
        assertEquals(instructor, instructors.get(0));  // Verify the instructor is as expected
    }

    @Test
    void testGetInstructorsByCourse_NoInstructors() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));  // Mock course retrieval
        when(instructorRepository.findAll()).thenReturn(List.of());  // Mock no instructors returned

        List<Instructor> instructors = instructorServices.getInstructorsByCourse(1L);  // Call the method to test

        assertTrue(instructors.isEmpty());  // Verify the list is empty
    }
}
