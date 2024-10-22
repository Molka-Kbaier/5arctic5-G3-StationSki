package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseServicesImplTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllCourses() {
        // Arrange
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setNumCourse(1L);
        courses.add(course1);

        Course course2 = new Course();
        course2.setNumCourse(2L);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseServices.retrieveAllCourses();

        // Assert
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testAddCourse() {
        // Arrange
        Course course = new Course();
        course.setNumCourse(1L);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.addCourse(course);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getNumCourse());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testUpdateCourse() {
        // Arrange
        Course course = new Course();
        course.setNumCourse(1L);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.updateCourse(course);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getNumCourse());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testRetrieveCourseFound() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        course.setNumCourse(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        // Assert
        assertNotNull(result);
        assertEquals(courseId, result.getNumCourse());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    public void testRetrieveCourseNotFound() {
        // Arrange
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        // Assert
        assertNull(result);
        verify(courseRepository, times(1)).findById(courseId);
    }
}
