package pe.edu.vallegrande.demo3.deacfijos.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;
import pe.edu.vallegrande.demo3.deacfijos.service.ActivoService;

import java.util.List;

@RestController
@RequestMapping("/api/activos")
@CrossOrigin(origins = "*")
public class ActivoController {

    @Autowired
    private ActivoService service;

    @PostMapping
    public ResponseEntity<Activo> crear(@RequestBody Activo activo) {
        return ResponseEntity.ok(service.crearActivo(activo));
    }

    @GetMapping
    public ResponseEntity<List<Activo>> listar() {
        return ResponseEntity.ok(service.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activo> obtener(@PathVariable String id) {
        return service.obtenerActivoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activo> actualizar(@PathVariable String id, @RequestBody Activo activo) {
        Activo actualizado = service.actualizarActivo(id, activo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminarActivo(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }
}
