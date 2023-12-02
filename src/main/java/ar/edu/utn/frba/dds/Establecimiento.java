package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.servicio.Servicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
//son las estaciones y las sucursales
public class Establecimiento {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;
  private String nombre;
  private String tipo;
  @ManyToOne
  private Entidad entidad;

  @Embedded
  private Posicion ubicacion;

  @OneToMany
  @JoinColumn(name = "establecimiento_id")
  private List<Servicio> servicios = new ArrayList<>();

  public Establecimiento(String nombre) {
    this.nombre = nombre;
  }

  public Establecimiento(String nombre, String tipo, Posicion ubicacion, Entidad entidad) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.ubicacion = ubicacion;
    this.entidad = entidad;
  }

  public Establecimiento() {

  }

  public String getNombre() {
    return nombre;
  }

  public int getId() {
    return id;
  }

  public String getTipo() {
    return tipo;
  }

  public Entidad getEntidad() {
    return entidad;
  }

  public Posicion getUbicacion() {
    return ubicacion;
  }

  public List<Servicio> getServicios() {
    return servicios;
  }
}

