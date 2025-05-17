package pe.edu.vallegrande.demo3.deacfijos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "activos")
public class Activo {
    @Id
    private String id;

    private String descripcion;
    private String tipo;
    private String marca;
    private String modelo;
    private String zona;
    private double costoAdquisicion;
    private Date fechaCompra;

    //id categoria
    //garegar vida util
    //estado
    //valor residual
    //metodo de depresiacion "devaluacion anual"

    // Constructor vacío
    public Activo() {
    }

    // Constructor completo
    public Activo(String id, String descripcion, String tipo, String marca, String modelo,
                  String zona, double costoAdquisicion, Date fechaCompra) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.zona = zona;
        this.costoAdquisicion = costoAdquisicion;
        this.fechaCompra = fechaCompra;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getZona() {
        return zona;
    }

    public double getCostoAdquisicion() {
        return costoAdquisicion;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public void setCostoAdquisicion(double costoAdquisicion) {
        this.costoAdquisicion = costoAdquisicion;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
