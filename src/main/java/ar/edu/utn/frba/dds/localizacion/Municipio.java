package ar.edu.utn.frba.dds.localizacion;

import javax.persistence.*;

@Entity
public class Municipio implements Localizacion {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;
  String nombre;
  @OneToOne
  Provincia provincia;

  public Municipio(int id, String nombre, Provincia provincia) {
    this.id = id;
    this.nombre = nombre;
    this.provincia = provincia;
  }

  public Municipio() {

  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public String toString() {
    return "Municipio{" + "id=" + id + ", nombre='"
        + nombre + '\'' + ", provincia=" + provincia + '}';
  }
}
