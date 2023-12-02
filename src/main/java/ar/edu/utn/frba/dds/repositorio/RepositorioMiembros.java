package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Incidente;
import ar.edu.utn.frba.dds.Miembro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMiembros implements WithSimplePersistenceUnit {
  public static RepositorioMiembros instancia = new RepositorioMiembros();
  List<Miembro> listaMiembros;

  public RepositorioMiembros() {
    this.listaMiembros = new ArrayList<>();

  }

  public List<Miembro> getListaMiembros() {
    return listaMiembros;
  }

  public void notificarMiembrosDeInteres(Incidente i) {

    for (Miembro m : listaMiembros) {
      List<Miembro> conInteres = listaMiembros.stream().filter(mi -> mi.servicioEsDeInteres(i.getServicio())).toList();
      conInteres.forEach(mi -> mi.guardarNotificacion(i));
    }
  }


  public List<Miembro> listar() {

    return entityManager().createQuery("from Miembro", Miembro.class).getResultList();
  }

  public List<Miembro> listarSegunEmpleados() {
    return entityManager().createQuery("from Miembro order by nombre desc", Miembro.class).getResultList();
  }

  public Miembro buscar(int id) {
    return entityManager().find(Miembro.class, id);
  }

  public Miembro buscarPorMail(String correo) {
    Miembro a = entityManager() //
        .createQuery("from Miembro c where c.email like :email", Miembro.class)
        .setParameter("email", "%" + correo + "%")
        .getResultList().get(0);
    System.out.println(a.toString());
    return a;
  }

  public void agregar(Miembro consultora) {
    entityManager().persist(consultora);
  }

  public List<Miembro> buscarPorNombre(String nombre) {
    return entityManager() //
        .createQuery("from Miembro c where c.nombre like :nombre", Miembro.class) //
        .setParameter("nombre", "%" + nombre + "%") //
        .getResultList();
  }
}
