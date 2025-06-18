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
        
        // Obtener depreciaciones anteriores para calcular el valor en libros actual
        List<Depreciacion> depreciacionesAnteriores = depreciacionRepository.findByActivoId(activoId);
        double depreciacionAcumulada = depreciacionesAnteriores.stream()
                .mapToDouble(Depreciacion::getValorDepreciado)
                .sum();
        
        double valorLibros = activo.getCostoAdquisicion() - depreciacionAcumulada;
        double valorDepreciado = 0.0;
        double tasaDepreciacion = 0.0;

        // Cálculo según el método de depreciación del activo
        switch (activo.getMetodoDepreciacion()) {
            case LINEA_RECTA:
                valorDepreciado = (activo.getCostoAdquisicion() - activo.getValorResidual()) / activo.getVidaUtilAnios();
                tasaDepreciacion = 1.0 / activo.getVidaUtilAnios();
                break;
            case SUMA_DIGITOS:
                int aniosRestantes = activo.getVidaUtilAnios() - depreciacionesAnteriores.size();
                int sumaDigitos = (activo.getVidaUtilAnios() * (activo.getVidaUtilAnios() + 1)) / 2;
                valorDepreciado = ((activo.getCostoAdquisicion() - activo.getValorResidual()) * aniosRestantes) / sumaDigitos;
                tasaDepreciacion = (double) aniosRestantes / sumaDigitos;
                break;
            case REDUCCION_SALDOS:
                double tasaBase = 1 - Math.pow(activo.getValorResidual() / activo.getCostoAdquisicion(), 1.0 / activo.getVidaUtilAnios());
                valorDepreciado = valorLibros * tasaBase;
                tasaDepreciacion = tasaBase;
                break;
            case UNIDADES_PRODUCIDAS:
                // Para este método necesitaríamos información adicional sobre las unidades producidas
                valorDepreciado = (activo.getCostoAdquisicion() - activo.getValorResidual()) / activo.getVidaUtilAnios();
                tasaDepreciacion = 1.0 / activo.getVidaUtilAnios();
                break;
        }

        // Crear y guardar la nueva depreciación
        Depreciacion depreciacion = new Depreciacion();
        depreciacion.setActivoId(activoId);
        depreciacion.setFecha(fecha);
        depreciacion.setValorDepreciado(valorDepreciado);
        depreciacion.setValorLibros(valorLibros - valorDepreciado);
        depreciacion.setAnioDepreciacion(fecha.getYear());
        depreciacion.setMesDepreciacion(fecha.getMonthValue());
        depreciacion.setTasaDepreciacionAplicada(tasaDepreciacion);
        depreciacion.setObservaciones("Depreciación calculada automáticamente - Método: " + activo.getMetodoDepreciacion());

        return depreciacionRepository.save(depreciacion);
    }

    public boolean eliminarDepreciacion(String id) {
        if (depreciacionRepository.existsById(id)) {
            depreciacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 