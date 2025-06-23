package pe.edu.vallegrande.demo3.deacfijos.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;
import pe.edu.vallegrande.demo3.deacfijos.model.CategoriaActivo;
import pe.edu.vallegrande.demo3.deacfijos.service.ActivoService;
import pe.edu.vallegrande.demo3.deacfijos.service.CategoriaActivoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activos")
@CrossOrigin(origins = "*")
public class ActivoController {

    @Autowired
    private ActivoService service;
    
    @Autowired
    private CategoriaActivoService categoriaService;

    @PostMapping
    public ResponseEntity<Activo> crear(@RequestBody Activo activo) {
        // Calcular la depreciación antes de guardar
        activo.calcularDepreciacionAnual();
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

    @GetMapping("/inactivos")
    public ResponseEntity<List<Activo>> listarActivosInactivos() {
        try {
            List<Activo> inactivos = service.listarActivosPorEstado(Activo.Estado.INACTIVO);
            return ResponseEntity.ok(inactivos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activo> actualizar(@PathVariable String id, @RequestBody Activo activoActualizado) {
        return service.obtenerActivoPorId(id)
                .map(activoExistente -> {
                    // Mantener el ID original
                    activoActualizado.setId(id);
                    
                    // Preservar valores existentes si los nuevos son null o 0
                    if (activoActualizado.getCategoriaId() == null) {
                        activoActualizado.setCategoriaId(activoExistente.getCategoriaId());
                    }
                    if (activoActualizado.getVidaUtilAnios() <= 0) {
                        activoActualizado.setVidaUtilAnios(activoExistente.getVidaUtilAnios());
                    }
                    if (activoActualizado.getCostoAdquisicion() <= 0) {
                        activoActualizado.setCostoAdquisicion(activoExistente.getCostoAdquisicion());
                    }
                    if (activoActualizado.getValorResidual() < 0) {
                        activoActualizado.setValorResidual(activoExistente.getValorResidual());
                    }
                    if (activoActualizado.getMetodoDepreciacion() == null) {
                        activoActualizado.setMetodoDepreciacion(activoExistente.getMetodoDepreciacion());
                    }
                    if (activoActualizado.getFechaCompra() == null) {
                        activoActualizado.setFechaCompra(activoExistente.getFechaCompra());
                    }
                    
                    // Calcular la depreciación antes de guardar
                    activoActualizado.calcularDepreciacionAnual();
                    
                    // Actualizar el activo usando el método correcto
                    Activo resultado = service.actualizarActivo(id, activoActualizado);
                    return ResponseEntity.ok(resultado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminarActivo(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }*/

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Activo> desactivarActivo(@PathVariable String id) {
        try {
            Optional<Activo> activoOptional = service.obtenerActivoPorId(id);

            if (activoOptional.isPresent()) {
                Activo activo = activoOptional.get();
                // Cambiar el estado a INACTIVO
                activo.setEstado(Activo.Estado.INACTIVO);
                // Guardar el activo actualizado
                Activo activoDesactivado = service.save(activo); // Usar el método save del servicio
                return ResponseEntity.ok(activoDesactivado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
        
    @PutMapping("/{id}/activar")
    public ResponseEntity<Activo> activarActivo(@PathVariable String id) {
        try {
            Optional<Activo> activoOptional = service.obtenerActivoPorId(id);

            if (activoOptional.isPresent()) {
                Activo activo = activoOptional.get();
                // Cambiar el estado a ACTIVO
                activo.setEstado(Activo.Estado.ACTIVO);
                // Guardar el activo actualizado
                Activo activoActivado = service.save(activo); // Usar el método save del servicio
                return ResponseEntity.ok(activoActivado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaActivo>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    // Agregar endpoint para calcular valor en libros
    @GetMapping("/{id}/valor-libros/{anio}")
    public ResponseEntity<Double> calcularValorLibros(@PathVariable String id, @PathVariable int anio) {
        return service.obtenerActivoPorId(id)
                .map(activo -> ResponseEntity.ok(activo.calcularValorLibros(anio)))
                .orElse(ResponseEntity.notFound().build());
    }
}
