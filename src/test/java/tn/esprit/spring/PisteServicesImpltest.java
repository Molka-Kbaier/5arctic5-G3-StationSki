package tn.esprit.spring;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import javax.transaction.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Charge le contexte Spring avec MySQL
@TestMethodOrder(OrderAnnotation.class)  // Définir l'ordre des tests
@Transactional  // Chaque test est isolé dans une transaction
@Rollback(true)  // Les modifications sont annulées après chaque test

public class PisteServicesImpltest {
    @Autowired
    private PisteServicesImpl pisteServices;

    @Autowired
    private IPisteRepository pisteRepository;

    // Test : Ajouter une piste
    @Test
    @Order(1)  // Ce test sera exécuté en premier
    public void testAddPiste() {
        Piste piste = new Piste(null, "Piste Bleue", null, 3000, 20, null);  // Constructeur adapté
        Piste savedPiste = pisteServices.addPiste(piste);

        assertNotNull(savedPiste);
        assertNotNull(savedPiste.getNumPiste());  // Vérifie si l'ID est généré
        assertEquals("Piste Bleue", savedPiste.getNamePiste());  // Vérifie le nom
    }

    // Test : Récupérer toutes les pistes
    @Test
    @Order(2)  // Ce test sera exécuté en deuxième
    public void testRetrieveAllPistes() {
        Piste piste1 = new Piste(null, "Piste Rouge", null, 2500, 15, null);
        Piste piste2 = new Piste(null, "Piste Noire", null, 3000, 25, null);
        pisteServices.addPiste(piste1);
        pisteServices.addPiste(piste2);

        List<Piste> pistes = pisteServices.retrieveAllPistes();
        assertTrue(pistes.size() >= 2);
    }

    // Test : Récupérer une piste par ID
    @Test
    @Order(3)  // Ce test sera exécuté en troisième
    public void testRetrievePiste() {
        Piste piste = new Piste(null, "Piste Verte", null, 2000, 10, null);
        Piste savedPiste = pisteServices.addPiste(piste);

        Piste foundPiste = pisteServices.retrievePiste(savedPiste.getNumPiste());
        assertNotNull(foundPiste);
        assertEquals("Piste Verte", foundPiste.getNamePiste());
    }
    // Test : Mettre à jour une piste
    @Test
    @Order(4)  // Ce test sera exécuté en quatrième
    public void testUpdatePiste() {
        Piste piste = new Piste(null, "Piste Jaune", null, 1800, 15, null);
        Piste savedPiste = pisteServices.addPiste(piste);

        // Mettre à jour la piste
        savedPiste.setNamePiste("Piste Jaune Modifiée");
        savedPiste.setLength(2000);
        Piste updatedPiste = pisteServices.addPiste(savedPiste); // Utilisation de addPiste pour faire un update

        // Vérifier que la mise à jour a bien été faite
        Piste foundPiste = pisteServices.retrievePiste(updatedPiste.getNumPiste());
        assertNotNull(foundPiste);
        assertEquals("Piste Jaune Modifiée", foundPiste.getNamePiste());
        assertEquals(2000, foundPiste.getLength());
    }
    // Test : Supprimer une piste
    @Test
    @Order(5)  // Ce test sera exécuté en dernier
    public void testRemovePiste() {
        Piste piste = new Piste(null, "Piste à Supprimer", null, 1500, 5, null);
        Piste savedPiste = pisteServices.addPiste(piste);

        pisteServices.removePiste(savedPiste.getNumPiste());

        Piste deletedPiste = pisteServices.retrievePiste(savedPiste.getNumPiste());
        assertNull(deletedPiste);
    }
}
