package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.parser.Rankingscsv;
import ar.edu.utn.frba.dds.rankings.CriterioPromedioIncidentes;
import ar.edu.utn.frba.dds.rankings.CriterioporCantidadIncidentes;
import ar.edu.utn.frba.dds.repositorio.RepositorioEntidades;
import ar.edu.utn.frba.dds.repositorio.RepositorioIncidentes;
import ar.edu.utn.frba.dds.servicio.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class RankigTipo2Test {
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

    Incidente i1=new Incidente(new Servicio(1,"Banio"),"bano roto",
        new Establecimiento("San justo","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    Incidente i2=new Incidente(new Servicio(1,"Banio"),"Puerta roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    Incidente i3=new Incidente(new Servicio(1,"Banio"),"Grifo roto",
        new Establecimiento("San justo","Shopping",new Posicion(100,100),e1),new Miembro("juan"));

    Incidente i4=new Incidente(new Servicio(4,"Banio"),"Otro grifo roto",
        new Establecimiento("San justo","Shopping",new Posicion(100,100),e2),new Miembro("juan"));

    i1.setId(1);
    i2.setId(2);
    i3.setId(3);
    i4.setId(4);

    i1.setApertura(LocalDateTime.now());
    i1.setCierre(LocalDateTime.now().plusHours(1));

    i2.setApertura(LocalDateTime.now().plusHours(3));
    i2.setCierre(LocalDateTime.now().plusHours(9));

    i3.setApertura(LocalDateTime.now().plusHours(24));
    //3.setCierre(LocalDateTime.now().plusHours(1));

    i4.setApertura(LocalDateTime.now().plusHours(18));
    //i4.setCierre(LocalDateTime.now().plusHours(30));

    repoInc.getIncidentes().add(i1);
    repoInc.getIncidentes().add(i2);
    repoInc.getIncidentes().add(i3);
    repoInc.getIncidentes().add(i4);

    repo.agregarEntidad(e1);
    repo.agregarEntidad(e2);
  }

  @Test
  void obtenerResultadoRankings() {

   // repo.getEntidades().forEach(e-> System.out.println(e.getNombre()+" "+e.getIncidentes().get(0).tiempoDeCierre()));
    System.out.println("=============================");
    CriterioporCantidadIncidentes c= new CriterioporCantidadIncidentes();
    Map<Entidad,Integer> ranking=c.aplicarCriterio(repo.getEntidades(),repoInc.getIncidentes());
    System.out.println("=============================");
    Rankingscsv csv=new Rankingscsv();
    csv.crearRankingscsv(ranking,"RankingPorCantidad");

  }
}
