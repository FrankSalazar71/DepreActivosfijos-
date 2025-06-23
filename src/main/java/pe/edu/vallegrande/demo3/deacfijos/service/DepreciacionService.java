package pe.edu.vallegrande.demo3.deacfijos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.deacfijos.model.Activo;
import pe.edu.vallegrande.demo3.deacfijos.model.Depreciacion;
import pe.edu.vallegrande.demo3.deacfijos.repository.DepreciacionRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DepreciacionService {

    @Autowired
    private DepreciacionRepository repository;

    @Autowired
    private ActivoService activoService; // Necesitamos el servicio de activos

    /**
     * Registra una depreciación manualmente.
     * @param depreciacion La depreciación a registrar.
     * @return La depreciación registrada.
     */
    public Depreciacion registrarDepreciacion(Depreciacion depreciacion) {
        // Puedes agregar validaciones aquí si es necesario
        return repository.save(depreciacion);
    }

    /**
     * Calcula y registra la depreciación de un activo para una fecha específica.
     * @param activoId ID del activo a depreciar.
     * @param fecha Fecha de la depreciación.
     * @return La depreciación calculada y registrada.
     * @throws RuntimeException si el activo no se encuentra o hay un error en el cálculo.
     */
    public Depreciacion calcularYRegistrarDepreciacion(String activoId, LocalDate fecha) {
        // Corregido: Usar obtenerActivoPorId en lugar de obtenerPorId
        Optional<Activo> activoOpt = activoService.obtenerActivoPorId(activoId);
        if (!activoOpt.isPresent()) {
            throw new RuntimeException("Activo no encontrado con ID: " + activoId);
        }
        Activo activo = activoOpt.get();

        // Obtener depreciaciones anteriores para calcular el valor en libros actual
        // Corregido: El método findByActivoIdAndFechaBefore debe existir en el repositorio
        List<Depreciacion> depreciacionesAnteriores = repository.findByActivoIdAndFechaBefore(activoId, fecha);
        double depreciacionAcumulada = depreciacionesAnteriores.stream()
                .mapToDouble(Depreciacion::getValorDepreciado)
                .sum();

        double valorActual = activo.getCostoAdquisicion() - depreciacionAcumulada;

        // Calcular la depreciación para el período actual
        double valorDepreciadoPeriodo = 0;
        double tasaDepreciacionAplicada = 0;

        // Calcular la depreciación según el método
        switch (activo.getMetodoDepreciacion()) {
            case LINEA_RECTA:
                if (activo.getVidaUtilAnios() > 0) {
                    double valorDepreciable = activo.getCostoAdquisicion() - activo.getValorResidual();
                    valorDepreciadoPeriodo = valorDepreciable / activo.getVidaUtilAnios(); // Depreciación anual
                    tasaDepreciacionAplicada = 1.0 / activo.getVidaUtilAnios(); // Tasa anual
                    // Si necesitas depreciación mensual, divide por 12
                    // valorDepreciadoPeriodo /= 12;
                    // tasaDepreciacionAplicada /= 12;
                }
                break;
            case SUMA_DIGITOS:
                // Implementación de Suma de Dígitos
                if (activo.getVidaUtilAnios() > 0) {
                    double valorDepreciable = activo.getCostoAdquisicion() - activo.getValorResidual();
                    int sumaDigitos = activo.getVidaUtilAnios() * (activo.getVidaUtilAnios() + 1) / 2;
                    // Calcular años restantes (simplificado, podrías necesitar una lógica más precisa)
                    long aniosTranscurridos = ChronoUnit.YEARS.between(activo.getFechaCompra().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(), fecha);
                    int aniosRestantes = activo.getVidaUtilAnios() - (int) aniosTranscurridos;
                    if (aniosRestantes > 0) {
                         valorDepreciadoPeriodo = valorDepreciable * aniosRestantes / sumaDigitos;
                         tasaDepreciacionAplicada = (double) aniosRestantes / sumaDigitos;
                         // Si necesitas depreciación mensual, ajusta el cálculo
                    } else {
                         valorDepreciadoPeriodo = 0;
                         tasaDepreciacionAplicada = 0;
                    }
                }
                break;
            case REDUCCION_SALDOS:
                 // Implementación de Reducción de Saldos (ejemplo simplificado con tasa doble línea recta)
                 if (activo.getVidaUtilAnios() > 0) {
                     double tasaDobleLineaRecta = (1.0 / activo.getVidaUtilAnios()) * 2;
                     valorDepreciadoPeriodo = valorActual * tasaDobleLineaRecta;
                     tasaDepreciacionAplicada = tasaDobleLineaRecta;
                     // Asegurar que no deprecie por debajo del valor residual
                     if (valorActual - valorDepreciadoPeriodo < activo.getValorResidual()) {
                         valorDepreciadoPeriodo = valorActual - activo.getValorResidual();
                     }
                     // Si necesitas depreciación mensual, ajusta el cálculo
                 }
                 break;
            case UNIDADES_PRODUCIDAS:
                 // Implementación de Unidades Producidas (requiere datos de producción)
                 // Este método necesita información sobre la producción total esperada y la producción del período
                 // Ejemplo: (Costo - Valor Residual) / Unidades Totales * Unidades Producidas en el Período
                 // Como no tenemos datos de producción aquí, lo dejamos como 0 o podrías usar una lógica placeholder
                 valorDepreciadoPeriodo = 0; // O implementar lógica real con datos de producción
                 tasaDepreciacionAplicada = 0; // O calcular tasa basada en unidades
                 break;
        }

        // Asegurar que no se deprecie por debajo del valor residual
        double nuevoValorLibros = valorActual - valorDepreciadoPeriodo;
        if (nuevoValorLibros < activo.getValorResidual()) {
            valorDepreciadoPeriodo = valorActual - activo.getValorResidual();
            nuevoValorLibros = activo.getValorResidual();
        }

        // Crear y guardar el registro de depreciación
        Depreciacion nuevaDepreciacion = new Depreciacion();
        nuevaDepreciacion.setActivoId(activoId);
        nuevaDepreciacion.setFecha(fecha);
        nuevaDepreciacion.setValorDepreciado(valorDepreciadoPeriodo);
        nuevaDepreciacion.setValorLibros(nuevoValorLibros);
        nuevaDepreciacion.setAnioDepreciacion(fecha.getYear());
        nuevaDepreciacion.setMesDepreciacion(fecha.getMonthValue());
        nuevaDepreciacion.setTasaDepreciacionAplicada(tasaDepreciacionAplicada);
        nuevaDepreciacion.setObservaciones("Depreciación calculada automáticamente - Método: " + activo.getMetodoDepreciacion());

        return repository.save(nuevaDepreciacion);
    }

    /**
     * Actualiza una depreciación existente.
     * @param depreciacion La depreciación con los datos actualizados.
     * @return La depreciación actualizada.
     */
    public Depreciacion actualizarDepreciacion(Depreciacion depreciacion) {
        // Aquí simplemente guardamos el objeto, asumiendo que el ID ya está seteado
        // Puedes agregar lógica para validar qué campos se pueden actualizar
        return repository.save(depreciacion);
    }


    /**
     * Lista todas las depreciaciones.
     * @return Lista de todas las depreciaciones.
     */
    public List<Depreciacion> listarTodas() {
        return repository.findAll();
    }

    /**
     * Obtiene una depreciación por su ID.
     * @param id ID de la depreciación.
     * @return Optional con la depreciación si se encuentra.
     */
    public Optional<Depreciacion> obtenerPorId(String id) {
        return repository.findById(id);
    }

    /**
     * Lista las depreciaciones de un activo específico.
     * @param activoId ID del activo.
     * @return Lista de depreciaciones del activo.
     */
    public List<Depreciacion> obtenerPorActivoId(String activoId) {
        return repository.findByActivoId(activoId);
    }

    /**
     * Lista las depreciaciones de un año específico.
     * @param anio Año de la depreciación.
     * @return Lista de depreciaciones del año.
     */
    public List<Depreciacion> obtenerPorAnio(int anio) {
        return repository.findByAnioDepreciacion(anio);
    }

    /**
     * Lista las depreciaciones de un período específico (año y mes).
     * @param anio Año de la depreciación.
     * @param mes Mes de la depreciación.
     * @return Lista de depreciaciones del período.
     */
    public List<Depreciacion> obtenerPorAnioYMes(int anio, int mes) {
        return repository.findByAnioDepreciacionAndMesDepreciacion(anio, mes);
    }

    /**
     * Lista las depreciaciones dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de depreciaciones dentro del rango.
     */
    public List<Depreciacion> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaBetween(fechaInicio, fechaFin);
    }

    /**
     * Elimina una depreciación por su ID.
     * @param id ID de la depreciación a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminarDepreciacion(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}