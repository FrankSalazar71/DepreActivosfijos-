package pe.edu.vallegrande.demo3.deacfijos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.deacfijos.model.CategoriaActivo;
import pe.edu.vallegrande.demo3.deacfijos.service.CategoriaActivoService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaActivoController {

    @Autowired
    private CategoriaActivoService service;

    @PostMapping
    public ResponseEntity<CategoriaActivo> crear(@RequestBody CategoriaActivo categoria) {
        return ResponseEntity.ok(service.crear(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaActivo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaActivo> obtener(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaActivo> actualizar(@PathVariable String id, @RequestBody CategoriaActivo categoria) {
        CategoriaActivo actualizado = service.actualizar(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminar(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }
} 