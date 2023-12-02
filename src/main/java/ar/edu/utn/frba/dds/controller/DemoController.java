package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.repositorio.RepositorioMiembros;
import ar.edu.utn.frba.dds.repositorio.RepositorioUsuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DemoController implements WithSimplePersistenceUnit {

  public ModelAndView listar(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    return new ModelAndView(modelo, "index.html.hbs");
  }


  private Usuario getUsuarioLogueado(Request request) {
    int idUsuario = request.session().attribute("idUsuario");

    Usuario usuario = RepositorioUsuario.instancia.getById(idUsuario);


    return usuario;
  }

  public ModelAndView logeado(Request request, Response response) {
    return new ModelAndView(null, "menu.html.hbs");
  }
}
