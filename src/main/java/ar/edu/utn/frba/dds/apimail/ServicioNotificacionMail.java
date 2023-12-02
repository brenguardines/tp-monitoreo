package ar.edu.utn.frba.dds.apimail;

import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.notificacion.NotificadorElegido;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class ServicioNotificacionMail implements NotificadorElegido {

  @Embedded
  private ApiMail apiMail = new ApiMail();

  public ServicioNotificacionMail(ApiMail apiMail) {
    this.apiMail = apiMail;
  }

  public ServicioNotificacionMail() {

  }


  public String convertirNotificacionEnMensaje(Notificacion notificacion) {
    String mensaje = "Ocurrio un incidente en el establecimiento "
        + notificacion.getIncidente().getEstablecimiento().getNombre() + " del servicio "
        + notificacion.getIncidente().getServicio().getNombre();
    return mensaje;
  }

  @Override
  public void enviar(Notificacion notificacion, Miembro miembro) {
    String mensaje = convertirNotificacionEnMensaje(notificacion);
    String subject = "Notificacion de incidente";
    apiMail.enviar(subject, mensaje, miembro.getEmail());
  }

  public ApiMail getApiMail() {
    return apiMail;
  }

  public void setApiMail(ApiMail apiMail) {
    this.apiMail = apiMail;
  }
}
