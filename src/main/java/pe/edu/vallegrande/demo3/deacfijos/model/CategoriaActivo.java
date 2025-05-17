package pe.edu.vallegrande.demo3.deacfijos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categorias_activos")
public class CategoriaActivo {
    
    public enum Estado {
        ACTIVO, INACTIVO
    }

    @Id
    private String id;
    
    private String nombreCategoria;
    
    private String descripcion;
    
    private Estado estado = Estado.ACTIVO;
    
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    private LocalDateTime fechaModificacion;
    
    private int vidaUtilPredeterminada; // Vida útil en años para esta categoría
    
    private double tasaDepreciacionAnual; // Tasa de depreciación anual predeterminada
    
    private String codigoContable; // Código contable para esta categoría de activos
} 