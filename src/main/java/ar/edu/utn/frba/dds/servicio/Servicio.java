package ar.edu.utn.frba.dds.servicio;

import ar.edu.utn.frba.dds.Establecimiento;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Servicio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String nombre;
  private Boolean funciona;
  private String descripcion;
  // variable mas que sea agrupacaion (y pueda ser mujeres u hombres para los baños
  // y el tramo(que podria ser un enum) para los medios de elevacion)
  @Enumerated
  private TipoServicio tipo;

  @ManyToOne
  private Establecimiento establecimiento;

  //Constructores de prueba para los test
  public Servicio(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Servicio(String nombre, Boolean funciona, String descripcion, TipoServicio tipo) {
    this.nombre = nombre;
    this.funciona = funciona;
    this.descripcion = descripcion;
    this.tipo = tipo;
  }

  public Servicio() {

  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
