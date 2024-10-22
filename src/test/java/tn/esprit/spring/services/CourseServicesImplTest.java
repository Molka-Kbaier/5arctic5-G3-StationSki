package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CourseServicesImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CourseServicesImplTest.class);

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("Setting up test for CourseServicesImpl");
    }

    @Test
     void testRetrieveAllCourses() {
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

        logger.info("Retrieved all courses: {}", result);

        // Assert
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
        logger.info("Successfully verified retrieval of all courses");
    }

    @Test
     void testAddCourse() {
        // Arrange
        Course course = new Course();
        course.setNumCourse(1L);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.addCourse(course);

        logger.info("Added course: {}", result);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getNumCourse());
        verify(courseRepository, times(1)).save(course);
        logger.info("Successfully verified addition of course");
    }

    @Test
     void testUpdateCourse() {
        // Arrange
        Course course = new Course();
        course.setNumCourse(1L);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.updateCourse(course);

        logger.info("Updated course: {}", result);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getNumCourse());
        verify(courseRepository, times(1)).save(course);
        logger.info("Successfully verified update of course");
    }

    @Test
     void testRetrieveCourseFound() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        course.setNumCourse(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        logger.info("Retrieved course: {}", result);

        // Assert
        assertNotNull(result);
        assertEquals(courseId, result.getNumCourse());
        verify(courseRepository, times(1)).findById(courseId);
        logger.info("Successfully verified retrieval of course found");
    }

    @Test
     void testRetrieveCourseNotFound() {
        // Arrange
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        logger.info("Attempted to retrieve course with ID: {} - Result: {}", courseId, result);

        // Assert
        assertNull(result);
        verify(courseRepository, times(1)).findById(courseId);
        logger.info("Successfully verified retrieval of course not found");
    }
}
