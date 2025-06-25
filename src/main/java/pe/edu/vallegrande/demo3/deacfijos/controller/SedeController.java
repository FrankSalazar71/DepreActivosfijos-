package pe.edu.vallegrande.demo3.deacfijos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.deacfijos.model.Sede;
import pe.edu.vallegrande.demo3.deacfijos.service.SedeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (ajustar según necesidad)
public class SedeController {

    private final SedeService service;

    @Autowired
    public SedeController(SedeService service) {
        this.service = service;
    }

    /**
     * Crea una nueva sede.
     * @param sede La sede a crear.
     * @return La sede creada con estado 201 Created.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sede crear(@RequestBody Sede sede) {
        return service.crearSede(sede);
    }

    /**
     * Lista todas las sedes o filtra por estado si se proporciona el parámetro 'estado'.
     * @param estado Opcional. El estado por el que filtrar (ACTIVA, INACTIVA).
     * @return Lista de sedes.
     */
    @GetMapping
    public List<Sede> listarTodas(@RequestParam(required = false) Sede.Estado estado) {
         if (estado != null) {
            return service.listarSedesPorEstado(estado);
        }
        return service.listarTodas();
    }

    /**
     * Obtiene una sede por su ID.
     * @param id ID de la sede.
     * @return ResponseEntity con la sede si se encuentra (200 OK) o 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza una sede existente.
     * @param id ID de la sede a actualizar.
     * @param sedeActualizada Datos actualizados de la sede.
     * @return ResponseEntity con la sede actualizada si se encuentra (200 OK) o 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sede> actualizar(@PathVariable String id, @RequestBody Sede sedeActualizada) {
        Sede resultado = service.actualizarSede(id, sedeActualizada);
        return resultado != null ?
                ResponseEntity.ok(resultado) :
                ResponseEntity.notFound().build();
    }

    /**
     * Elimina una sede por su ID.
     * @param id ID de la sede a eliminar.
     * @return ResponseEntity 200 OK si se eliminó, 404 Not Found si no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminarSede(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    /**
     * Desactiva una sede cambiando su estado a INACTIVA.
     * Utiliza PUT para esta acción específica.
     * @param id ID de la sede a desactivar.
     * @return ResponseEntity con la sede actualizada o 404 si no se encuentra.
     */
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Sede> desactivarSede(@PathVariable String id) {
        return service.desactivarSede(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Activa una sede cambiando su estado a ACTIVA.
     * Utiliza PUT para esta acción específica.
     * @param id ID de la sede a activar.
     * @return ResponseEntity con la sede actualizada o 404 si no se encuentra.
     */
    @PutMapping("/{id}/activar")
    public ResponseEntity<Sede> activarSede(@PathVariable String id) {
        return service.activarSede(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}