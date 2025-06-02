package cl.unab.inf.ptec102.tarea2Fabi.backend;

import java.util.List;

public class OpcionMultiple extends Item {
    private List<String> opciones;
    private String respuestaCorrecta;

    public OpcionMultiple(String enunciado, NivelBloom nivel, int tiempo, List<String> opciones, String correcta) {
        super(enunciado, nivel, tiempo);
        this.opciones = opciones;
        this.respuestaCorrecta = correcta;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    @Override
    public boolean esCorrecta(String respuesta) {
        return respuestaCorrecta.equalsIgnoreCase(respuesta);
    }

    @Override
    public TipoItem getTipo() {
        return TipoItem.Opcion_Multiple;
    }
}