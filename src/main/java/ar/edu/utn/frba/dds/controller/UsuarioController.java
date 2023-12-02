package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.repositorio.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import java.util.HashMap;
import java.util.Map;

public class UsuarioController {
  public ModelAndView inicioSesion(Request request, Response response) {
    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if(usuarioID!=null){
      response.redirect("/incidentes");
    }
    return new ModelAndView(null, "login.html.hbs");
  }

  public ModelAndView startLogin(Request request, Response response) {
    String username = request.queryParams("correo");
    String password = request.queryParams("contrasenia");

    Usuario o = RepositorioUsuario.instancia.buscarPorMail(username);

    Session session = request.session(true);

    session.attribute("usuarioID", Integer.toString(o.getId()));

    if (o.getContrasenia().compareTo(password) == 0) {

      response.redirect("/incidentes?estado=Activos");
    } else {

      System.out.println("Credenciales incorrectas. Por favor, intenta de nuevo.");
    }
    return new ModelAndView(null, "login.html.hbs");

  }
  public ModelAndView cerrarSesion(Request request, Response response){
    Session session = request.session();
    if (session.attributes().contains("usuarioID")) {
      session.removeAttribute("usuarioID");
    }
    response.redirect("/");
    return null;
  }
}
