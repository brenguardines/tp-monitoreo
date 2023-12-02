package ar.edu.utn.frba.dds.notificacion;

import ar.edu.utn.frba.dds.Incidente;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Notificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  Incidente incidente;

  Boolean entregado;

  public Notificacion(Incidente incidente, Boolean entregado) {
    this.incidente = incidente;
    this.entregado = entregado;
  }

  public Notificacion() {

  }

  public Boolean getEntregado() {
    return entregado;
  }

  public Incidente getIncidente() {
    return incidente;
  }

  public void realizarEntrega() {
    this.entregado = true;
  }

  public int getId() {
    return id;
  }
}
