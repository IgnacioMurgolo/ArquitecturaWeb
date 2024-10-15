package org.tudai.entregable3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tudai.entregable3.dto.EstudianteDTO;
import org.tudai.entregable3.model.Estudiante;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT new org.tudai.entregable3.dto.EstudianteDTO (e.nombres,e.apellido,e.anioNacimiento,e.genero,e.dni,e.ciudadResidencia,e.libretaUniv) FROM Estudiante e WHERE e.nombres=:nombre")
    List<EstudianteDTO> findByNombre(String nombre);

    @Query("SELECT new org.tudai.entregable3.dto.EstudianteDTO (e.nombres,e.apellido,e.anioNacimiento,e.genero,e.dni,e.ciudadResidencia,e.libretaUniv) FROM Estudiante e WHERE e.apellido=:apellido")
    List<EstudianteDTO> findByApellido(String apellido);

    //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    @Query("SELECT new org.tudai.entregable3.dto.EstudianteDTO (e.nombres,e.apellido,e.anioNacimiento,e.genero,e.dni,e.ciudadResidencia,e.libretaUniv) FROM Estudiante e ORDER BY e.nombres")
    List<EstudianteDTO> getEstudiantesOrderByNombre();

    //d) recuperar un estudiante, en base a su número de libreta universitaria.
    @Query("SELECT new org.tudai.entregable3.dto.EstudianteDTO (e.nombres,e.apellido,e.anioNacimiento,e.genero,e.dni,e.ciudadResidencia,e.libretaUniv) FROM Estudiante e WHERE e.libretaUniv =:libretaU")
    EstudianteDTO getEstudianteByNumeroLibreta(@Param("libretaU") long libretaU  );

    //e) recuperar todos los estudiantes, en base a su género.
    @Query("SELECT new org.tudai.entregable3.dto.EstudianteDTO(e.nombres, e.apellido, e.anioNacimiento, e.genero, e.dni, e.ciudadResidencia, e.libretaUniv) FROM Estudiante e WHERE e.genero =:genero")
    List<EstudianteDTO> getEstudiantesByGenero(@Param("genero")String genero);

    //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @Query("SELECT new org.tudai.entregable3.dto.EstudianteDTO(e.nombres, e.apellido, e.anioNacimiento, e.genero, e.dni, e.ciudadResidencia, e.libretaUniv) FROM Inscripcion i JOIN i.estudiante e JOIN i.carrera c WHERE e.ciudadResidencia = :ciudad AND c.nombre = :nombreCarrera")
    List<EstudianteDTO> getEstudiantesByCiudadAndCarrera(@Param("ciudad") String ciudad, @Param("nombreCarrera") String nombreCarrera);













}
