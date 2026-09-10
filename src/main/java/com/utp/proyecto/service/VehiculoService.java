package com.utp.proyecto.service;

import com.utp.proyecto.dto.VehiculoRequest;
import com.utp.proyecto.dto.VehiculoResponse;
import com.utp.proyecto.exception.ResourceNotFoundException;
import com.utp.proyecto.model.EstadoVehiculo;
import com.utp.proyecto.model.Vehiculo;
import com.utp.proyecto.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    // =========================
    // LISTAR TODOS
    // =========================

    public List<VehiculoResponse> listarTodos() {

        return vehiculoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    public VehiculoResponse buscarPorId(Long id) {

        Vehiculo vehiculo = buscarVehiculo(id);

        return convertirAResponse(vehiculo);
    }

    // =========================
    // CREAR
    // =========================

    public VehiculoResponse crear(VehiculoRequest request) {

        // Verificar que no exista la placa
        vehiculoRepository.findByPlaca(request.getPlaca())
                .ifPresent(v -> {
                    throw new IllegalArgumentException(
                            "Ya existe un vehículo con la placa: "
                                    + request.getPlaca()
                    );
                });

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPlaca(request.getPlaca());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setPrecioPorDia(request.getPrecioPorDia());
        vehiculo.setTipo(request.getTipo());

        // Todo vehículo nuevo empieza disponible
        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);

        Vehiculo guardado = vehiculoRepository.save(vehiculo);

        return convertirAResponse(guardado);
    }

    // =========================
    // ACTUALIZAR
    // =========================

    public VehiculoResponse actualizar(
            Long id,
            VehiculoRequest request) {

        Vehiculo vehiculo = buscarVehiculo(id);

        // Verificar que la nueva placa no pertenezca
        // a otro vehículo
        vehiculoRepository.findByPlaca(request.getPlaca())
                .ifPresent(vehiculoEncontrado -> {

                    if (!vehiculoEncontrado.getId().equals(id)) {
                        throw new IllegalArgumentException(
                                "La placa "
                                        + request.getPlaca()
                                        + " ya pertenece a otro vehículo"
                        );
                    }
                });

        vehiculo.setPlaca(request.getPlaca());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setPrecioPorDia(request.getPrecioPorDia());
        vehiculo.setTipo(request.getTipo());

        return convertirAResponse(vehiculo);
    }

    // =========================
    // ELIMINAR
    // =========================

    public void eliminar(Long id) {

        Vehiculo vehiculo = buscarVehiculo(id);

        // No permitimos eliminar un vehículo
        // que esté alquilado o reservado
        if (vehiculo.getEstado() == EstadoVehiculo.ALQUILADO ||
                vehiculo.getEstado() == EstadoVehiculo.RESERVADO) {

            throw new IllegalStateException(
                    "No se puede eliminar un vehículo "
                            + "que está " + vehiculo.getEstado()
            );
        }

        vehiculoRepository.delete(vehiculo);
    }

    // =========================
    // MÉTODO AUXILIAR
    // =========================

    private Vehiculo buscarVehiculo(Long id) {

        return vehiculoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe el vehículo con ID: " + id
                        )
                );
    }

    // =========================
    // CONVERTIR A RESPONSE
    // =========================

    private VehiculoResponse convertirAResponse(
            Vehiculo vehiculo) {

        return new VehiculoResponse(
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio(),
                vehiculo.getPrecioPorDia(),
                vehiculo.getTipo(),
                vehiculo.getEstado()
        );
    }
}
