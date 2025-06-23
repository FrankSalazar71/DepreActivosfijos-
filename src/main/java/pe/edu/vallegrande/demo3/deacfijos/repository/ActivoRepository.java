package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;

import java.util.List;

@Repository
public interface ActivoRepository extends MongoRepository<Activo, String> {

    List<Activo> findByEstado(Activo.Estado estado);

}
