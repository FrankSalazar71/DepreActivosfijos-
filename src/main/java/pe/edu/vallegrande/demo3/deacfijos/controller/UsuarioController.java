package pe.edu.vallegrande.demo3.deacfijos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.deacfijos.model.Usuario;
import pe.edu.vallegrande.demo3.deacfijos.service.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        return usuarioService.findByCorreo(loginRequest.getCorreo())
                .filter(usuario -> usuario.getContrasena().equals(loginRequest.getContrasena()))
                .map(usuario -> {
                    // Actualizar último acceso
                    usuario.setUltimoAcceso(LocalDateTime.now());
                    usuarioService.save(usuario);

                    // Crear respuesta
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", "JWT_TOKEN_" + usuario.getId()); // Aquí deberías generar un JWT real
                    response.put("usuario", usuario);
                    
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping
    public List<Usuario> getAllUsuarios(
            @RequestParam(required = false) Usuario.Estado estado,
            @RequestParam(required = false) String sedeId) {

        if (estado != null) {
            return usuarioService.listarUsuariosPorEstado(estado);
        } else if (sedeId != null && !sedeId.isEmpty()) {
            return usuarioService.listarUsuariosPorSede(sedeId);
        }
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable String id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.update(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable String id) {
        usuarioService.delete(id);
    }*/

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> deactivateUsuario(@PathVariable String id) {
        return usuarioService.desactivarUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Usuario> activateUsuario(@PathVariable String id) {
        return usuarioService.activarUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
        return usuarioService.findByCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Usuario> getUsuarioByDni(@PathVariable String dni) {
        return usuarioService.findByDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

class LoginRequest {
    private String correo;
    private String contrasena;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
} 