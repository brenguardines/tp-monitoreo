package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.servicio.Servicio;

import static java.time.temporal.ChronoUnit.SECONDS;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import javax.persistence.Transient;

public class IncidenteVista {
  int id;
  private Servicio servicio;

  private String observacion;

  private Establecimiento establecimiento;

  private String apertura = "";

  private String cierre = "";

  private Miembro miembroApertura;

  private Boolean vigente = true;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Servicio getServicio() {
    return servicio;
  }

  public void setServicio(Servicio servicio) {
    this.servicio = servicio;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public Establecimiento getEstablecimiento() {
    return establecimiento;
  }

  public void setEstablecimiento(Establecimiento establecimiento) {
    this.establecimiento = establecimiento;
  }

  public String getApertura() {
    return apertura;
  }

  public void setApertura(String apertura) {
    this.apertura = apertura;
  }

  public String getCierre() {
    return cierre;
  }

  public void setCierre(String cierre) {
    this.cierre = cierre;
  }

  public Miembro getMiembroApertura() {
    return miembroApertura;
  }

  public void setMiembroApertura(Miembro miembroApertura) {
    this.miembroApertura = miembroApertura;
  }

  public Boolean getVigente() {
    return vigente;
  }

  public void setVigente(Boolean vigente) {
    this.vigente = vigente;
  }
}
