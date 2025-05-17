package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo3.deacfijos.model.Depreciacion;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepreciacionRepository extends MongoRepository<Depreciacion, String> {
    
    // Buscar depreciaciones por ID del activo
    List<Depreciacion> findByActivoId(String activoId);
    
    // Buscar depreciaciones por año
    List<Depreciacion> findByAnioDepreciacion(int anio);
    
    // Buscar depreciaciones por año y mes
    List<Depreciacion> findByAnioDepreciacionAndMesDepreciacion(int anio, int mes);
    
    // Buscar depreciaciones por rango de fechas
    List<Depreciacion> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar depreciaciones por activo y año
    List<Depreciacion> findByActivoIdAndAnioDepreciacion(String activoId, int anio);
} 