package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.apimail.ApiMail;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.servicio.Servicio;
import ar.edu.utn.frba.dds.servicio.TipoServicio;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class BaseDatosTest extends AbstractPersistenceTest implements WithGlobalEntityManager {


	@Test
	public void testing(){

		Entidad enti=new Entidad("Walmart","Shoping");
		Posicion posicion=new Posicion(1,2);
		Miembro miembro1=new Miembro("Matias", "Paredes", "MT@gmail.com", 12345, new ServicioNotificacionMail(new ApiMail()));
		Establecimiento establecimiento = new Establecimiento("ypf", "tipo",posicion,enti);
		Servicio servicio1 = new Servicio( "Servicio1",true,"Escalera", TipoServicio.ESCALERA_MECANICA);
		Incidente incidente = new Incidente(servicio1, "Escalera no funciona", establecimiento, miembro1);
		Comunidad comunidad = new Comunidad();
		Notificacion noti=new Notificacion(incidente,false);

		establecimiento.getServicios().add(servicio1);

		miembro1.guardarNotificacion(incidente);
		miembro1.getServiciosDeInteres().add(servicio1);
		miembro1.getHorariosNotificacion().add(5);
		miembro1.getHorariosNotificacion().add(9);
		comunidad.getMiembros().add(miembro1);

		//enti.getIncidentes().add(incidente);
		enti.getEstablecimientos().add(establecimiento);


		entityManager().persist(comunidad);
		entityManager().persist(establecimiento);
		entityManager().persist(servicio1);
		entityManager().persist(miembro1);
		entityManager().persist(incidente);
		entityManager().persist(noti);
		entityManager().persist(enti);

		Miembro persona = entityManager().find(Miembro.class, 1);
		Entidad entidad = entityManager().find(Entidad.class, 1);
		Comunidad comu = entityManager().find(Comunidad.class, 1);

		if (persona != null) {
			System.out.println("Persona encontrada: " + persona.getNombre());
			System.out.println("Cantidad de notificaciones "+persona.getNotificaciones().size());
			System.out.println("Cantidad de horarios "+persona.getHorariosNotificacion().size());
			System.out.println("Obtengo el incidente de la notificacion: "+persona.getNotificaciones().get(0).getIncidente().getObservacion());
			System.out.println("Obtengo el Servicio del incidente: "+persona.getNotificaciones().get(0).getIncidente().getServicio().getNombre());
			System.out.println("Obtengo el Establecimiento del incidente: "+persona.getNotificaciones().get(0).getIncidente().getEstablecimiento().getNombre());
			System.out.println("Obtengo el miembro quien lo abrio: "+persona.getNotificaciones().get(0).getIncidente().getMiembroApertura().getNombre());


			System.out.println("Obtengo la entidad "+entidad.getNombre()+" que tiene al establecimiento  "+entidad.getEstablecimientos().get(0).getNombre()+
					" y obtengo el servicio de ese establecimiento"+entidad.getEstablecimientos().get(0).getServicios().get(0));

			System.out.println("Obtengo la comunidad "+comu.id+" que tiene al miembro "+comu.getMiembros().stream().toList().get(0).getNombre());

		}else {
			System.out.println("Persona no encontrada.");

		}

	}

}
