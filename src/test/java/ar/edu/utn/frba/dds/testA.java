package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.apimail.ApiMail;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.repositorio.*;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.Test;

import java.time.LocalDateTime;

public class testA implements SimplePersistenceTest {

  //miembro y notificaciones
  Miembro miembro1=new Miembro( "Matias", "Paredes", "MT@gmail.com", 123456789,new ServicioNotificacionMail(new ApiMail()));
  Miembro miembro2 = new Miembro("juan", "gonzalez", "juang@gmail.com", 1144556677,new ServicioNotificacionMail(new ApiMail()));

  Incidente i1= RepositorioIncidentes.instancia.buscar(2);
  Incidente i2= RepositorioIncidentes.instancia.buscar(5);
  Incidente i3= RepositorioIncidentes.instancia.buscar(8);
  Notificacion n1=new Notificacion(i1,true);
  Notificacion n2=new Notificacion(i2,true);
  Notificacion n3=new Notificacion(i3,true);

  // 2 5 8
  @Test
  public void aTestThatRequiresAPersistenceContext() {

    Miembro miembro1=new Miembro( "Matias", "Paredes", "MT@gmail.com", 123456789,new ServicioNotificacionMail(new ApiMail()));
    Miembro miembro2 = new Miembro("juan", "gonzalez", "juang@gmail.com", 1144556677,new ServicioNotificacionMail(new ApiMail()));

    /*
    Entidad enti=new Entidad("Waalmart","Shopping");

    withTransaction(() -> {
      RepositorioEntidades.instancia.agregar(enti);
    });

    Entidad buscada=RepositorioEntidades.instancia.buscar(1);
    Establecimiento e=new Establecimiento("Sanjusto","Mediano",new Posicion(100,100),buscada);

    withTransaction(() -> {
      RepositorioEstablecimiento.instancia.agregar(e);
    });

    Servicio s1=new Servicio("Baño",false,"En el segundo piso", TipoServicio.BANIO);
    Servicio s2=new Servicio("Ascensor",false,"En el segundo piso", TipoServicio.ASCENSOR);
    Servicio s3=new Servicio("Escalera mecanica",false,"En el segundo piso", TipoServicio.ESCALERA_MECANICA);
    withTransaction(() -> {
      RepositorioServicio.instancia.agregar(s1);
      RepositorioServicio.instancia.agregar(s2);
      RepositorioServicio.instancia.agregar(s3);
    });

    Establecimiento encontrado=RepositorioEstablecimiento.instancia.buscar(1);

    Incidente i1=new Incidente(RepositorioServicio.instancia.buscar(1),"Grifo roto",encontrado,null );
    Incidente i2=new Incidente(RepositorioServicio.instancia.buscar(2),"No funciona",encontrado,null);
    Incidente i3=new Incidente(RepositorioServicio.instancia.buscar(3),"Parada",encontrado,null);

    withTransaction(() -> {
      RepositorioIncidentes.instancia.agregar(i1);
      RepositorioIncidentes.instancia.agregar(i2);
      RepositorioIncidentes.instancia.agregar(i3);
    });

    Usuario u=new Usuario("12345", LocalDateTime.now(),"tomas@gmail",5);
    withTransaction(() -> {
      RepositorioUsuario.instancia.agregar(u);
    });

=====================================================================
    Entidad buscada=RepositorioEntidades.instancia.buscar(1);
    Establecimiento e1=new Establecimiento("Sanjusto","Mediano",new Posicion(100,100),buscada);
    Establecimiento e2=new Establecimiento("Flores","Mediano",new Posicion(100,100),buscada);
    Establecimiento e3=new Establecimiento("Corrientes","Mediano",new Posicion(100,100),buscada);
    withTransaction(() -> {
      RepositorioEstablecimiento.instancia.agregar(e1);
      RepositorioEstablecimiento.instancia.agregar(e2);
      RepositorioEstablecimiento.instancia.agregar(e3);
    });
===============================================


==========================================================
    Servicio s1=new Servicio("Baño",false,"En el segundo piso", TipoServicio.BANIO);
    Servicio s2=new Servicio("Ascensor",false,"En el segundo piso", TipoServicio.ASCENSOR);
    Servicio s3=new Servicio("Escalera mecanica",false,"En el segundo piso", TipoServicio.ESCALERA_MECANICA);
    withTransaction(() -> {
      RepositorioServicio.instancia.agregar(s1);
      RepositorioServicio.instancia.agregar(s2);
      RepositorioServicio.instancia.agregar(s3);
      RepositorioServicio.instancia.agregar(s1);
      RepositorioServicio.instancia.agregar(s2);
      RepositorioServicio.instancia.agregar(s3);
    });
===============================================================
  Servicio s1=RepositorioServicio.instancia.buscar(4);
    Servicio s2=RepositorioServicio.instancia.buscar(5);
    Servicio s3=RepositorioServicio.instancia.buscar(6);
    Servicio s4=RepositorioServicio.instancia.buscar(7);
    Servicio s5=RepositorioServicio.instancia.buscar(8);

    Establecimiento e1=RepositorioEstablecimiento.instancia.buscar(1);
    Establecimiento e2=RepositorioEstablecimiento.instancia.buscar(2);
    e1.getServicios().add(s1);
    e1.getServicios().add(s2);
    e1.getServicios().add(s3);
    e2.getServicios().add(s4);
    e2.getServicios().add(s5);
    withTransaction(() -> {
      RepositorioEstablecimiento.instancia.agregar(e1);
      RepositorioEstablecimiento.instancia.agregar(e2);
    });


    Establecimiento est=RepositorioEstablecimiento.instancia.buscar(1);
    System.out.println(est.getServicios().size());
    Establecimiento est2=RepositorioEstablecimiento.instancia.buscar(2);
    System.out.println(est2.getServicios().size());

    Miembro m=RepositorioMiembros.instancia.buscar(1);
    Notificacion n1=new Notificacion(RepositorioIncidentes.instancia.buscar(1),true);
    Notificacion n2=new Notificacion(RepositorioIncidentes.instancia.buscar(2),true);
    Notificacion n3=new Notificacion(RepositorioIncidentes.instancia.buscar(3),true);
    withTransaction(() -> {
      RepositorioNotificacion.instancia.agregar(n1);
      RepositorioNotificacion.instancia.agregar(n2);
      RepositorioNotificacion.instancia.agregar(n3);
    });
    m.getNotificaciones().add(RepositorioNotificacion.instancia.buscar(1));
    m.getNotificaciones().add(RepositorioNotificacion.instancia.buscar(2));
    m.getNotificaciones().add(RepositorioNotificacion.instancia.buscar(3));
    withTransaction(() -> {
      RepositorioMiembros.instancia.agregar(m);
    });
*/
  }



