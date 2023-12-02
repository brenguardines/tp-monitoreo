package ar.edu.utn.frba.dds.apimail;

import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.notificacion.NotificadorElegido;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

public class ServicioNotificacionWpp implements NotificadorElegido {

  private ApiWpp apiWpp = new ApiWpp();

  public ServicioNotificacionWpp() {
  }

  public String convertirNotificacionEnMensaje(Notificacion notificacion) {
    String mensaje = "Ocurrio un incidente en el establecimiento "
        + notificacion.getIncidente().getEstablecimiento().getNombre()
        + " del servicio " + notificacion.getIncidente().getServicio().getNombre();
    return mensaje;
  }

  @Override
  public void enviar(Notificacion notificacion, Miembro miembro) {
    String mensaje = convertirNotificacionEnMensaje(notificacion);
    apiWpp.enviar(mensaje, miembro.getCelular());
  }
}
