package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Comunidad;
import ar.edu.utn.frba.dds.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;

public class RepositorioIncidentes implements WithSimplePersistenceUnit {

  public static RepositorioIncidentes instancia = new RepositorioIncidentes();

  List<Incidente> incidentes;

  public RepositorioIncidentes() {
    this.incidentes = new ArrayList<>();
  }

  public List<Incidente> getIncidentes() {
    return incidentes;
  }

  public void agregarIncidente(Incidente incidente, RepositorioComunidades repoComunidades) {
    this.incidentes.add(incidente);
    List<Comunidad> comunidadesInteresadas = repoComunidades.buscarInteresComunidades(incidente.getServicio());
    System.out.println("Se notificara a todos los miembros de las comunidades interesadas");
    comunidadesInteresadas.forEach(c -> c.agregarIncidente(incidente));
  }

  public List<Incidente> listar() {

    return entityManager().createQuery("from Incidente ", Incidente.class).getResultList();
  }


  public Incidente buscar(int id) {
    return entityManager().find(Incidente.class, id);
  }

  public List<Incidente> buscarPorEstado(String estado) {

    if (estado.equals("Cerrados")) {
      return entityManager()
          .createQuery("from Incidente  c where c.vigente = false", Incidente.class)
          .getResultList();
    } else if (estado.equals("Activos")) {
      return entityManager()
          .createQuery("from Incidente  c where c.vigente = true", Incidente.class)
          .getResultList();
    }
    return entityManager()
        .createQuery("from Incidente  ", Incidente.class)
        .getResultList();
  }

  public void cerrarIncidente(int id) {
    Incidente encontrado = this.buscar(id);
    encontrado.cerrarIncidente();
    withTransaction(() -> {
      this.agregar(encontrado);
    });

  }

  public List<Incidente> buscarPorServicio(String servicio) {
    return entityManager() //
        .createQuery("from Incidente c where c.servicio.nombre like :nombre", Incidente.class)
        .setParameter("nombre", "%" + servicio + "%")
        .getResultList();

  }

  public void agregar(Incidente incidente) {
    entityManager().persist(incidente);
  }
}
