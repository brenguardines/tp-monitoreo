package ar.edu.utn.frba.dds.apiwpp;

import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.notificacion.NotificadorElegido;

public class ServicioNotificacionWpp implements NotificadorElegido {
  private ApiWpp apiWpp = new ApiWpp();

  @Override
  public void enviar(Notificacion notificacion, Miembro miembro) {

    apiWpp.enviar(notificacion, miembro.getCelular());
  }
}
