package org.tudai.entregable3.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.entregable3.dto.InscripcionDTO;
import org.tudai.entregable3.dto.ReporteCarreraDTO;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.model.Estudiante;
import org.tudai.entregable3.model.Inscripcion;
import org.tudai.entregable3.repository.EstudianteRepository;
import org.tudai.entregable3.repository.InscripcionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    @Autowired
    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @Transactional(readOnly = true)
    public List<ReporteCarreraDTO> getReporteCarreras() {
        try {
            List<ReporteCarreraDTO> inscriptos = inscripcionRepository.getInscriptosPorAnio();
            List<ReporteCarreraDTO> egresados = inscripcionRepository.getEgresadosPorAnio();

            // Combinar listas de inscriptos y egresados si es necesario.
            Map<String, Map<Integer, ReporteCarreraDTO>> reporteMap = new HashMap<>();

            // Procesar inscriptos
            for (ReporteCarreraDTO inscripto : inscriptos) {
                reporteMap
                        .computeIfAbsent(inscripto.getNombreCarrera(), k -> new HashMap<>())
                        .put(inscripto.getAnio(), inscripto);
            }

            // Procesar egresados
            for (ReporteCarreraDTO egresado : egresados) {
                reporteMap
                        .computeIfAbsent(egresado.getNombreCarrera(), k -> new HashMap<>())
                        .computeIfAbsent(egresado.getAnio(), k -> new ReporteCarreraDTO(egresado.getNombreCarrera(), egresado.getAnio(), 0, 0))
                        .setCantEgresados(egresado.getCantEgresados());
            }

            return reporteMap.values().stream()
                    .flatMap(map -> map.values().stream())
                    .sorted(Comparator.comparing(ReporteCarreraDTO::getNombreCarrera)
                            .thenComparing(ReporteCarreraDTO::getAnio))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al generar reporte de carreras", e);
        }
    }


    @Transactional
    public void actualizarInscripcion(Integer anio, boolean esGraduado, Long estudianteId, Long carreraId) {
        inscripcionRepository.actualizarInscripcion(anio, esGraduado, estudianteId, carreraId);
    }

    @Transactional
    public void save(Inscripcion inscripcion) {
        try {
            Estudiante estudiante = inscripcion.getEstudiante();
            estudiante.addInscripcion(inscripcion);
            Carrera carrera = inscripcion.getCarrera();
            carrera.addInscripcion(inscripcion);

            inscripcionRepository.save(inscripcion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Inscripcion getInscripcionById(Long id) {
        return inscripcionRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(Long id) {
        inscripcionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<InscripcionDTO> findAll() {
        List<InscripcionDTO> resultado = new ArrayList<>();
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();

        for (Inscripcion inscripcion : inscripciones) {
            Carrera carrera = inscripcion.getCarrera();
            Estudiante estudiante = inscripcion.getEstudiante();

            InscripcionDTO dto = new InscripcionDTO(
                    inscripcion.getAnioInscripcion(),
                    inscripcion.getAnioEgreso(),
                    inscripcion.isGraduado(),
                    carrera != null ? carrera.getNombre() : null, // Nombre de la carrera
                    estudiante != null ? estudiante.getNombres() + " " + estudiante.getApellido() : null // Nombre del estudiante
            );
            resultado.add(dto);
        }
        return resultado;
    }

    public InscripcionDTO convertToDTO(Inscripcion inscripcion) {
        Carrera carrera = inscripcion.getCarrera();
        Estudiante estudiante = inscripcion.getEstudiante();

        return new InscripcionDTO(
                inscripcion.getAnioInscripcion(),
                inscripcion.getAnioEgreso(),
                inscripcion.isGraduado(),
                carrera != null ? carrera.getNombre() : null, // Nombre de la carrera
                estudiante != null ? estudiante.getNombres() + " " + estudiante.getApellido() : null // Nombre del estudiante
        );
    }

}
