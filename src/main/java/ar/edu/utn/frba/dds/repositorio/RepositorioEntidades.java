package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.Incidente;
import ar.edu.utn.frba.dds.rankings.CriterioRankings;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import java.util.List;


public class RepositorioEntidades implements WithSimplePersistenceUnit {

  public static RepositorioEntidades instancia = new RepositorioEntidades();

  List<Entidad> entidades;

  public RepositorioEntidades() {
    this.entidades = new ArrayList<>();
  }

  public void agregarEntidad(Entidad entidad) {
    this.entidades.add(entidad);
  }

  public List<Entidad> getEntidades() {
    return entidades;
  }

  /*
  public Entidad mayorPromedioTiempoDeCierre() {
    this.entidades.forEach(entidad -> {

      List<Incidente> incidentesCerradosEnElMismoDia = entidad.getIncidentes().stream().filter(incidente -> incidente.diasDeCierre() == 0).collect(Collectors.toList());

      if(incidentesCerradosEnElMismoDia.stream().count() > 0) {

        incidentesCerradosEnElMismoDia.stream().map(i -> i.tiempoDeCierre());
      }
    });

    return null;
  }
 */
  public void agregar(Entidad entidad) {
    entityManager().persist(entidad);
  }

  public Entidad buscarPorEntidad(String nombre) {
    System.out.println(nombre);

    return entityManager().createQuery("from Entidad c where c.nombre=:nom", Entidad.class)
        .setParameter("nom", "%" + nombre + "%")
        .getResultList().get(0);
  }

  public Entidad buscar(int id) {
    return entityManager().find(Entidad.class, id);
  }
}
