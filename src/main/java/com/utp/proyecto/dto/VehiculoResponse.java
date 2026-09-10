package com.utp.proyecto.dto;

import com.utp.proyecto.model.EstadoVehiculo;
import com.utp.proyecto.model.TipoVehiculo;

import java.math.BigDecimal;

public class VehiculoResponse {

    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precioPorDia;
    private TipoVehiculo tipo;
    private EstadoVehiculo estado;

    public VehiculoResponse() {
    }

    public VehiculoResponse(Long id, String placa, String marca,
                            String modelo, Integer anio,
                            BigDecimal precioPorDia,
                            TipoVehiculo tipo,
                            EstadoVehiculo estado) {

        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioPorDia = precioPorDia;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public BigDecimal getPrecioPorDia() {
        return precioPorDia;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }
}

