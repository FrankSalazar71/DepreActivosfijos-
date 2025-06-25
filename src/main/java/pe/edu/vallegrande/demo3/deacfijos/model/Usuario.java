package pe.edu.vallegrande.demo3.deacfijos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    
    public enum Estado {
        ACTIVO, INACTIVO
    }

    @Id
    private String id;

    private String nombre;
    
    private String apellidos;

    @Indexed(unique = true)
    private String correo;

    private String contrasena;

    private String rol = "EMPLEADO";  // ADMIN, SUPERVISOR, EMPLEADO

    @Indexed(unique = true)
    private String dni;

    private String telefono;

    private Estado estado = Estado.ACTIVO;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private LocalDateTime ultimoAcceso;

    private LocalDateTime fechaModificacion;

    private String sedeId;
} 