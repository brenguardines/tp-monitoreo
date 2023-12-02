package ar.edu.utn.frba.dds.localizacion;

import javax.persistence.*;

@Entity
public class Departamento implements Localizacion {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;

  String nombre;
  @OneToOne
  Provincia provincia;

  public Departamento(int id, String nombre, Provincia provincia) {
    this.id = id;
    this.nombre = nombre;
    this.provincia = provincia;
  }

  public Departamento() {

  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public String toString() {
    return "Departamento{" + "id=" + id + ", nombre='"
        + nombre + '\'' + ", provincia=" + provincia + '}';
  }
}
