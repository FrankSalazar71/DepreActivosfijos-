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
            activo.setDescripcion(activoActualizado.getDescripcion());
            activo.setTipo(activoActualizado.getTipo());
            activo.setMarca(activoActualizado.getMarca());
            activo.setModelo(activoActualizado.getModelo());
            activo.setZona(activoActualizado.getZona());
            activo.setCostoAdquisicion(activoActualizado.getCostoAdquisicion());
            activo.setFechaCompra(activoActualizado.getFechaCompra());
            return repository.save(activo);
        }).orElse(null);
    }

    public boolean eliminarActivo(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
