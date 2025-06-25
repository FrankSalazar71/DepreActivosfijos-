package pe.edu.vallegrande.demo3.deacfijos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.deacfijos.model.Sede;
import pe.edu.vallegrande.demo3.deacfijos.repository.SedeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SedeService {

    private final SedeRepository repository;

    @Autowired
    public SedeService(SedeRepository repository) {
        this.repository = repository;
    }

    public Sede crearSede(Sede sede) {
        // Asegurarse de que el estado se establezca si no viene en el request, o usar el por defecto
        if (sede.getEstado() == null) {
             sede.setEstado(Sede.Estado.ACTIVO);
        }
        return repository.save(sede);
    }

    public List<Sede> listarTodas() {
        return repository.findAll();
    }

    public Optional<Sede> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public Sede actualizarSede(String id, Sede sedeActualizada) {
        return repository.findById(id).map(sede -> {
            sede.setNombreSede(sedeActualizada.getNombreSede()); // Usar nombreSede
            sede.setDepartamento(sedeActualizada.getDepartamento()); // Actualizar departamento
            sede.setDireccion(sedeActualizada.getDireccion());
            sede.setEstado(sedeActualizada.getEstado()); // Actualizar estado
            // Actualiza otros campos si los tienes
            return repository.save(sede);
        }).orElse(null);
    }


    public boolean eliminarSede(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Sede> desactivarSede(String id) {
        return repository.findById(id)
                .map(sede -> {
                    sede.setEstado(Sede.Estado.INACTIVO);
                    return repository.save(sede);
                });
    }

    public Optional<Sede> activarSede(String id) {
        return repository.findById(id)
                .map(sede -> {
                    sede.setEstado(Sede.Estado.ACTIVO);
                    return repository.save(sede);
                });
    }

    public List<Sede> listarSedesPorEstado(Sede.Estado estado) {
        return repository.findByEstado(estado);
    }

}