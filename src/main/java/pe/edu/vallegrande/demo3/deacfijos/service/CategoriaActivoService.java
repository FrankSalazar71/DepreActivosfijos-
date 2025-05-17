package pe.edu.vallegrande.demo3.deacfijos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.deacfijos.model.CategoriaActivo;
import pe.edu.vallegrande.demo3.deacfijos.repository.CategoriaActivoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaActivoService {

    private final CategoriaActivoRepository repository;

    @Autowired
    public CategoriaActivoService(CategoriaActivoRepository repository) {
        this.repository = repository;
    }

    public CategoriaActivo crear(CategoriaActivo categoria) {
        categoria.setFechaCreacion(LocalDateTime.now());
        return repository.save(categoria);
    }

    public List<CategoriaActivo> listarTodos() {
        return repository.findAll();
    }

    public Optional<CategoriaActivo> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public CategoriaActivo actualizar(String id, CategoriaActivo categoriaActualizada) {
        return repository.findById(id)
                .map(categoria -> {
                    categoria.setNombreCategoria(categoriaActualizada.getNombreCategoria());
                    categoria.setDescripcion(categoriaActualizada.getDescripcion());
                    categoria.setEstado(categoriaActualizada.getEstado());
                    categoria.setVidaUtilPredeterminada(categoriaActualizada.getVidaUtilPredeterminada());
                    categoria.setTasaDepreciacionAnual(categoriaActualizada.getTasaDepreciacionAnual());
                    categoria.setCodigoContable(categoriaActualizada.getCodigoContable());
                    categoria.setFechaModificacion(LocalDateTime.now());
                    return repository.save(categoria);
                })
                .orElse(null);
    }

    public boolean eliminar(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
} 