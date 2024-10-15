package org.tudai.entregable3.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.entregable3.dto.EstudianteDTO;
import org.tudai.entregable3.dto.InscripcionDTO;
import org.tudai.entregable3.model.Estudiante;
import org.tudai.entregable3.repository.EstudianteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) {
        List<InscripcionDTO> inscripcionesDTO = estudiante.getInscripciones().stream()
                .map(inscripcion -> new InscripcionDTO(
                        inscripcion.getAnioInscripcion(),
                        inscripcion.getAnioEgreso(),
                        inscripcion.isGraduado(),
                        inscripcion.getCarrera().getNombre(),
                        estudiante.getNombres() + " " + estudiante.getApellido() // Nombre completo del estudiante
                ))
                .collect(Collectors.toList());

        // Convertir Estudiante a EstudianteDTO
        return new EstudianteDTO(
                estudiante.getNombres() != null ? estudiante.getNombres() : "Nombre Desconocido",
                estudiante.getApellido() != null ? estudiante.getApellido() : "Apellido Desconocido",
                estudiante.getAnioNacimiento() != null ? estudiante.getAnioNacimiento() : 0,
                estudiante.getGenero() != null ? estudiante.getGenero() : "Género Desconocido",
                estudiante.getDni() != null ? estudiante.getDni() : 0L,
                estudiante.getCiudadResidencia() != null ? estudiante.getCiudadResidencia() : "Ciudad Desconocida",
                estudiante.getLibretaUniv() != null ? estudiante.getLibretaUniv() : 0L,
                inscripcionesDTO
        );
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findById(long id) {
        return estudianteRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id " + id));
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findAll() {
        List<EstudianteDTO> resultado = new ArrayList<>();
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        for (Estudiante estudiante : estudiantes) {
            EstudianteDTO dto = convertToDTO(estudiante);
            resultado.add(dto);
        }
        return resultado;
    }

    // a) dar de alta un estudiante
    @Transactional
    public void save(Estudiante estudiante) {
        estudianteRepository.save(estudiante);
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findByNombre(String nombre) {
        return estudianteRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findByApellido(String apellido) {
        return estudianteRepository.findByApellido(apellido);
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findByGenero(String genero) {
        return estudianteRepository.getEstudiantesByGenero(genero);
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findByNumeroLibreta(long lu) {
        return estudianteRepository.getEstudianteByNumeroLibreta(lu);
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findOrderByNombre() {
        return estudianteRepository.getEstudiantesOrderByNombre();
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findByCiudadAndCarrera(String ciudad, String carrera) {
        return estudianteRepository.getEstudiantesByCiudadAndCarrera(ciudad, carrera);
    }

    @Transactional
    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }
}
