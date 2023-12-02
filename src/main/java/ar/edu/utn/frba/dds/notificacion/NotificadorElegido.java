package ar.edu.utn.frba.dds.notificacion;

import ar.edu.utn.frba.dds.Miembro;
import javax.persistence.Embeddable;

@Embeddable
public interface NotificadorElegido {
  public void enviar(Notificacion notificacion, Miembro miembro);
}
