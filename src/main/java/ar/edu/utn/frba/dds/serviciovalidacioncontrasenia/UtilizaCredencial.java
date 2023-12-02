package ar.edu.utn.frba.dds.serviciovalidacioncontrasenia;

import ar.edu.utn.frba.dds.Usuario;

public class UtilizaCredencial implements Validador {
  @Override
  public boolean validar(Usuario usuario) {

    return !(usuario.getContrasenia().contains(usuario.getUsuario()));
  }
}
