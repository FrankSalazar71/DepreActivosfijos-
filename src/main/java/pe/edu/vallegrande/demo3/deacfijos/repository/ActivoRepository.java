package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;

@Repository
public interface ActivoRepository extends MongoRepository<Activo, String> {
}
