package ar.edu.utn.frba.dds.parser;

public class OrganismoControl {
  String nombre;
  TipoRegulador tipo;
  String nombreEmpresa;

  public OrganismoControl(String nombre, TipoRegulador tipo, String nombreEmpresa) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.nombreEmpresa = nombreEmpresa;
  }

  public void setting(String nombre, TipoRegulador tipo, String nombreEmpresa) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.nombreEmpresa = nombreEmpresa;
  }


  public void mostrar() {
    System.out.println("regulador{"
        +
        "nombre='" + nombre + '\''
        +
        ", tipo=" + tipo
        +
        ", nombreEmpresa='"
        + nombreEmpresa + '\''
        +
        '}');
  }

  public String getNombre() {
    return nombre;
  }
}
