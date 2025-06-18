package pe.edu.vallegrande.demo3.deacfijos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.deacfijos.model.Depreciacion;
import pe.edu.vallegrande.demo3.deacfijos.service.DepreciacionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/depreciaciones")
@CrossOrigin(origins = "*")
public class DepreciacionController {

    @Autowired
    private DepreciacionService service;

    /**
     * Registra una depreciación manualmente
     * @param depreciacion Datos de la depreciación a registrar
     * @return La depreciación registrada
     */
    @PostMapping
    public ResponseEntity<Depreciacion> registrar(@RequestBody Depreciacion depreciacion) {
        try {
            if (depreciacion.getActivoId() == null || depreciacion.getFecha() == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(service.registrarDepreciacion(depreciacion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Calcula y registra la depreciación de un activo en una fecha específica
     * @param activoId ID del activo a depreciar
     * @param fecha Fecha de la depreciación
     * @return La depreciación calculada y registrada
     */
    @PostMapping("/calcular/{activoId}")
    public ResponseEntity<Depreciacion> calcularYRegistrar(
            @PathVariable String activoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            if (fecha == null) {
                fecha = LocalDate.now();
            }
            return ResponseEntity.ok(service.calcularYRegistrarDepreciacion(activoId, fecha));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lista todas las depreciaciones registradas
     */
    @GetMapping
    public ResponseEntity<List<Depreciacion>> listarTodas() {
        try {
            return ResponseEntity.ok(service.listarTodas());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene una depreciación por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Depreciacion> obtenerPorId(@PathVariable String id) {
        try {
            return service.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lista todas las depreciaciones de un activo específico
     */
    @GetMapping("/activo/{activoId}")
    public ResponseEntity<List<Depreciacion>> obtenerPorActivoId(@PathVariable String activoId) {
        try {
            List<Depreciacion> depreciaciones = service.obtenerPorActivoId(activoId);
            return depreciaciones.isEmpty() ? 
                ResponseEntity.noContent().build() : 
                ResponseEntity.ok(depreciaciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lista las depreciaciones de un año específico
     */
    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<Depreciacion>> obtenerPorAnio(@PathVariable int anio) {
        try {
            List<Depreciacion> depreciaciones = service.obtenerPorAnio(anio);
            return depreciaciones.isEmpty() ? 
                ResponseEntity.noContent().build() : 
                ResponseEntity.ok(depreciaciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lista las depreciaciones de un período específico (año y mes)
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<Depreciacion>> obtenerPorAnioYMes(
            @RequestParam int anio,
            @RequestParam int mes) {
        try {
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest().build();
            }
            List<Depreciacion> depreciaciones = service.obtenerPorAnioYMes(anio, mes);
            return depreciaciones.isEmpty() ? 
                ResponseEntity.noContent().build() : 
                ResponseEntity.ok(depreciaciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lista las depreciaciones dentro de un rango de fechas
     */
    @GetMapping("/rango")
    public ResponseEntity<List<Depreciacion>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            if (fechaInicio.isAfter(fechaFin)) {
                return ResponseEntity.badRequest().build();
            }
            List<Depreciacion> depreciaciones = service.obtenerPorRangoFechas(fechaInicio, fechaFin);
            return depreciaciones.isEmpty() ? 
                ResponseEntity.noContent().build() : 
                ResponseEntity.ok(depreciaciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Elimina una depreciación por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            return service.eliminarDepreciacion(id) ?
                    ResponseEntity.ok().build() :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 