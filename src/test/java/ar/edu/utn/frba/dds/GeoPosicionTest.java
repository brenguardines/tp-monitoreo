package ar.edu.utn.frba.dds;
import ar.edu.utn.frba.dds.repositorio.RepositorioEntidades;
import ar.edu.utn.frba.dds.repositorio.RepositorioIncidentes;
import ar.edu.utn.frba.dds.repositorio.RepositorioMiembros;
import ar.edu.utn.frba.dds.servicio.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeoPosicionTest {
  Entidad e1;
  Entidad e2;
  Entidad e3;
  RepositorioEntidades repo;
  RepositorioIncidentes repoInc;
  RepositorioMiembros repoMiem;

  GeoGoogle geolocalizador;

  @BeforeEach
  void setup() {
    repo=new RepositorioEntidades();
    repoInc=new RepositorioIncidentes();
    repoMiem=new RepositorioMiembros();


    e1=new Entidad("walart","Shopping");

    Incidente i1=new Incidente(new Servicio(1,"Banio"),"bano roto",
        new Establecimiento("San justo","Shopping",new Posicion(100,100),e1),new Miembro("juan"));


    Incidente i2=new Incidente(new Servicio(2,"Banio"),"bano roto",
        new Establecimiento("San justo","Shopping",new Posicion(200,100),e2),new Miembro("juan"));


    Incidente i3=new Incidente(new Servicio(3,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(300,100),e3),new Miembro("juan"));

    repoInc.getIncidentes().add(i1);
    repoInc.getIncidentes().add(i2);
    repoInc.getIncidentes().add(i3);

    Miembro m1=new Miembro("Juan");
    Miembro m2=new Miembro("Jonhy");
    Miembro m3=new Miembro("Jose");

    m1.getServiciosDeInteres().add(new Servicio(1,"Banio"));
    m2.getServiciosDeInteres().add(new Servicio(1,"Banio"));
    m1.getServiciosDeInteres().add(new Servicio(2,"Banio"));
    m2.getServiciosDeInteres().add(new Servicio(2,"Banio"));
    m3.getServiciosDeInteres().add(new Servicio(3,"Banio"));

    repoMiem.getListaMiembros().add(m1);
    repoMiem.getListaMiembros().add(m2);
    repoMiem.getListaMiembros().add(m3);



  }

  @Test
  void obtenerResultadoRankings() {

    mainBuscarPosicion buscador=new mainBuscarPosicion();
    buscador.buscarposicionYmandarMensaje(repoMiem.getListaMiembros(),repoInc.getIncidentes(),new GeoGoogle());
    repoMiem.getListaMiembros().forEach(m-> System.out.println("Cantidad de notifiaciones "+m.getNotificaciones().size()));

  }
}
