package ar.edu.utn.frba.dds.serviciovalidacioncontrasenia;

import ar.edu.utn.frba.dds.Usuario;
import java.util.List;

public class ContraseniaFuerte implements Validador {

  @Override
  public boolean validar(Usuario usuario) {
    List<String> peoresContrasenias;
    peoresContrasenias = LeerArchivoContrasenia.listaPeoresContrasenias();
    return !(peoresContrasenias.contains(usuario.getContrasenia()));
  }
}
