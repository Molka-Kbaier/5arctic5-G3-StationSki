package tn.esprit.spring;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Charge le contexte Spring avec MySQL
@TestMethodOrder(OrderAnnotation.class)  // Définir l'ordre des tests
@Transactional  // Chaque test est isolé dans une transaction
@Rollback(true)  // Les modifications sont annulées après chaque test
public class RegistrationServicesImplTest {

    @Autowired
    private IRegistrationRepository registrationRepository;

    @Autowired
    private ISkierRepository skierRepository;

    @Autowired
    private ICourseRepository courseRepository;

    private Skier skier;
    private Course course;
    private Registration registration;

    @BeforeEach
    public void setUp() {
        // Initialisation des objets pour les tests
        skier = new Skier();
        skier.setFirstName("Amine");
        skier.setLastName("Kbaier");
        skier.setDateOfBirth(LocalDate.of(2010, 1, 1));  // 14 ans
        skier = skierRepository.save(skier);

        course = new Course();
        course.setNumCourse(1L);
        course = courseRepository.save(course);

        registration = new Registration();
        registration.setNumWeek(5);
    }

    @Test
    @Order(1)
    public void testAddRegistrationAndAssignToSkier_Success() {
        Registration savedRegistration = registrationRepository.save(registration);
        savedRegistration.setSkier(skier);

        assertNotNull(savedRegistration.getNumRegistration());
        assertEquals(skier.getNumSkier(), savedRegistration.getSkier().getNumSkier());
    }

    @Test
    @Order(2)
    public void testAssignRegistrationToCourse_Success() {
        registration.setSkier(skier);
        registration = registrationRepository.save(registration);
        registration.setCourse(course);

        Registration updatedRegistration = registrationRepository.save(registration);

        assertNotNull(updatedRegistration);
        assertEquals(course.getNumCourse(), updatedRegistration.getCourse().getNumCourse());
    }

    @Test
    @Order(3)
    public void testFindById_Success() {
        registration.setSkier(skier);
        registration.setCourse(course);
        Registration savedRegistration = registrationRepository.save(registration);

        Optional<Registration> foundRegistration = registrationRepository.findById(savedRegistration.getNumRegistration());

        assertTrue(foundRegistration.isPresent());
        assertEquals(savedRegistration.getNumRegistration(), foundRegistration.get().getNumRegistration());
    }

    @Test
    @Order(4)
    public void testFindAllRegistrations() {
        registration.setSkier(skier);
        registration.setCourse(course);
        registrationRepository.save(registration);

        //List<Registration> registrations = registrationRepository.findAll();
        List<Registration> registrations = StreamSupport
                .stream(registrationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        assertFalse(registrations.isEmpty());
        assertTrue(registrations.contains(registration));
    }

    @Test
    @Order(5)
    public void testUpdateRegistration_Success() {
        // Enregistrer l'enregistrement initial
        registration.setSkier(skier);
        registration.setCourse(course);
        Registration savedRegistration = registrationRepository.save(registration);

        // Modifier des valeurs
        savedRegistration.setNumWeek(10); // Modifier le numéro de la semaine
        savedRegistration = registrationRepository.save(savedRegistration); // Sauvegarder les modifications

        // Vérifier les modifications
        assertEquals(10, savedRegistration.getNumWeek());
        assertEquals(skier.getNumSkier(), savedRegistration.getSkier().getNumSkier());
    }

    @Test
    @Order(6)
    public void testDeleteRegistration_Success() {
        registration.setSkier(skier);
        registration.setCourse(course);
        Registration savedRegistration = registrationRepository.save(registration);

        registrationRepository.deleteById(savedRegistration.getNumRegistration());

        Optional<Registration> deletedRegistration = registrationRepository.findById(savedRegistration.getNumRegistration());

        assertFalse(deletedRegistration.isPresent());
    }
}