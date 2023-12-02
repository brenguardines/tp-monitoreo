package ar.edu.utn.frba.dds.serviciovalidacioncontrasenia;

import ar.edu.utn.frba.dds.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ValidadorContrasenia {

  private int cantidadMaxIntentos = 5;
  private List<Validador> validadores = new ArrayList<>();



  public boolean esContraseniaSegura(Usuario usuario) {
    return validadores.stream().allMatch(validador -> validador.validar(usuario));
  }

  public boolean superoIntentos(Usuario contrasenia) {
    return contrasenia.getCantidadIntentos() >= cantidadMaxIntentos;
  }

  public void setValidaciones(Validador validacion) {
    validadores.add(validacion);
  }

  public List<Validador> getValidadores() {
    return validadores;
  }
}
