package tn.esprit.spring;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)  // Activer Mockito pour ce test
public class PisteServicesImpltestMock {
    @Mock
    private IPisteRepository pisteRepository;  // Simuler le repository

    @InjectMocks
    private PisteServicesImpl pisteServices;  // Injecter le mock dans le service

    private Piste piste;

    @BeforeEach
    public void setup() {
        // Créer une piste de test
        piste = new Piste(1L, "Piste Bleue", null, 3000, 20, null);
    }

    // Test : Ajouter une piste
    @Test
    public void testAddPiste() {
        when(pisteRepository.save(piste)).thenReturn(piste);

        Piste savedPiste = pisteServices.addPiste(piste);

        assertNotNull(savedPiste);
        assertEquals("Piste Bleue", savedPiste.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);  // Vérifier que save a été appelé
    }

    // Test : Récupérer toutes les pistes
    @Test
    public void testRetrieveAllPistes() {
        List<Piste> pistes = new ArrayList<>();
        pistes.add(piste);
        when(pisteRepository.findAll()).thenReturn(pistes);

        List<Piste> result = pisteServices.retrieveAllPistes();

        assertEquals(1, result.size());
        verify(pisteRepository, times(1)).findAll();  // Vérifier que findAll a été appelé
    }

    // Test : Récupérer une piste par ID
    @Test
    public void testRetrievePiste() {
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        Piste foundPiste = pisteServices.retrievePiste(1L);

        assertNotNull(foundPiste);
        assertEquals("Piste Bleue", foundPiste.getNamePiste());
        verify(pisteRepository, times(1)).findById(1L);  // Vérifier que findById a été appelé
    }

    // Test : Mettre à jour une piste
    @Test
    public void testUpdatePiste() {
        piste.setNamePiste("Piste Bleue Modifiée");
        when(pisteRepository.save(piste)).thenReturn(piste);

        Piste updatedPiste = pisteServices.addPiste(piste);  // Utilisation de save pour update

        assertEquals("Piste Bleue Modifiée", updatedPiste.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);  // Vérifier que save a été appelé
    }

    // Test : Supprimer une piste
    @Test
    public void testRemovePiste() {
        doNothing().when(pisteRepository).deleteById(1L);

        pisteServices.removePiste(1L);

        verify(pisteRepository, times(1)).deleteById(1L);  // Vérifier que deleteById a été appelé
    }
}
