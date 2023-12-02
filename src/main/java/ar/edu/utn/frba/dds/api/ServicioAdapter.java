package ar.edu.utn.frba.dds.api;

import ar.edu.utn.frba.dds.localizacion.Localizacion;
import java.util.List;

public class ServicioAdapter {
  GeoRefApi geoRefApi;

  public ServicioAdapter(GeoRefApi geoRefApi) {
    this.geoRefApi = geoRefApi;
  }

  public List<Localizacion> traerProvincias() {
    return geoRefApi.traerProvincias();
  }

  public List<Localizacion> traerMunicipios() {
    return geoRefApi.traerMunicipios();
  }

  public List<Localizacion> traerDepartamentos() {
    return geoRefApi.traerDepartamentos();
  }
}
