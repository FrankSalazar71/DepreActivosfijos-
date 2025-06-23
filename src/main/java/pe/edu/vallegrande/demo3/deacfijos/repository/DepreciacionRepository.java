package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.vallegrande.demo3.deacfijos.model.Depreciacion;

import java.time.LocalDate;
import java.util.List;

public interface DepreciacionRepository extends MongoRepository<Depreciacion, String> {

    // Método para encontrar depreciaciones por ID de activo
    List<Depreciacion> findByActivoId(String activoId);

    // Método para encontrar depreciaciones por año
    List<Depreciacion> findByAnioDepreciacion(int anio);

    // Método para encontrar depreciaciones por año y mes
    List<Depreciacion> findByAnioDepreciacionAndMesDepreciacion(int anio, int mes);

    // Método para encontrar depreciaciones dentro de un rango de fechas
    List<Depreciacion> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Método para encontrar depreciaciones de un activo antes de una fecha específica
    List<Depreciacion> findByActivoIdAndFechaBefore(String activoId, LocalDate fecha);
}