package pe.edu.vallegrande.demo3.deacfijos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sedes")
public class Sede {

    @Id
    private String sedeId; // Cambiado de 'id' a 'sedeId'

    @Field("nombre_sede") // Mapea el nombre del campo en la base de datos
    private String nombreSede; // Cambiado de 'nombre' a 'nombreSede' para seguir convenciones Java

    private String departamento;
    private String direccion;

    private Estado estado; // Añadido campo estado

    // Enum para el estado de la sede
    public enum Estado {
        ACTIVO,
        INACTIVO
    }

    // Constructor vacío
    public Sede() {
    }

    // Constructor con campos (sin sedeId y estado inicial)
    public Sede(String nombreSede, String departamento, String direccion) {
        this.nombreSede = nombreSede;
        this.departamento = departamento;
        this.direccion = direccion;
        this.estado = Estado.ACTIVO; // Estado por defecto al crear
    }

    // Getters y Setters
    public String getSedeId() {
        return sedeId;
    }

    public void setSedeId(String sedeId) {
        this.sedeId = sedeId;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Sede{" +
               "sedeId='" + sedeId + '\'' +
               ", nombreSede='" + nombreSede + '\'' +
               ", departamento='" + departamento + '\'' +
               ", direccion='" + direccion + '\'' +
               ", estado=" + estado +
               '}';
    }
}