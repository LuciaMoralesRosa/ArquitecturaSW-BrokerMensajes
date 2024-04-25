public class Objeto {
    private Integer prioridad;
    private Long momentoEntrada;
    private Object mensaje;

    public Objeto(Integer prioridad, Long momentoEntrada, Object mensaje) {
        this.prioridad = prioridad;
        this.momentoEntrada = momentoEntrada;
        this.mensaje = mensaje;
    }
    
    public Integer getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    public Long getMomentoEntrada() {
        return momentoEntrada;
    }
    public void setMomentoEntrada(Long momentoEntrada) {
        this.momentoEntrada = momentoEntrada;
    }
    public Object getMensaje() {
        return mensaje;
    }
    public void setMensaje(Object mensaje) {
        this.mensaje = mensaje;
    }

}
