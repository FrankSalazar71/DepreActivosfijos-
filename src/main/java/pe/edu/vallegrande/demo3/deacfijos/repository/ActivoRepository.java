package pe.edu.vallegrande.demo3.deacfijos.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;

public interface ActivoRepository extends MongoRepository<Activo, String> {
}
