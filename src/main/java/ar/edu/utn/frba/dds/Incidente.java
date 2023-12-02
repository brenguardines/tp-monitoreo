package ar.edu.utn.frba.dds;

import static java.time.temporal.ChronoUnit.SECONDS;
import ar.edu.utn.frba.dds.repositorio.RepositorioIncidentes;
import ar.edu.utn.frba.dds.servicio.Servicio;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Incidente {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;
  @ManyToOne(fetch = FetchType.EAGER)
  private Servicio servicio;

  private String observacion;

  @ManyToOne(fetch = FetchType.EAGER)
  private Establecimiento establecimiento;

  private LocalDateTime apertura = LocalDateTime.now();

  private LocalDateTime cierre = null;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "miembroApertura_id")
  private Miembro miembroApertura;

  private Boolean vigente = true;

  @Transient
  private RepositorioIncidentes repoIncidentes = new RepositorioIncidentes();

  public Incidente(Servicio servicio, String observacion, Establecimiento establecimiento,
                   Miembro miembroApertura) {
    this.servicio = servicio;
    this.observacion = observacion;
    this.establecimiento = establecimiento;
    this.miembroApertura = miembroApertura;
    this.repoIncidentes.getIncidentes().add(this);
  }

  public Incidente() {

  }

  public Boolean getVigente() {
    return vigente;
  }

  public Integer getId() {
    return id;
  }

  public Servicio getServicio() {
    return servicio;
  }

  public Miembro getMiembroApertura() {
    return miembroApertura;
  }

  public String getObservacion() {
    return observacion;
  }

  public Establecimiento getEstablecimiento() {
    return establecimiento;
  }

  public LocalDateTime getApertura() {
    return apertura;
  }

  public LocalDateTime getCierre() {
    return cierre;
  }

  public void setId(int i) {
    this.id = i;
  }

  public boolean estaVigente() {
    return this.cierre != null;
  }

  public void cerrarIncidente() {
    this.cierre = LocalDateTime.now();
    this.vigente = false;
  }

  public void setCierre(LocalDateTime cierre) {
    this.cierre = cierre;
    vigente = false;
  }

  public void setApertura(LocalDateTime apertura) {
    this.apertura = apertura;
  }

  public long tiempoDeCierre() {
    return SECONDS.between(this.apertura, this.cierre);
  }
}
