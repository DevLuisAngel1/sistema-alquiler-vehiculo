package com.utp.proyecto.controller;

import com.utp.proyecto.dto.VehiculoRequest;
import com.utp.proyecto.dto.VehiculoResponse;
import com.utp.proyecto.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // =========================
    // GET /api/vehiculos
    // =========================

    @GetMapping
    public ResponseEntity<List<VehiculoResponse>> listarTodos() {

        return ResponseEntity.ok(
                vehiculoService.listarTodos()
        );
    }

    // =========================
    // GET /api/vehiculos/{id}
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vehiculoService.buscarPorId(id)
        );
    }

    // =========================
    // POST /api/vehiculos
    // =========================

    @PostMapping
    public ResponseEntity<VehiculoResponse> crear(
            @Valid @RequestBody VehiculoRequest request) {

        VehiculoResponse response =
                vehiculoService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // PUT /api/vehiculos/{id}
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoRequest request) {

        VehiculoResponse response =
                vehiculoService.actualizar(id, request);

        return ResponseEntity.ok(response);
    }

    // =========================
    // DELETE /api/vehiculos/{id}
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        vehiculoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
