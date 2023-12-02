package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioNotificacion implements WithSimplePersistenceUnit {

  public static RepositorioNotificacion instancia = new RepositorioNotificacion();
  public void agregar( Notificacion notificacion) {
    entityManager().persist(notificacion);
  }

  public Notificacion buscar(int id) {
    return entityManager().find(Notificacion.class, id);
  }
}
