package tn.esprit.spring;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

@SpringBootTest  // Load the Spring context with MySQL
@TestMethodOrder(OrderAnnotation.class)  // Define the order of tests
@Transactional  // Each test is isolated in a transaction
@Rollback(true)  // Changes are rolled back after each test
public class InstructorServicesImplTest {
    @Autowired
    private InstructorServicesImpl instructorServices;

    @Autowired
    private IInstructorRepository instructorRepository;

    // Test: Add an instructor
    @Test
    @Order(1)  // This test will run first
    public void testAddInstructor() {
        Instructor instructor = new Instructor(null, "Jane", "Doe", LocalDate.now(), null);
        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        assertNotNull(savedInstructor);
        assertNotNull(savedInstructor.getNumInstructor());  // Check if ID is generated
        assertEquals("Jane", savedInstructor.getFirstName());  // Check first name
    }

    // Test: Retrieve all instructors
    @Test
    @Order(2)  // This test will run second
    public void testRetrieveAllInstructors() {
        Instructor instructor1 = new Instructor(null, "Alice", "Smith", LocalDate.now(), null);
        Instructor instructor2 = new Instructor(null, "Bob", "Brown", LocalDate.now(), null);
        instructorServices.addInstructor(instructor1);
        instructorServices.addInstructor(instructor2);

        List<Instructor> instructors = instructorServices.retrieveAllInstructors();
        assertTrue(instructors.size() >= 2);
    }

    // Test: Retrieve an instructor by ID
    @Test
    @Order(3)  // This test will run third
    public void testRetrieveInstructor() {
        Instructor instructor = new Instructor(null, "Charlie", "Johnson", LocalDate.now(), null);
        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        Instructor foundInstructor = instructorServices.retrieveInstructor(savedInstructor.getNumInstructor());
        assertNotNull(foundInstructor);
        assertEquals("Charlie", foundInstructor.getFirstName());
    }

    // Test: Update an instructor
    @Test
    @Order(4)  // This test will run fourth
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor(null, "David", "Williams", LocalDate.now(), null);
        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        // Update the instructor
        savedInstructor.setFirstName("David Updated");
        Instructor updatedInstructor = instructorServices.updateInstructor(savedInstructor);  // Assuming updateInstructor uses save

        // Check that the update was successful
        Instructor foundInstructor = instructorServices.retrieveInstructor(updatedInstructor.getNumInstructor());
        assertNotNull(foundInstructor);
        assertEquals("David Updated", foundInstructor.getFirstName());
    }

    // Test: Remove an instructor
    @Test
    @Order(5)  // This test will run last
    public void testRemoveInstructor() {
        Instructor instructor = new Instructor(null, "Eve", "Davis", LocalDate.now(), null);
        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        instructorServices.retrieveInstructor(savedInstructor.getNumInstructor());

        Instructor deletedInstructor = instructorServices.retrieveInstructor(savedInstructor.getNumInstructor());
        assertNull(deletedInstructor);  // Verify that the instructor has been deleted
    }
}
