package cl.unab.inf.ptec102.tarea2Fabi.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Prueba {
    private List<Item> items;
    private List<RespuestaUsuario> respuestas;
    private int indiceActual;

    public Prueba() {
        items = new ArrayList<>();
        respuestas = new ArrayList<>();
        indiceActual = 0;
    }

    public void cargarDesdeCSV(String ruta) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(";");
            String tipo = partes[0];
            String enunciado = partes[1];
            NivelBloom nivel = NivelBloom.valueOf(partes[2].toUpperCase());
            int tiempo = Integer.parseInt(partes[3]);

            if (tipo.equalsIgnoreCase("multiple")) {
                List<String> opciones = Arrays.asList(partes[4].split("\\|"));
                String correcta = partes[5];
                items.add(new OpcionMultiple(enunciado, nivel, tiempo, opciones, correcta));
            } else if (tipo.equalsIgnoreCase("vf")) {
                boolean correcta = Boolean.parseBoolean(partes[4]);
                items.add(new VerdaderoFalso(enunciado, nivel, tiempo, correcta));
            }
        }
        br.close();

        // Inicializar respuestas vacías
        for (Item item : items) {
            respuestas.add(new RespuestaUsuario(item, "")); // respuesta vacía por defecto
        }
    }

    public int getCantidadItems() {
        return items.size();
    }

    public int getTiempoTotal() {
        return items.stream().mapToInt(Item::getTiempoEstimado).sum();
    }

    public Item getItemActual() {
        return items.get(indiceActual);
    }

    public List<Item> getItems() {
        return items;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public void responderActual(String respuesta) {
        RespuestaUsuario ru = new RespuestaUsuario(items.get(indiceActual), respuesta);
        respuestas.set(indiceActual, ru);
    }

    public RespuestaUsuario getRespuestaActual() {
        return respuestas.get(indiceActual);
    }

    public boolean puedeRetroceder() {
        return indiceActual > 0;
    }

    public boolean puedeAvanzar() {
        return indiceActual < items.size() - 1;
    }

    public void avanzar() {
        if (puedeAvanzar()) indiceActual++;
    }

    public void retroceder() {
        if (puedeRetroceder()) indiceActual--;
    }

    public List<RespuestaUsuario> getRespuestas() {
        return respuestas;
    }

    public void setIndiceActual(int indice) {
        this.indiceActual = indice;
    }

    public Map<NivelBloom, Double> resumenPorNivel() {
        Map<NivelBloom, Integer> total = new EnumMap<>(NivelBloom.class);
        Map<NivelBloom, Integer> correctas = new EnumMap<>(NivelBloom.class);

        for (RespuestaUsuario ru : respuestas) {
            NivelBloom nivel = ru.getItem().getNivel();
            total.put(nivel, total.getOrDefault(nivel, 0) + 1);
            if (ru.esCorrecta()) {
                correctas.put(nivel, correctas.getOrDefault(nivel, 0) + 1);
            }
        }

        Map<NivelBloom, Double> resumen = new EnumMap<>(NivelBloom.class);
        for (NivelBloom nivel : total.keySet()) {
            int tot = total.get(nivel);
            int corr = correctas.getOrDefault(nivel, 0);
            resumen.put(nivel, (corr * 100.0) / tot);
        }
        return resumen;
    }

    public Map<TipoItem, Double> resumenPorTipo() {
        Map<TipoItem, Integer> total = new EnumMap<>(TipoItem.class);
        Map<TipoItem, Integer> correctas = new EnumMap<>(TipoItem.class);

        for (RespuestaUsuario ru : respuestas) {
            TipoItem tipo = ru.getItem().getTipo();
            total.put(tipo, total.getOrDefault(tipo, 0) + 1);
            if (ru.esCorrecta()) {
                correctas.put(tipo, correctas.getOrDefault(tipo, 0) + 1);
            }
        }

        Map<TipoItem, Double> resumen = new EnumMap<>(TipoItem.class);
        for (TipoItem tipo : total.keySet()) {
            int tot = total.get(tipo);
            int corr = correctas.getOrDefault(tipo, 0);
            resumen.put(tipo, (corr * 100.0) / tot);
        }
        return resumen;
    }
}
