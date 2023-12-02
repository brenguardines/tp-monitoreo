package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.apimail.ApiMail;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.localizacion.ServicioPosicionamiento.ServicioDePosicionamiento;
import ar.edu.utn.frba.dds.notificacion.Notificacion;
import ar.edu.utn.frba.dds.repositorio.RepositorioMiembros;
import ar.edu.utn.frba.dds.servicio.Servicio;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
public class Miembro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;
  private String nombre;
  private String apellido;
  private String email;
  private long celular;

  @Column(name = "administrador")
  private boolean esAdministrador;
  private long codAdmin;
  @Embedded
  private ServicioNotificacionMail modoEnvio;
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Notificacion> notificaciones = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Servicio> serviciosDeInteres = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "horarios")
  @Column(name = "horario")
  private List<Integer> horariosNotificacion = new ArrayList<>();

  @Transient
  private RepositorioMiembros repoMiembros = new RepositorioMiembros();

  public Miembro(String nombre, String apellido, String email,
                 long celular, ServicioNotificacionMail modoEnvio) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.celular = celular;
    this.modoEnvio = modoEnvio;
    this.esAdministrador = false;
    this.repoMiembros.getListaMiembros().add(this);

  }

  public Miembro(String nombre, String apellido, String email,
                 ServicioNotificacionMail modoEnvio) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;

    this.modoEnvio = modoEnvio;
    this.esAdministrador = false;
    this.repoMiembros.getListaMiembros().add(this);

  }

  public Miembro(String nombre) {
    this.nombre = nombre;
    this.modoEnvio = new ServicioNotificacionMail(new ApiMail());
  }

  public Miembro() {

  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public long getCelular() {
    return celular;
  }

  public String getEmail() {
    return email;
  }

  public String getApellido() {
    return apellido;
  }

  public boolean isEsAdministrador() {
    return esAdministrador;
  }

  public long getCodAdmin() {
    return codAdmin;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public void setCelular(long celular) {
    this.celular = celular;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCodAdmin(long codAdmin) {
    this.codAdmin = codAdmin;
  }


  public ServicioNotificacionMail getModoEnvio() {
    return modoEnvio;
  }

  public void setModoEnvio(ServicioNotificacionMail modoEnvio) {
    this.modoEnvio = modoEnvio;
  }

  public void setEsAdministrador(Boolean bool) {
    this.esAdministrador = bool;
  }

  public List<Notificacion> getNotificaciones() {
    return notificaciones;
  }

  public List<Integer> getHorariosNotificacion() {
    return horariosNotificacion;
  }


  public void addServiciosDeInteres(Servicio s) {
    this.serviciosDeInteres.add(s);
  }

  public List<Servicio> getServiciosDeInteres() {
    return serviciosDeInteres;
  }


  //Son las notificaciones que se envian individualmente en el momento.
  // OK sirve cuando la entidad quiere mandar mensaje directo al miembro interesado
  public void enviarNotificacion(Incidente incidente) {
    Notificacion notificacionaEnviar = new Notificacion(incidente, true);
    //solo para los casos de que la entidad abra un incidente o cuando el miembro este cerca de una
    // ubicacion con un incidente reportado
    notificacionaEnviar.getEntregado();
    modoEnvio.enviar(notificacionaEnviar, this);
  }

  public void guardarNotificacion(Incidente incidente) {
    Notificacion notificacionaGuardar = new Notificacion(incidente, false);
    notificaciones.add(notificacionaGuardar);
  }

  //Son el conjunto de las notificaciones a enviar en el horario que quiera el miembro
  public void enviarNotificaciones() {
    List<Notificacion> notificacionesaEnviar = this.notificaciones.stream().filter(
        notificacion -> !notificacion.getEntregado()).collect(Collectors.toList());

    this.notificaciones.stream().forEach(notificacion -> notificacion.realizarEntrega());

    this.notificaciones.stream().forEach(notificacion -> modoEnvio.enviar(notificacion, this));
  }

  public Posicion obtenerposicion() {
    return new ServicioDePosicionamiento().obtenerPosicion();
  }

  public boolean servicioEsDeInteres(Servicio s) {
    for (Servicio ser : serviciosDeInteres) {
      if (ser.getId() == s.getId()) {
        return true;
      }
    }
    return false;
  }
  public void verificarCategoria(String categoria){
    if(categoria.equals("usuario")){
      esAdministrador=false;
    }else{
      esAdministrador=true;
    }

  }

  @Override
  public String toString() {
    return "Miembro{"
        + "id=" + id
        + ", nombre='" + nombre + '\''
        + ", apellido='" + apellido + '\''
        + ", email='" + email + '\''
        + ", celular=" + celular
        + '}';
  }


  public boolean estaCercaDeEstablecimiento(Posicion miPosicion, Posicion otraPosicion) {

    double distanciaX = miPosicion.getLongitud() - otraPosicion.getLongitud();
    double distanciaY = miPosicion.getLatitud() - otraPosicion.getLatitud();

    return Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY) <= 50;
  }

}
