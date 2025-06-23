package pe.edu.vallegrande.demo3.deacfijos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;
import pe.edu.vallegrande.demo3.deacfijos.repository.ActivoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivoService {

    private final ActivoRepository repository;

    @Autowired
    public ActivoService(ActivoRepository repository) {
        this.repository = repository;
    }

    public Activo crearActivo(Activo activo) {
        return repository.save(activo);
    }

    public List<Activo> listarActivos() {
        return repository.findAll();
    }

    public Optional<Activo> obtenerActivoPorId(String id) {
        return repository.findById(id);
    }

    public Activo actualizarActivo(String id, Activo activoActualizado) {
        return repository.findById(id).map(activo -> {
            // Actualizar todos los campos
            activo.setDescripcion(activoActualizado.getDescripcion());
            activo.setTipo(activoActualizado.getTipo());
            activo.setMarca(activoActualizado.getMarca());
            activo.setModelo(activoActualizado.getModelo());
            activo.setZona(activoActualizado.getZona());
            activo.setCostoAdquisicion(activoActualizado.getCostoAdquisicion());
            activo.setFechaCompra(activoActualizado.getFechaCompra());
            activo.setCategoriaId(activoActualizado.getCategoriaId());
            activo.setVidaUtilAnios(activoActualizado.getVidaUtilAnios());
            activo.setEstado(activoActualizado.getEstado());
            activo.setValorResidual(activoActualizado.getValorResidual());
            activo.setMetodoDepreciacion(activoActualizado.getMetodoDepreciacion());
            
            // Recalcular la depreciación
            activo.calcularDepreciacionAnual();
            
            return repository.save(activo);
        }).orElse(null);
    }

    /*public boolean eliminarActivo(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }*/

    public Activo save(Activo activo) {
        return repository.save(activo);
    }

    public Optional<Activo> desactivarActivo(String id) {
        return repository.findById(id)
                .map(activo -> {
                    activo.setEstado(Activo.Estado.INACTIVO);
                    // Puedes añadir lógica adicional aquí si es necesario al desactivar
                    return repository.save(activo);
                });
    }

    public Optional<Activo> activarActivo(String id) {
        return repository.findById(id)
                .map(activo -> {
                    activo.setEstado(Activo.Estado.ACTIVO);
                    // Puedes añadir lógica adicional aquí si es necesario al activar
                    return repository.save(activo);
                });
    }

    public List<Activo> listarActivosPorEstado(Activo.Estado estado) {
        return repository.findByEstado(estado);
    }

}
