package org.tudai.entregable3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.entregable3.dto.InscripcionDTO;
import org.tudai.entregable3.dto.ReporteCarreraDTO;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.model.Estudiante;
import org.tudai.entregable3.model.Inscripcion;
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
            List<Object[]> inscriptos = inscripcionRepository.getInscriptosPorAnio();
            List<Object[]> egresados = inscripcionRepository.getEgresadosPorAnio();

            Map<String, Map<Integer, ReporteCarreraDTO>> reporteMap = new HashMap<>();

            // Procesar inscriptos
            for (Object[] inscripto : inscriptos) {
                String nombreCarrera = (String) inscripto[0];
                int anio = (Integer) inscripto[1];
                int cantInscriptos = (Integer) inscripto[2];

                reporteMap
                        .computeIfAbsent(nombreCarrera, k -> new HashMap<>())
                        .put(anio, new ReporteCarreraDTO(nombreCarrera, anio, cantInscriptos, 0));
            }

            // Procesar egresados
            for (Object[] egresado : egresados) {
                String nombreCarrera = (String) egresado[0];
                int anio = (Integer) egresado[1];
                int cantEgresados = (Integer) egresado[2];

                reporteMap
                        .computeIfAbsent(nombreCarrera, k -> new HashMap<>())
                        .computeIfAbsent(anio, k -> new ReporteCarreraDTO(nombreCarrera, anio, 0, 0))
                        .setCantEgresados(cantEgresados);
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
