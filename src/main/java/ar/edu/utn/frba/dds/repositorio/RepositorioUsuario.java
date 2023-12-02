package ar.edu.utn.frba.dds.repositorio;

import ar.edu.utn.frba.dds.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class RepositorioUsuario implements WithSimplePersistenceUnit {

  public static RepositorioUsuario instancia = new RepositorioUsuario();

  public void agregar(Usuario nuevo) {
    entityManager().persist(nuevo);
  }

  public Usuario buscarPorMail(String correo) {
    List<Usuario> lis = entityManager() //
        .createQuery("from Usuario c where c.usuario like :email", Usuario.class)
        .setParameter("email", "%" + correo + "%")
        .getResultList();
    System.out.println("Elementos " + lis.size());
    if (lis.isEmpty()) {
      System.out.println("No hay elementos");
    } else {
      return lis.get(0);
    }
    return null;
  }

  public boolean verificarUsuario(String email, String contrasenia) {
    Usuario encontrado = buscarPorMail(email);

    if (encontrado != null && encontrado.getContrasenia() == contrasenia) {
      return true;
    }

    return false;
  }

  public Usuario getById(int id) {
    return entityManager().find(Usuario.class, id);
  }
}
