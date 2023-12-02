package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioServicio implements WithSimplePersistenceUnit {

  public static RepositorioServicio instancia = new RepositorioServicio();
  public List<Servicio> listar() {

    return entityManager().createQuery("from Servicio", Servicio.class).getResultList();
  }
  public List<Servicio> listarSegunEstablecimiento(int idEstablecimiento) {
    return entityManager().createQuery("from Servicio c where c.establecimiento.id=:id", Servicio.class)
        .setParameter("id", idEstablecimiento)
        .getResultList();
  }
  public Servicio buscar(int id) {
    return entityManager().find(Servicio.class, id);
  }
  public void agregar( Servicio servicio) {
    entityManager().persist(servicio);
  }
}

