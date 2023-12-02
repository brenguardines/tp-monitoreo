package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.ValidadorContrasenia;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
public class Usuario {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  int id;
  private String contrasenia;

  private LocalDateTime fechaCreacion;

  private String usuario;

  private int cantidadIntentos;

  @OneToOne
  @JoinColumn(name = "miembro_id")
  private Miembro miembro;

  public Usuario(String contrasenia, LocalDateTime fechaCreacion,
                 String usuario, int cantidadIntentos) {
    this.contrasenia = contrasenia;
    this.fechaCreacion = fechaCreacion;
    this.usuario = usuario;
    this.cantidadIntentos = cantidadIntentos;
  }

  public Usuario() {

  }

  public int getId() {
    return id;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public LocalDateTime getFechaCreacion() {
    return fechaCreacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public int getCantidadIntentos() {
    return cantidadIntentos;
  }

  public void setMiembro(Miembro miembro) {
    this.miembro = miembro;
  }

  public Miembro getMiembro() {
    return miembro;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  @Override
  public String toString() {
    return "Usuario{" +
        "id=" + id +
        ", contrasenia='" + contrasenia + '\'' +
        ", fechaCreacion=" + fechaCreacion +
        ", usuario='" + usuario + '\'' +
        ", cantidadIntentos=" + cantidadIntentos +
        '}';
  }

}
