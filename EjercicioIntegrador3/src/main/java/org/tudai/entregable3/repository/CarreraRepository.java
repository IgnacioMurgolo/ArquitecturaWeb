package org.tudai.entregable3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tudai.entregable3.dto.CarreraConCantidadInscriptosDTO;
import org.tudai.entregable3.model.Carrera;

import java.util.List;
@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    @Query("SELECT new org.tudai.entregable3.dto.CarreraConCantidadInscriptosDTO(c.nombre, COUNT(*)) FROM Inscripcion i JOIN i.carrera c GROUP BY c.nombre")
    List<CarreraConCantidadInscriptosDTO> carrerasConInscriptos();

}
