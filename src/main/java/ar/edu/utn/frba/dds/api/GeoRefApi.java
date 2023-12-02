package ar.edu.utn.frba.dds.api;

import ar.edu.utn.frba.dds.localizacion.Localizacion;
import java.util.List;

public interface GeoRefApi {
  List<Localizacion> traerMunicipios();

  List<Localizacion> traerProvincias();

  List<Localizacion> traerDepartamentos();

}
