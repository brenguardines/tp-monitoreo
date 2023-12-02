package ar.edu.utn.frba.dds.localizacion.ServicioPosicionamiento;

import ar.edu.utn.frba.dds.Posicion;
import java.util.Random;

public class ServicioDePosicionamiento implements PosicionService {
  public ServicioDePosicionamiento() {
  }

  @Override
  public Posicion obtenerPosicion() {
    Random rand = new Random();
    int x = rand.nextInt(101) + 100;
    int y = rand.nextInt(101) + 100;

    Posicion posicion = new Posicion(x, y);

    return posicion;
  }
}
