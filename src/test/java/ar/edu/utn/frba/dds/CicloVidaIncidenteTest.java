package ar.edu.utn.frba.dds;
import ar.edu.utn.frba.dds.repositorio.RepositorioComunidades;
import ar.edu.utn.frba.dds.repositorio.RepositorioEntidades;
import ar.edu.utn.frba.dds.repositorio.RepositorioIncidentes;
import ar.edu.utn.frba.dds.repositorio.RepositorioMiembros;
import ar.edu.utn.frba.dds.servicio.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class CicloVidaIncidenteTest {
  Entidad e1;

  Servicio s1;
  Servicio s2;
  Comunidad c1;
  Comunidad c2;
  RepositorioEntidades repo;
  RepositorioIncidentes repoInc;
  RepositorioMiembros repoMiem;
  RepositorioComunidades repoComu;

  @BeforeEach
  void setup() {
    repoComu=new RepositorioComunidades();
    repo=new RepositorioEntidades();
    repoInc=new RepositorioIncidentes();
    repoMiem=new RepositorioMiembros();

    c1=new Comunidad();
    c2=new Comunidad();


    e1=new Entidad("walart","Shopping");

    s1=new Servicio(1,"Banio");
    s2=new Servicio(2,"Escalera mecanica");

    c1.getServiciosInteres().add(s1);
    c2.getServiciosInteres().add(s2);

    repoComu.agregarComunidad(c1);
    repoComu.agregarComunidad(c2);


    Miembro m1=new Miembro("Juan");
    m1.getServiciosDeInteres().add(s1);
    Miembro m2=new Miembro("Jonhy");
    m2.getServiciosDeInteres().add(s1);
    Miembro m3=new Miembro("Jose");
    m3.getServiciosDeInteres().add(s2);

    repoMiem.getListaMiembros().add(m1);
    repoMiem.getListaMiembros().add(m2);
    repoMiem.getListaMiembros().add(m3);

    c1.getMiembros().add(m1);
    c1.getMiembros().add(m2);
    c2.getMiembros().add(m3);

  }
  @Test
  public void seCreoIncidente(){
    Incidente i1=new Incidente(new Servicio(1,"Banio"),"bano roto",
        new Establecimiento("San justo","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    Incidente i2=new Incidente(new Servicio(1,"Banio"),"Filtra agua",
        new Establecimiento("San justo","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    Incidente i3=new Incidente(new Servicio(2,"Banio"),"Filtra agua",
        new Establecimiento("Flores","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    repoInc.agregarIncidente(i1,repoComu);
    repoInc.agregarIncidente(i2,repoComu);
    repoInc.agregarIncidente(i3,repoComu);

    for(Miembro m:repoMiem.getListaMiembros()){
      System.out.println("Notificaciones del miembro "+m.getNombre()+" son "+m.getNotificaciones().size());
    }

  }
}
