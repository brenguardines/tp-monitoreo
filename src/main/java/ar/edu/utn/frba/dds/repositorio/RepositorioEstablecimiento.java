package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Establecimiento;
import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioEstablecimiento implements WithSimplePersistenceUnit {

  public static RepositorioEstablecimiento instancia = new RepositorioEstablecimiento();

  public List<Establecimiento> listar() {

    return entityManager().createQuery("from Establecimiento", Establecimiento.class).getResultList();
  }

  public Establecimiento buscar(int id) {
    return entityManager().find(Establecimiento.class, id);
  }

  public void agregar(Establecimiento establecimiento) {
    entityManager().persist(establecimiento);
  }
}
