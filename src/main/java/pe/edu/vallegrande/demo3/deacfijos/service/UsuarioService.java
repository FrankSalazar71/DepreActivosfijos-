package pe.edu.vallegrande.demo3.deacfijos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.deacfijos.model.Usuario;
import pe.edu.vallegrande.demo3.deacfijos.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setFechaCreacion(LocalDateTime.now());
        }
        usuario.setFechaModificacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> update(String id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(existingUsuario -> {
                    usuario.setId(id);
                    usuario.setFechaCreacion(existingUsuario.getFechaCreacion());
                    usuario.setFechaModificacion(LocalDateTime.now());
                    if (usuario.getSedeId() == null) {
                         usuario.setSedeId(existingUsuario.getSedeId());
                    }
                    return usuarioRepository.save(usuario);
                });
    }

    /*public void delete(String id) {
        usuarioRepository.deleteById(id);
    }*/

    public Optional<Usuario> desactivarUsuario(String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setEstado(Usuario.Estado.INACTIVO);
                    usuario.setFechaModificacion(LocalDateTime.now()); // Opcional: actualizar fecha de modificación
                    return usuarioRepository.save(usuario);
                });
    }

    public Optional<Usuario> activarUsuario(String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setEstado(Usuario.Estado.ACTIVO);
                    usuario.setFechaModificacion(LocalDateTime.now()); // Opcional: actualizar fecha de modificación
                    return usuarioRepository.save(usuario);
                });
    }

    public List<Usuario> listarUsuariosPorEstado(Usuario.Estado estado) {
        return usuarioRepository.findByEstado(estado);
    }

    public List<Usuario> listarUsuariosPorSede(String sedeId) {
        return usuarioRepository.findBySedeId(sedeId);
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> findByDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existsByDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }
}