package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.servicio.Servicio;
import java.util.List;

public class mainBuscarPosicion {

  public mainBuscarPosicion() {
  }

  public void buscarposicionYmandarMensaje(List<Miembro> miembros,
                                           List<Incidente> incidentes, GeoGoogle localizador) {
    //filtra los incidentes que siguen abiertos/vigentes
    List<Incidente> incidentesAbiertos = incidentes.stream().filter(m -> m.getVigente()).toList();
    System.out.println("Cantidad de incidentes abiertos " + incidentesAbiertos.size());
    for (Incidente i : incidentesAbiertos) {

      //Una vez obtenidos los incidentes abiertos por cada uno
      // se buscara los miembros interesados en el servicio del incidente
      List<Miembro> miembrosConInteres = miembros.stream()
          .filter(m -> esDeInteres(m.getServiciosDeInteres(), i.getServicio())).toList();
      System.out.println("Cantidad de miembros con interes en servicio "
          + i.getServicio().getId() + " son " + miembrosConInteres.size());
      for (Miembro m : miembrosConInteres) {
        //Ahora con los miembros interesados a cada uno se les
        // buscara la posicion mediante la API entrante GeoBoogle a la cual le damos un miembro

        //obtiene posicion del miembro
        Posicion deMiembro = localizador.obtenerposicion(m);

        //compara latitudes y longitudes del establecimiento y
        // del miembro para ver si este esta cerca del incidente
        if (deMiembro.getLongitud() == i.getEstablecimiento().getUbicacion().getLongitud()
            && deMiembro.getLatitud() == i.getEstablecimiento().getUbicacion().getLatitud()) {
          System.out.println("El miembro " + m.getNombre() + " esta cerca del incidente");
          m.guardarNotificacion(i);

        } else {
          System.out.println("El miembro " + m.getNombre() + " no esta cerca del incidente");
        }
      }
    }
  }

  public boolean esDeInteres(List<Servicio> intereses, Servicio s) {
    return intereses.stream().anyMatch(i -> i.getId() == s.getId());
  }
}
