package tn.esprit.spring;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

@ExtendWith(MockitoExtension.class)  // Enable Mockito for this test class
public class InstructorServicesImplTestMock {

	@Mock
	private IInstructorRepository instructorRepository;  // Mock the repository

	@InjectMocks
	private InstructorServicesImpl instructorServices;  // Inject the mock into the service

	private Instructor instructor;

	@BeforeEach
	public void setup() {
		// Create a test instructor
		instructor = new Instructor(null, "John", "Doe", LocalDate.now(), null);
	}

	// Test: Add an instructor
	@Test
	public void testAddInstructor() {
		when(instructorRepository.save(instructor)).thenReturn(instructor);

		Instructor savedInstructor = instructorServices.addInstructor(instructor);

		assertNotNull(savedInstructor);
		assertEquals("John", savedInstructor.getFirstName());
		verify(instructorRepository, times(1)).save(instructor);  // Verify save was called
	}

	// Test: Retrieve all instructors
	@Test
	public void testRetrieveAllInstructors() {
		List<Instructor> instructors = new ArrayList<>();
		instructors.add(instructor);
		when(instructorRepository.findAll()).thenReturn(instructors);

		List<Instructor> result = instructorServices.retrieveAllInstructors();

		assertEquals(1, result.size());
		verify(instructorRepository, times(1)).findAll();  // Verify findAll was called
	}

	// Test: Retrieve an instructor by ID
	@Test
	public void testRetrieveInstructor() {
		when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

		Instructor foundInstructor = instructorServices.retrieveInstructor(1L);

		assertNotNull(foundInstructor);
		assertEquals("John", foundInstructor.getFirstName());
		verify(instructorRepository, times(1)).findById(1L);  // Verify findById was called
	}

	// Test: Update an instructor
	@Test
	public void testUpdateInstructor() {
		instructor.setFirstName("Johnathan");
		when(instructorRepository.save(instructor)).thenReturn(instructor);

		Instructor updatedInstructor = instructorServices.updateInstructor(instructor);  // Assuming updateInstructor uses save

		assertEquals("Johnathan", updatedInstructor.getFirstName());
		verify(instructorRepository, times(1)).save(instructor);  // Verify save was called
	}

	// Test: Remove an instructor
	@Test
	public void testRemoveInstructor() {
		doNothing().when(instructorRepository).deleteById(1L);

		instructorServices.retrieveInstructor(1L);

		verify(instructorRepository, times(1)).deleteById(1L);  // Verify deleteById was called
	}
}
