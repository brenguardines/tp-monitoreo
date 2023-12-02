package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.apimail.ApiMail;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.servicio.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiMailTest {
  Entidad e1;
  Entidad e2;
  Entidad e3;
  Notificacion notificacion;
  Miembro miembro1;
  Incidente i1;
  String miembroMail = "";//Mail al que se va a enviar la notificacion

  @BeforeEach
  void setup() {
    ServicioNotificacionMail servicioNotificacionMail = new ServicioNotificacionMail( new ApiMail());
    e1=new Entidad("walart","Shopping");
    e1.setId(1);
    e2=new Entidad("jumbo","Shopping");
    e2.setId(2);
    e3=new Entidad("changoMas","Shopping");
    e3.setId(3);
    miembro1 = new Miembro("Juan","Perez",miembroMail,servicioNotificacionMail);
    i1=new Incidente(new Servicio(1,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e1),miembro1);

    Incidente i2=new Incidente(new Servicio(2,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e2),miembro1);

    Incidente i3=new Incidente(new Servicio(3,"Banio"),"bano roto",
        new Establecimiento("La matanza","Shopping",new Posicion(100,100),e3),miembro1);

    i1.setId(1);
    i2.setId(2);
    i3.setId(3);


  }

  @Test
  void enviarMail(){
    miembro1.enviarNotificacion(i1);
  }
}
