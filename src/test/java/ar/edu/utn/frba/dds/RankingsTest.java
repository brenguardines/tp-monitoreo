package ar.edu.utn.frba.dds;
import ar.edu.utn.frba.dds.parser.Rankingscsv;
import ar.edu.utn.frba.dds.rankings.CriterioPromedioIncidentes;
import ar.edu.utn.frba.dds.repositorio.RepositorioEntidades;
import ar.edu.utn.frba.dds.repositorio.RepositorioIncidentes;
import ar.edu.utn.frba.dds.servicio.Servicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class RankingsTest {
  Entidad e1;
  Entidad e2;
  Entidad e3;
  RepositorioEntidades repo;
  RepositorioIncidentes repoInc;

  @BeforeEach
  void setup() {
     repo=new RepositorioEntidades();
     repoInc=new RepositorioIncidentes();
     e1=new Entidad("walart","Shopping");
     e1.setId(1);
     e2=new Entidad("jumbo","Shopping");
     e2.setId(2);
     e3=new Entidad("changoMas","Shopping");
     e3.setId(3);
    Incidente i1=new Incidente(new Servicio(1,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    Incidente i2=new Incidente(new Servicio(2,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e2),new Miembro("juan"));

    Incidente i3=new Incidente(new Servicio(3,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e3),new Miembro("juan"));

    i1.setId(1);
    i2.setId(2);
    i3.setId(3);

    i1.setCierre(LocalDateTime.now().plusHours(3));
    i2.setCierre(LocalDateTime.now().plusHours(5));
    i3.setCierre(LocalDateTime.now().plusHours(1));

    repoInc.getIncidentes().add(i1);
    repoInc.getIncidentes().add(i2);
    repoInc.getIncidentes().add(i3);

    repo.agregarEntidad(e1);
    repo.agregarEntidad(e2);
    repo.agregarEntidad(e3);
  }

  @Test
  void obtenerResultadoRankings() {



    System.out.println("=============================");
    CriterioPromedioIncidentes c= new CriterioPromedioIncidentes();
    Map<Entidad,Integer> ranking=c.aplicarCriterio(repo.getEntidades(),repoInc.getIncidentes());
    System.out.println("=============================");
    Rankingscsv csv=new Rankingscsv();
    csv.crearRankingscsv(ranking,"RankingPromedio");



  }
  @Test
  void segundoTestRankings(){
    Incidente i4=new Incidente(new Servicio(1,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e1),new Miembro("juan"));
    i4.setCierre(LocalDateTime.now().plusHours(3));
    Incidente i5=new Incidente(new Servicio(2,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e1),new Miembro("juan"));
    i5.setCierre(LocalDateTime.now().plusHours(3));
    Incidente i6=new Incidente(new Servicio(3,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e3),new Miembro("juan"));

    i4.setId(4);
    i5.setId(5);
    i6.setId(6);

    i4.setCierre(LocalDateTime.now().plusHours(3));
    i5.setCierre(LocalDateTime.now().plusHours(5));
    i6.setCierre(LocalDateTime.now().plusHours(1));

    repoInc.getIncidentes().add(i4);
    repoInc.getIncidentes().add(i5);
    repoInc.getIncidentes().add(i6);

    repo.getEntidades().forEach(e-> System.out.println(e.getNombre()));
    System.out.println("=============================");
    CriterioPromedioIncidentes c= new CriterioPromedioIncidentes();
    Map<Entidad,Integer> ranking=c.aplicarCriterio(repo.getEntidades(),repoInc.getIncidentes());
    Rankingscsv csv=new Rankingscsv();
    csv.crearRankingscsv(ranking,"RankingPromedio");
  }

}
