package cl.unab.inf.ptec102.tarea2Fabi.backend;

public class VerdaderoFalso extends Item {
    private boolean respuestaCorrecta;

    public VerdaderoFalso(String enunciado, NivelBloom nivel, int tiempo, boolean correcta) {
        super(enunciado, nivel, tiempo);
        this.respuestaCorrecta = correcta;
    }

    public VerdaderoFalso(String enunciado, NivelBloom nivel, int tiempoEstimado) {
        super(enunciado, nivel, tiempoEstimado);
    }

    @Override
    public boolean esCorrecta(String respuesta) {
        return Boolean.parseBoolean(respuesta) == respuestaCorrecta;
    }

    @Override
    public TipoItem getTipo() {
        return TipoItem.Verdadero_Falso;
    }

    public boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}