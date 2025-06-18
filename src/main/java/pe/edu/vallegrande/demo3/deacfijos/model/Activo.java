package pe.edu.vallegrande.demo3.deacfijos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Date;

@Data
@Document(collection = "activos")
public class Activo {
    public enum Estado {
        ACTIVO, INACTIVO, EN_MANTENIMIENTO, DADO_DE_BAJA
    }

    public enum MetodoDepreciacion {
        LINEA_RECTA,
        SUMA_DIGITOS,
        REDUCCION_SALDOS,
        UNIDADES_PRODUCIDAS
    }

    @Id
    private String id;

    private String descripcion;
    private String tipo;
    private String marca;
    private String modelo;
    private String zona;
    private double costoAdquisicion;
    private Date fechaCompra;
    
    // Nuevos campos
    private String categoriaId;
    private int vidaUtilAnios;
    private Estado estado = Estado.ACTIVO;
    private double valorResidual;
    private MetodoDepreciacion metodoDepreciacion = MetodoDepreciacion.LINEA_RECTA;
    private double depreciacionAnual;
    
    // Constructor vacío
    public Activo() {
    }

    // Constructor completo
    public Activo(String id, String descripcion, String tipo, String marca, String modelo,
                  String zona, double costoAdquisicion, Date fechaCompra, String categoriaId,
                  int vidaUtilAnios, Estado estado, double valorResidual, 
                  MetodoDepreciacion metodoDepreciacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.zona = zona;
        this.costoAdquisicion = costoAdquisicion;
        this.fechaCompra = fechaCompra;
        this.categoriaId = categoriaId;
        this.vidaUtilAnios = vidaUtilAnios;
        this.estado = estado;
        this.valorResidual = valorResidual;
        this.metodoDepreciacion = metodoDepreciacion;
        this.calcularDepreciacionAnual();
    }

    // Método para calcular la depreciación anual
    public void calcularDepreciacionAnual() {
        switch (metodoDepreciacion) {
            case LINEA_RECTA:
                // (Costo - Valor Residual) / Vida Útil
                this.depreciacionAnual = (costoAdquisicion - valorResidual) / vidaUtilAnios;
                break;
                
            case SUMA_DIGITOS:
                // Factor = (Años restantes) / (Suma de dígitos de años)
                int sumaDigitos = (vidaUtilAnios * (vidaUtilAnios + 1)) / 2;
                this.depreciacionAnual = ((costoAdquisicion - valorResidual) * vidaUtilAnios) / sumaDigitos;
                break;
                
            case REDUCCION_SALDOS:
                // Tasa = 1 - (Valor Residual/Costo)^(1/n)
                double tasa = 1 - Math.pow(valorResidual / costoAdquisicion, 1.0 / vidaUtilAnios);
                this.depreciacionAnual = costoAdquisicion * tasa;
                break;
                
            case UNIDADES_PRODUCIDAS:
                // En este caso, necesitaríamos información adicional sobre las unidades producidas
                // Por ahora, usaremos un cálculo básico similar al de línea recta
                this.depreciacionAnual = (costoAdquisicion - valorResidual) / vidaUtilAnios;
                break;
        }
    }
    
    // Método para obtener el valor en libros actual
    public double calcularValorLibros(int anioActual) {
        if (anioActual <= 0 || anioActual > vidaUtilAnios) {
            return valorResidual;
        }
        
        double valorLibros = costoAdquisicion;
        
        switch (metodoDepreciacion) {
            case LINEA_RECTA:
                valorLibros = costoAdquisicion - (depreciacionAnual * anioActual);
                break;
                
            case SUMA_DIGITOS:
                int sumaDigitos = (vidaUtilAnios * (vidaUtilAnios + 1)) / 2;
                double depreciacionAcumulada = 0;
                for (int i = 1; i <= anioActual; i++) {
                    int factorAnio = vidaUtilAnios - i + 1;
                    depreciacionAcumulada += ((costoAdquisicion - valorResidual) * factorAnio) / sumaDigitos;
                }
                valorLibros = costoAdquisicion - depreciacionAcumulada;
                break;
                
            case REDUCCION_SALDOS:
                double tasa = 1 - Math.pow(valorResidual / costoAdquisicion, 1.0 / vidaUtilAnios);
                valorLibros = costoAdquisicion * Math.pow(1 - tasa, anioActual);
                break;
                
            case UNIDADES_PRODUCIDAS:
                valorLibros = costoAdquisicion - (depreciacionAnual * anioActual);
                break;
        }
        
        return Math.max(valorLibros, valorResidual);
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
