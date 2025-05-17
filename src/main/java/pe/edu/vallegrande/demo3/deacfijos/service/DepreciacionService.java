package pe.edu.vallegrande.demo3.deacfijos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;
import pe.edu.vallegrande.demo3.deacfijos.model.Depreciacion;
import pe.edu.vallegrande.demo3.deacfijos.repository.DepreciacionRepository;
import pe.edu.vallegrande.demo3.deacfijos.repository.ActivoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DepreciacionService {

    private final DepreciacionRepository depreciacionRepository;
    private final ActivoRepository activoRepository;

    @Autowired
    public DepreciacionService(DepreciacionRepository depreciacionRepository, ActivoRepository activoRepository) {
        this.depreciacionRepository = depreciacionRepository;
        this.activoRepository = activoRepository;
    }

    public Depreciacion registrarDepreciacion(Depreciacion depreciacion) {
        return depreciacionRepository.save(depreciacion);
    }

    public List<Depreciacion> listarTodas() {
        return depreciacionRepository.findAll();
    }

    public Optional<Depreciacion> obtenerPorId(String id) {
        return depreciacionRepository.findById(id);
    }

    public List<Depreciacion> obtenerPorActivoId(String activoId) {
        return depreciacionRepository.findByActivoId(activoId);
    }

    public List<Depreciacion> obtenerPorAnio(int anio) {
        return depreciacionRepository.findByAnioDepreciacion(anio);
    }

    public List<Depreciacion> obtenerPorAnioYMes(int anio, int mes) {
        return depreciacionRepository.findByAnioDepreciacionAndMesDepreciacion(anio, mes);
    }

    public List<Depreciacion> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return depreciacionRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public Depreciacion calcularYRegistrarDepreciacion(String activoId, LocalDate fecha) {
        Optional<Activo> activoOpt = activoRepository.findById(activoId);
        
        if (activoOpt.isEmpty()) {
            throw new RuntimeException("Activo no encontrado");
        }

        Activo activo = activoOpt.get();
        double valorDepreciado = 0.0;
        double valorLibros = 0.0;

        // Cálculo según el método de depreciación del activo
        switch (activo.getMetodoDepreciacion()) {
            case LINEA_RECTA:
                valorDepreciado = (activo.getCostoAdquisicion() - activo.getValorResidual()) / activo.getVidaUtilAnios();
                break;
            case SUMA_DIGITOS:
                // Implementar cálculo para suma de dígitos
                break;
            case REDUCCION_SALDOS:
                // Implementar cálculo para reducción de saldos
                break;
            case UNIDADES_PRODUCIDAS:
                // Implementar cálculo para unidades producidas
                break;
        }

        // Obtener la última depreciación para calcular el nuevo valor en libros
        List<Depreciacion> depreciacionesAnteriores = depreciacionRepository.findByActivoId(activoId);
        if (depreciacionesAnteriores.isEmpty()) {
            valorLibros = activo.getCostoAdquisicion() - valorDepreciado;
        } else {
            valorLibros = depreciacionesAnteriores.get(depreciacionesAnteriores.size() - 1).getValorLibros() - valorDepreciado;
        }

        Depreciacion nuevaDepreciacion = new Depreciacion(activoId, fecha, valorDepreciado, valorLibros);
        nuevaDepreciacion.setTasaDepreciacionAplicada(valorDepreciado / activo.getCostoAdquisicion());
        
        return depreciacionRepository.save(nuevaDepreciacion);
    }

    public boolean eliminarDepreciacion(String id) {
        if (depreciacionRepository.existsById(id)) {
            depreciacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 