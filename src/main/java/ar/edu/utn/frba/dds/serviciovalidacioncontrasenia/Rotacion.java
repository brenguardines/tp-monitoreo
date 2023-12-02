package ar.edu.utn.frba.dds.serviciovalidacioncontrasenia;

import ar.edu.utn.frba.dds.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Rotacion implements Validador {
  @Override
  public boolean validar(Usuario usuario) {
    LocalDateTime tresMesesDespues = usuario.getFechaCreacion().plusMonths(3);
    return LocalDateTime.now().isBefore(tresMesesDespues);
  }
}
