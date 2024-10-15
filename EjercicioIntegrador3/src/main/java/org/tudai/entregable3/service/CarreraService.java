package org.tudai.entregable3.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.entregable3.dto.CarreraConCantidadInscriptosDTO;
import org.tudai.entregable3.dto.CarreraDTO;
import org.tudai.entregable3.model.Carrera;
import org.tudai.entregable3.repository.CarreraRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;

    @Autowired
    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Transactional(readOnly = true)
    public List<CarreraConCantidadInscriptosDTO> getCarrerasByCantidadInscriptos() {
        return carreraRepository.carrerasConInscriptos();
    }

    @Transactional(readOnly = true)
    public List<CarreraDTO> findAll() {
        List<CarreraDTO> resultado = new ArrayList<>();
        List<Carrera> carreras = carreraRepository.findAll();
        for (Carrera carrera : carreras) {
            CarreraDTO car = convertToDTO(carrera);
            resultado.add(car);
        }
        return resultado;
    }

    private CarreraDTO convertToDTO(Carrera carrera) {
        return new CarreraDTO(carrera.getNombre());
    }

    @Transactional
    public void save(Carrera carrera) {
        carreraRepository.save(carrera);
    }

    @Transactional
    public void deleteById(long id) {
        carreraRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CarreraDTO findById(Long id) {
        return carreraRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Carrera no encontrada con id " + id));
    }
}
