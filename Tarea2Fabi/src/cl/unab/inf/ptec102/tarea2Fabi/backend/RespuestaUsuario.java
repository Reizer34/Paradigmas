package cl.unab.inf.ptec102.tarea2Fabi.backend;

public class RespuestaUsuario {
    private Item item;
    private String respuesta;

    public RespuestaUsuario(Item item, String respuesta) {
        this.item = item;
        this.respuesta = respuesta;
    }

    public boolean esCorrecta() {
        return item.esCorrecta(respuesta);
    }

    public Item getItem() {
        return item;
    }

    public String getRespuesta() {
        return respuesta;
    }
}