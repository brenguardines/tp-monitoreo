package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Comunidad;
import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Incidente;
import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioComunidades implements WithSimplePersistenceUnit {
  public static RepositorioComunidades instancia = new RepositorioComunidades();
  List<Comunidad> comunidades;

  public RepositorioComunidades() {
    this.comunidades = new ArrayList<>();
  }

  public void agregarComunidad(Comunidad comunidad) {
    this.comunidades.add(comunidad);
  }

  public void agregar(Comunidad comunidad) {
    entityManager().persist(comunidad);
  }

  public List<Comunidad> buscarInteresComunidades(Servicio servicio) {
    return this.comunidades.stream().filter(i -> i.estaInteresado(servicio)).
        collect(Collectors.toList());
  }

  public List<Comunidad> filtrarPorMiembro(Miembro miembro) {
    List<Comunidad> resultado = new ArrayList<>();
    for (Comunidad com : comunidades) {
      if (com.getMiembros().contains(miembro)) {
        resultado.add(com);
      }
    }
    return resultado;
  }

  public List<Comunidad> listar() {
    List<Comunidad> lista=entityManager().createQuery(
            "SELECT c FROM Comunidad c ", Comunidad.class)
        .getResultList();
    for (Comunidad comunidad : comunidades) {
      // Acceder a la colección Lazy dentro de la transacción
      Hibernate.initialize(comunidad.getMiembros());
      Hibernate.initialize(comunidad.getIncidentes());
      Hibernate.initialize(comunidad.getServiciosInteres());

    }

    return lista;
  }


  public Comunidad buscar(int id) {
    Comunidad c= entityManager().find(Comunidad.class, id);
    Hibernate.initialize(c.getMiembros());
    Hibernate.initialize(c.getServiciosInteres());
    Hibernate.initialize(c.getIncidentes());
    return c;
     /*
         entityManager().createQuery(
            "SELECT c FROM Comunidad c " +
                "LEFT JOIN FETCH c.miembros " +
                "LEFT JOIN FETCH c.serviciosInteres " +
                "WHERE c.id = :id", Comunidad.class)
        .setParameter("id", id)
        .getSingleResult();*/
  }

  public void eliminarComunidad(int id) {
    Comunidad encontrado = this.buscar(id);
    entityManager().remove(encontrado);

  }

}


