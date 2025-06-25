package pe.edu.vallegrande.demo3.deacfijos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo3.deacfijos.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDni(String dni);
    boolean existsByCorreo(String correo);
    boolean existsByDni(String dni);
    List<Usuario> findByEstado(Usuario.Estado estado);
    List<Usuario> findBySedeId(String sedeId);
} 