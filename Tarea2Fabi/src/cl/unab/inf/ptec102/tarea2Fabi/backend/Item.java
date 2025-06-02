package cl.unab.inf.ptec102.tarea2Fabi.backend;

public abstract class Item {
    protected String enunciado;
    protected NivelBloom nivel;
    protected int tiempoEstimado; // en segundos

    public Item(String enunciado, NivelBloom nivel, int tiempoEstimado) {
        this.enunciado = enunciado;
        this.nivel = nivel;
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public NivelBloom getNivel() {
        return nivel;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public abstract TipoItem getTipo();
    public abstract boolean esCorrecta(String respuesta);

}
