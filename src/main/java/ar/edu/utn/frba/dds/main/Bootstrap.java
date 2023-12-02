package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.*;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.repositorio.RepositorioEntidades;
import ar.edu.utn.frba.dds.repositorio.RepositorioEstablecimiento;
import ar.edu.utn.frba.dds.repositorio.RepositorioIncidentes;
import ar.edu.utn.frba.dds.repositorio.RepositorioServicio;
import ar.edu.utn.frba.dds.servicio.CategoriaTipoServicio;
import ar.edu.utn.frba.dds.servicio.Servicio;
import ar.edu.utn.frba.dds.servicio.TipoServicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class Bootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {

    Entidad enti = new Entidad("Waalmart", "Shopping");

    withTransaction(() -> {
      RepositorioEntidades.instancia.agregar(enti);
    });
    Entidad buscada = RepositorioEntidades.instancia.buscar(1);
    Establecimiento e = new Establecimiento("Sanjusto",
        "Mediano", new Posicion(100, 100), buscada);

    withTransaction(() -> {
      RepositorioEstablecimiento.instancia.agregar(e);
    });

    Servicio s1 = new Servicio("Baño", false,
        "En el segundo piso", TipoServicio.BANIO);
    Servicio s2 = new Servicio("Ascensor", false,
        "En el segundo piso", TipoServicio.ASCENSOR);
    Servicio s3 = new Servicio("Escalera mecanica", false,
        "En el segundo piso", TipoServicio.ESCALERA_MECANICA);
    withTransaction(() -> {
      RepositorioServicio.instancia.agregar(s1);
      RepositorioServicio.instancia.agregar(s2);
      RepositorioServicio.instancia.agregar(s3);
    });

    Establecimiento encontrado = RepositorioEstablecimiento.instancia.buscar(1);

    Incidente i1 = new Incidente(RepositorioServicio.instancia.buscar(1),
        "Grifo roto", encontrado, null);
    Incidente i2 = new Incidente(RepositorioServicio.instancia.buscar(2),
        "No funciona", encontrado, null);
    Incidente i3 = new Incidente(RepositorioServicio.instancia.buscar(3),
        "Parada", encontrado, null);

    withTransaction(() -> {
      RepositorioIncidentes.instancia.agregar(i1);
      RepositorioIncidentes.instancia.agregar(i2);
      RepositorioIncidentes.instancia.agregar(i3);
    });

  }

}
