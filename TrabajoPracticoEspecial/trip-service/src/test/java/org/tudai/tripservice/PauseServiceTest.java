package org.tudai.tripservice;

import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.tudai.tripservice.dto.PauseDTO;
import org.tudai.tripservice.entitity.Pause;
import org.tudai.tripservice.repository.PauseRepository;
import org.tudai.tripservice.service.PauseService;
import org.tudai.tripservice.service.TripService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PauseServiceTest {

    @Mock
    private PauseRepository pauseRepository;

    @Mock
    private TripService tripService;

    @InjectMocks
    private PauseService pauseService;

    private PauseDTO pauseDTO;
    private Pause pause;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear objetos de prueba
        pauseDTO = new PauseDTO(new Date(), new Date(), true, "1");
        pause = new Pause(new Date(), new Date(), true, "1");
    }

    @Test
    void save_shouldSavePauseAndCallTripService() {
        // Definir el comportamiento del repositorio y el servicio de viajes
        when(pauseRepository.save(any(Pause.class))).thenReturn(pause);
        doNothing().when(tripService).setPauseIdToTrip(pause.getId(), pause.getTripId());

        // Llamar al método save
        pauseService.save(pauseDTO);

        // Verificar que el repositorio de pausas haya sido llamado con el objeto correcto
        verify(pauseRepository, times(1)).save(any(Pause.class));

        // Verificar que el servicio de viajes haya sido llamado para asociar el ID de la pausa al viaje
        verify(tripService, times(1)).setPauseIdToTrip(pause.getId(), pause.getTripId());
    }

    @Test
    void getAll_shouldReturnListOfPauseDTO() {
        // Crear una lista de pausas simuladas
        List<Pause> pauses = Arrays.asList(pause);
        when(pauseRepository.findAll()).thenReturn(pauses);

        // Llamar al método getAll
        List<PauseDTO> result = pauseService.getAll();

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(result);
        assertFalse(result.isEmpty());

        // Verificar que el DTO devuelto tiene la misma información que el objeto Pause original
        assertEquals(pause.getStartPause(), result.get(0).getStartPause());
        assertEquals(pause.getEndPause(), result.get(0).getEndPause());
        assertEquals(pause.getExceededTime(), result.get(0).getExceededTime());
        assertEquals(pause.getTripId(), result.get(0).getTripId());
    }

    @Test
    void deleteById_shouldDeletePause() {
        // Preparar el comportamiento del repositorio para eliminar la pausa
        doNothing().when(pauseRepository).deleteById(pause.getId());

        // Llamar al método deleteById
        pauseService.deleteById(pause.getId());

        // Verificar que el repositorio haya sido llamado para eliminar el objeto por su ID
        verify(pauseRepository, times(1)).deleteById(pause.getId());
    }

    @Test
    void updateById_shouldUpdatePause() {
        // Crear un nuevo DTO con datos actualizados
        PauseDTO updatedPauseDTO = new PauseDTO(new Date(), new Date(), false, "2");

        // Definir el comportamiento de búsqueda y actualización del repositorio
        when(pauseRepository.findById(pause.getId())).thenReturn(Optional.of(pause));

        // Llamar al método updateById
        pauseService.updateById(pause.getId(), updatedPauseDTO);

        // Verificar que el repositorio haya guardado el objeto actualizado
        verify(pauseRepository, times(1)).save(any(Pause.class));
    }

    @Test
    void updateById_shouldThrowExceptionWhenPauseNotFound() {
        // Simular que no se encuentra la pausa
        when(pauseRepository.findById(pause.getId())).thenReturn(Optional.empty());

        // Llamar al método updateById y verificar que se lance la excepción
        assertThrows(EntityNotFoundException.class, () -> {
            pauseService.updateById(pause.getId(), pauseDTO);
        });
    }
}

