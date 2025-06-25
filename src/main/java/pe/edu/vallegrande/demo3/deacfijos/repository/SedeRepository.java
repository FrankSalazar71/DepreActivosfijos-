package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo3.deacfijos.model.Sede;

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeRepository extends MongoRepository<Sede, String> {
    // Spring Data MongoDB proporcionará automáticamente los métodos CRUD básicos
    // Puedes añadir métodos de consulta personalizados aquí si los necesitas,
    // por ejemplo: Optional<Sede> findByNombreSede(String nombreSede);
     List<Sede> findByEstado(Sede.Estado estado);
}