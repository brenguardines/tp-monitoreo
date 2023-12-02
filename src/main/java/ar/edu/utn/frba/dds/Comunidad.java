package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.repositorio.RepositorioComunidades;
import ar.edu.utn.frba.dds.servicio.Servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Comunidad {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;
  String nombre;
  String descripcion;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "comunidad_miembro",
      joinColumns = @JoinColumn(name = "comunidad_id"),
      inverseJoinColumns = @JoinColumn(name = "miembro_id"))
  private Set<Miembro> miembros=new HashSet<>();;

  @ManyToMany(fetch = FetchType.LAZY)

  private List<Incidente> incidentes=new ArrayList<>();;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "comunidad_servicio",
      joinColumns = @JoinColumn(name = "comunidad_id"),
      inverseJoinColumns = @JoinColumn(name = "servicio_id"))
  private Set<Servicio> serviciosInteres = new HashSet<>();

  @Transient
  private RepositorioComunidades repoComunidades = new RepositorioComunidades();

  public Comunidad(String nombre,String detalle) {
    this.nombre=nombre;
    this.descripcion=detalle;
    //this.repoComunidades.agregarComunidad(this);
  }

  public Comunidad() {

  }

  public Set<Miembro> getMiembros() {
    return miembros;
  }

  public Set<Servicio> getServiciosInteres() {
    return serviciosInteres;
  }

  public List<Incidente> getIncidentes() {
    return incidentes;
  }

  public void agregarIncidente(Incidente incidente) {
    incidentes.add(incidente);
    this.notificarMiembros(incidente);
  }

  public boolean estaInteresado(Servicio servicio) {
    return this.serviciosInteres.stream().anyMatch(s -> s.getId() == servicio.getId());
  }

  public void notificarMiembros(Incidente incidente) {
    miembros.forEach(m -> {
      m.guardarNotificacion(incidente);
      m.enviarNotificacion(incidente);
    });
  }


  public void mostrar() {
    System.out.println("id= " + id + ", nombre= " + nombre  + ", detalle= " + descripcion);
  }

  public boolean perteneceAlaComunidad(Miembro miembro) {
    return miembros.contains(miembro);
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
