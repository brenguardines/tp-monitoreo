package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.ContraseniaFuerte;
import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.Rotacion;
import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.SecretoMemorizado;
import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.UtilizaCredencial;
import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.Validador;
import ar.edu.utn.frba.dds.serviciovalidacioncontrasenia.ValidadorContrasenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidadorContraseniaTest {

    private Validador utilizaCredencial;
    private Validador cumpleSecretoMemorizado;
    private Validador rotacion;
    private Validador contraseniaFuerte;
    private ValidadorContrasenia validador;

  @BeforeEach
  public void initialize() {
    utilizaCredencial = new UtilizaCredencial();
    cumpleSecretoMemorizado = new SecretoMemorizado();
    rotacion = new Rotacion();
    contraseniaFuerte = new ContraseniaFuerte();
    validador = new ValidadorContrasenia();
    validador.setValidaciones(utilizaCredencial);
    validador.setValidaciones(cumpleSecretoMemorizado);
    validador.setValidaciones(rotacion);
    validador.setValidaciones(contraseniaFuerte);
  }


  private Usuario crearCredencial(String contrasenia, int cantidadIntentos) {
    return new Usuario(contrasenia, LocalDateTime.now(), "Marta", cantidadIntentos);
  }

  @Test
  public void contraseniaConUsuarioIncluido() {
    Usuario contraseniaConUsuarioIncluido = crearCredencial("Marta1290", 3);
    assertFalse(utilizaCredencial.validar(contraseniaConUsuarioIncluido));
  }

  @Test
  public void contraseniaConUsuarioNoIncluido() {
    Usuario contraseniaConUsuarioNoIncluido = crearCredencial("estasifunca", 3);
    assertTrue(utilizaCredencial.validar(contraseniaConUsuarioNoIncluido));
  }

  @Test
  public void contraseniaConCantidadDeCaracteresSuperior() {
    Usuario credencialConCantidadMayorCaracteres = crearCredencial("contraValida", 3);
    assertTrue(cumpleSecretoMemorizado.validar(credencialConCantidadMayorCaracteres));
  }

  @Test
  public void contraseniaConCantidadDeCaracteresMenor() {
    Usuario contraseniaConCantidadDeCaracteresMenor = crearCredencial("noda", 3);
    assertFalse(cumpleSecretoMemorizado.validar(contraseniaConCantidadDeCaracteresMenor));
  }

  @Test
  public void contraseniaConCantidadDeIntentosSuperados() {
    Usuario contraseniaConCantidadDeIntentosSuperados = crearCredencial("YaSePaso", 6);
    assertTrue(validador.superoIntentos(contraseniaConCantidadDeIntentosSuperados));
  }

  @Test
  public void contraseniaConCantidadDeIntentosDisponibles() {
    Usuario contraseniaConCantidadDeIntentosDisponibles = crearCredencial("Nada1465", 3);
    assertFalse(validador.superoIntentos(contraseniaConCantidadDeIntentosDisponibles));
  }

  @Test
  public void contraseniaConCantidadDeIntentosDisponiblesYCantidadDeCaracteresMenor() {
    Usuario contraDiponibleYMenor = crearCredencial("NoPasa", 1);
    assertFalse(validador.superoIntentos(contraDiponibleYMenor) && cumpleSecretoMemorizado.validar(contraDiponibleYMenor));
  }

  @Test
  public void esContraseniaSegura() {
    Usuario credencial = crearCredencial("estasifunca", 1);
    assertTrue(validador.esContraseniaSegura(credencial));
  }

  @Test
  public void noEsContraseniaSegura() {
    Usuario credencial = crearCredencial("password", 1);
    assertFalse(validador.esContraseniaSegura(credencial));
  }
  //Entrega

}

