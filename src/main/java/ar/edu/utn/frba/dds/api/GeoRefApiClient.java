package ar.edu.utn.frba.dds.api;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GeoRefApiClient {
  private Client client = ClientBuilder.newClient();

  private String apiPath = "https://apis.datos.gob.ar/georef/api";

  public List<JSONArray> traerJsonMunicipios() {
    return this.traerTodosLosRegistros("municipios");
  }

  public List<JSONArray> traerJsonDepartamentos() {
    return this.traerTodosLosRegistros("departamentos");
  }

  public JSONArray traerJsonProvincias() {
    String path = "provincias";
    String resultado = this.llamadoApi(path);

    try {
      JSONObject jsonProvincias = new JSONObject(resultado);
      JSONArray jsonArrayProvincias = jsonProvincias.getJSONArray(path);
      return jsonArrayProvincias;
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  private List<JSONArray> traerTodosLosRegistros(String path) {
    List<JSONArray> lista = new ArrayList<>();

    int totalDeRegistros = obtenerTotal(path);
    int cantidadDeRegistros = obtenerCantidadDeRegistros(path);
    int paginas = totalDeRegistros / cantidadDeRegistros;
    int inicio = 0;

    try {
      for (int i = 0; i <= paginas; i++) {

        String resultado = client.target(apiPath)
            .path(path)
            .queryParam("inicio", inicio)
            .request(MediaType.APPLICATION_JSON)
            .get(String.class);
        JSONObject json = new JSONObject(resultado);
        JSONArray jsonArray = json.getJSONArray(path);
        lista.add(jsonArray);
        inicio = inicio + 10;
      }
      return lista;
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  private int obtenerTotal(String path) {
    try {
      String resultado = this.llamadoApi(path);
      JSONObject jsonResultado = new JSONObject(resultado);
      int total = jsonResultado.getInt("total");
      return total;
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }

  }

  private int obtenerCantidadDeRegistros(String path) {
    try {
      String resultado = this.llamadoApi(path);
      JSONObject jsonResultado = new JSONObject(resultado);
      int cantidad = jsonResultado.getInt("cantidad");
      return cantidad;
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  private String llamadoApi(String path) {
    return client.target(apiPath)
        .path(path)
        .request(MediaType.APPLICATION_JSON)
        .get(String.class);
  }
}