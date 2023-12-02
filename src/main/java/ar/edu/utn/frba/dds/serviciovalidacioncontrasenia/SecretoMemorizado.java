package ar.edu.utn.frba.dds.serviciovalidacioncontrasenia;

import ar.edu.utn.frba.dds.Usuario;

public class SecretoMemorizado implements Validador {

  @Override
  public boolean validar(Usuario usuario) {

    return usuario.getContrasenia().length() >= 8;
  }
}
