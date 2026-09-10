package com.utp.proyecto.repository;


import com.utp.proyecto.model.Vehiculo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VehiculoRepository {

    private final List<Vehiculo> vehiculos = new ArrayList<>();

    private Long siguienteId = 1L;

    public List<Vehiculo> findAll() {
        return vehiculos;
    }

    public Optional<Vehiculo> findById(Long id) {
        return vehiculos.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
    }

    public Optional<Vehiculo> findByPlaca(String placa) {
        return vehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    public Vehiculo save(Vehiculo vehiculo) {

        vehiculo.setId(siguienteId++);

        vehiculos.add(vehiculo);

        return vehiculo;
    }

    public void delete(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
    }
}
