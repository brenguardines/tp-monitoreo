package ar.edu.utn.frba.dds.localizacion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Provincia implements Localizacion {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;
  String nombre;

  public Provincia(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Provincia() {

  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public String toString() {
    return "Provincia{" + "id=" + id + ", nombre='" + nombre + '\'' + '}';
  }
}
