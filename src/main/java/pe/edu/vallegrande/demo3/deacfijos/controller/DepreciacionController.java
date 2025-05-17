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

    @PostMapping
    public ResponseEntity<Depreciacion> registrar(@RequestBody Depreciacion depreciacion) {
        return ResponseEntity.ok(service.registrarDepreciacion(depreciacion));
    }

    @PostMapping("/calcular/{activoId}")
    public ResponseEntity<Depreciacion> calcularYRegistrar(
            @PathVariable String activoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.calcularYRegistrarDepreciacion(activoId, fecha));
    }

    @GetMapping
    public ResponseEntity<List<Depreciacion>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depreciacion> obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activo/{activoId}")
    public ResponseEntity<List<Depreciacion>> obtenerPorActivoId(@PathVariable String activoId) {
        return ResponseEntity.ok(service.obtenerPorActivoId(activoId));
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<Depreciacion>> obtenerPorAnio(@PathVariable int anio) {
        return ResponseEntity.ok(service.obtenerPorAnio(anio));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Depreciacion>> obtenerPorAnioYMes(
            @RequestParam int anio,
            @RequestParam int mes) {
        return ResponseEntity.ok(service.obtenerPorAnioYMes(anio, mes));
    }

    @GetMapping("/rango")
    public ResponseEntity<List<Depreciacion>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(service.obtenerPorRangoFechas(fechaInicio, fechaFin));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return service.eliminarDepreciacion(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }
} 