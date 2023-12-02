package ar.edu.utn.frba.dds;

import javax.persistence.Embeddable;

@Embeddable
public class Posicion {
  private double latitud;
  private double longitud;

  public Posicion(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Posicion() {
  }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  public void setLatitud(double latitud) {
    this.latitud = latitud;
  }

  public void setLongitud(double longitud) {
    this.longitud = longitud;
  }
}
