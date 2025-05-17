package pe.edu.vallegrande.demo3.deacfijos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "depreciaciones")
public class Depreciacion {
    
    @Id
    private String id;
    
    private String activoId; // Referencia al ID del activo en MongoDB
    
    private LocalDate fecha;
    
    private double valorDepreciado;
    
    private double valorLibros;
    
    // Campos adicionales para mejor seguimiento
    private int anioDepreciacion;
    private int mesDepreciacion;
    private double tasaDepreciacionAplicada;
    private String observaciones;
    
    // Constructor personalizado para crear una depreciación
    public Depreciacion(String activoId, LocalDate fecha, double valorDepreciado, double valorLibros) {
        this.activoId = activoId;
        this.fecha = fecha;
        this.valorDepreciado = valorDepreciado;
        this.valorLibros = valorLibros;
        this.anioDepreciacion = fecha.getYear();
        this.mesDepreciacion = fecha.getMonthValue();
    }
} 