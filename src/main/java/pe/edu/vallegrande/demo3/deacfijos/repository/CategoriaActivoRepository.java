package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo3.deacfijos.model.CategoriaActivo;

@Repository
public interface CategoriaActivoRepository extends MongoRepository<CategoriaActivo, String> {
    // Métodos personalizados pueden ser agregados aquí si se necesitan
} 