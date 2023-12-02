package ar.edu.utn.frba.dds.api;

import ar.edu.utn.frba.dds.localizacion.Departamento;
import ar.edu.utn.frba.dds.localizacion.Localizacion;
import ar.edu.utn.frba.dds.localizacion.Municipio;
import ar.edu.utn.frba.dds.localizacion.Provincia;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServicioGeoRefApi implements GeoRefApi {

  private GeoRefApiClient geoRefApiClient;

  public ServicioGeoRefApi(GeoRefApiClient geoRefApiClient) {
    this.geoRefApiClient = geoRefApiClient;
  }

  @Override
  public List<Localizacion> traerProvincias() {
    List<Localizacion> listaProvincia = new ArrayList<>();
    JSONArray listaJsonProvincias = geoRefApiClient.traerJsonProvincias();
    for (int i = 0; i < listaJsonProvincias.length(); i++) {
      try {
        JSONObject jsonProvincia = listaJsonProvincias.getJSONObject(i);
        int idProvincia = jsonProvincia.getInt("id");
        String nombreProvincia = jsonProvincia.getString("nombre");
        Provincia provincia = new Provincia(idProvincia, nombreProvincia);
        listaProvincia.add(provincia);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }
    return listaProvincia;
  }

  @Override
  public List<Localizacion> traerMunicipios() {
    List<JSONArray> listaJsonMunicipios = geoRefApiClient.traerJsonMunicipios();

    List<Localizacion> listaMunicipios = this.formatear(listaJsonMunicipios, "municipio");

    return listaMunicipios;
  }

  @Override
  public List<Localizacion> traerDepartamentos() {
    List<JSONArray> listaJsonDepartamentos = geoRefApiClient.traerJsonDepartamentos();

    List<Localizacion> listaDepartamentos = this.formatear(listaJsonDepartamentos, "departamento");

    return listaDepartamentos;
  }

  private List<Localizacion> formatear(List<JSONArray> listaJson, String tipo) {
    List<Localizacion> lista = new ArrayList<>();

    for (JSONArray arrayListaJson : listaJson) {
      for (int i = 0; i < arrayListaJson.length(); i++) {
        try {
          JSONObject json = arrayListaJson.getJSONObject(i);
          int id = json.getInt("id");
          String nombre = json.getString("nombre");
          int idProvincia = json.getJSONObject("provincia").getInt("id");
          String nombreProvincia = json.getJSONObject("provincia").getString("nombre");
          Provincia provincia = new Provincia(idProvincia, nombreProvincia);

          if (tipo == "departamento") {
            Departamento departamento = new Departamento(id, nombre, provincia);
            lista.add(departamento);
          }
          if (tipo == "municipio") {
            Municipio municipio = new Municipio(id, nombre, provincia);
            lista.add(municipio);
          }
        } catch (JSONException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return lista;
  }

}