  @Test
  public void aTestThatRequiresAPersistenceContextw() {

    Miembro miembro1=new Miembro( "Matias", "Paredes", "MT@gmail.com", 123456789,new ServicioNotificacionMail(new ApiMail()));
    Miembro miembro2 = new Miembro("juan", "gonzalez", "juang@gmail.com", 1144556677,new ServicioNotificacionMail(new ApiMail()));
    Miembro miembro3=new Miembro( "juli", "flores", "juli@gmail.com", 123456789,new ServicioNotificacionMail(new ApiMail()));
    Miembro miembro4 = new Miembro("tomas", "gonzalez", "tomas@gmail.com", 1144556677,new ServicioNotificacionMail(new ApiMail()));
    withTransaction(() -> {
      RepositorioMiembros.instancia.agregar(miembro1);
      RepositorioMiembros.instancia.agregar(miembro2);
      RepositorioMiembros.instancia.agregar(miembro3);
      RepositorioMiembros.instancia.agregar(miembro4);
    });


    /*
    Comunidad c=RepositorioComunidades.instancia.buscar(3);
    Comunidad c1=RepositorioComunidades.instancia.buscar(4);
    System.out.println("===========================================================================");
    System.out.println(c.getMiembros().size()+"  "+c.getServiciosInteres().size()+" "+c.getServiciosInteres().get(0).getId());
    System.out.println(c1.getMiembros().size()+"  "+c1.getServiciosInteres().size()+" "+c1.getServiciosInteres().get(0).getId());
    for(Miembro m:c.getMiembros()){
      System.out.println(m.getNombre()+" "+m.getMail());
    }
    for(Miembro m:c1.getMiembros()){
      System.out.println(m.getNombre()+" "+m.getMail());
    }

    System.out.println("===========================================================================");


    Miembro m=RepositorioMiembros.instancia.buscar(2);
    Miembro m1=RepositorioMiembros.instancia.buscar(3);
    Miembro m2=RepositorioMiembros.instancia.buscar(4);

    Comunidad c=RepositorioComunidades.instancia.buscar(3);
    Comunidad c1=RepositorioComunidades.instancia.buscar(4);

    Servicio s=RepositorioServicio.instancia.buscar(1);
    Servicio s1=RepositorioServicio.instancia.buscar(2);
    Servicio s2=RepositorioServicio.instancia.buscar(3);
    Servicio s3=RepositorioServicio.instancia.buscar(4);

    c.getMiembros().add(m);
    c.getMiembros().add(m1);
    c1.getMiembros().add(m2);

    c.getServiciosInteres().add(s);
    c1.getServiciosInteres().add(s1);
    c1.getServiciosInteres().add(s2);
    c1.getServiciosInteres().add(s3);

    withTransaction(() -> {
      RepositorioComunidades.instancia.agregar(c);
      RepositorioComunidades.instancia.agregar(c1);
    });
        Miembro m=RepositorioMiembros.instancia.buscar(2);
    Miembro m1=RepositorioMiembros.instancia.buscar(3);
    Miembro m2=RepositorioMiembros.instancia.buscar(4);

    Comunidad c=RepositorioComunidades.instancia.buscar(1);
    Comunidad c1=RepositorioComunidades.instancia.buscar(2);

    Servicio s=RepositorioServicio.instancia.buscar(1);
    Servicio s1=RepositorioServicio.instancia.buscar(2);
    Servicio s2=RepositorioServicio.instancia.buscar(3);
    Servicio s3=RepositorioServicio.instancia.buscar(4);

    c.getMiembros().add(m);
    c.getMiembros().add(m1);
    c1.getMiembros().add(m2);

    c.getServiciosInteres().add(s);
    c1.getServiciosInteres().add(s1);
    c1.getServiciosInteres().add(s2);
    c1.getServiciosInteres().add(s3);

    withTransaction(() -> {
      RepositorioComunidades.instancia.agregarComunidad(c);
      RepositorioComunidades.instancia.agregarComunidad(c1);
    });
    Comunidad m=new Comunidad("Escaleras unidos","iteresado en movilidad en shoping");
    Comunidad m1=new Comunidad("escalera mecanica de san justo","iteresado en movilidad en shoping");
    Comunidad m2=new Comunidad("inclusion en san justo","iteresado en movilidad en sanjusto");

    withTransaction(() -> {
      RepositorioComunidades.instancia.agregar(m);
      RepositorioComunidades.instancia.agregar(m1);
      RepositorioComunidades.instancia.agregar(m2);
    });
    Entidad enti=new Entidad("Waalmart","Shopping");


    Entidad buscada=RepositorioEntidades.instancia.buscar(1);
    Establecimiento e=new Establecimiento("Sanjusto","Mediano",new Posicion(100,100),buscada);



    Servicio s1=new Servicio("Baño",false,"En el segundo piso", TipoServicio.BANIO);
    Servicio s2=new Servicio("Ascensor",false,"En el segundo piso", TipoServicio.ASCENSOR);
    Servicio s3=new Servicio("Escalera mecanica",false,"En el segundo piso", TipoServicio.ESCALERA_MECANICA);
    withTransaction(() -> {
      RepositorioServicio.instancia.agregar(s1);
      RepositorioServicio.instancia.agregar(s2);
      RepositorioServicio.instancia.agregar(s3);
    });

    Establecimiento encontrado=RepositorioEstablecimiento.instancia.buscar(1);

    Incidente i1=new Incidente(RepositorioServicio.instancia.buscar(1),"Grifo roto",encontrado,null );
    Incidente i2=new Incidente(RepositorioServicio.instancia.buscar(2),"No funciona",encontrado,null);
    Incidente i3=new Incidente(RepositorioServicio.instancia.buscar(3),"Parada",encontrado,null);

    withTransaction(() -> {
      RepositorioIncidentes.instancia.agregar(i1);
      RepositorioIncidentes.instancia.agregar(i2);
      RepositorioIncidentes.instancia.agregar(i3);
    });
  }*/
  }
}