package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.apimail.ApiMail;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.repositorio.RepositorioMiembros;
import ar.edu.utn.frba.dds.repositorio.RepositorioUsuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class MiembroController implements WithSimplePersistenceUnit {

  public ModelAndView configurarMiembro(Request request, Response response) {
    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if (usuarioID == null) {
      response.redirect("/");
    }
    Usuario u = RepositorioUsuario.instancia.getById(Integer.parseInt(usuarioID));
    Map<String, Object> apertura = new HashMap<>();


    apertura.put("nombre", u.getMiembro().getNombre());
    apertura.put("email", u.getMiembro().getEmail());
    apertura.put("celular", u.getMiembro().getCelular());
    apertura.put("apellido", u.getMiembro().getApellido());
    apertura.put("contrasenia", u.getContrasenia());


    return new ModelAndView(apertura, "configurar_miembro.html.hbs");
  }

  public ModelAndView terminarRegistro(Request request, Response response) {
    String email = request.queryParams("correo");
    String celular = request.queryParams("celular");
    String nombre = request.queryParams("nombre");
    String apellido = request.queryParams("apellido");
    String contrasenia = request.queryParams("contrasenia");
    String contraseniaR = request.queryParams("repetirContrasenia");

    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if (!contrasenia.equals(contraseniaR)) {
      response.redirect("/miembros");
    }

    Usuario o = RepositorioUsuario.instancia.getById(Integer.parseInt(usuarioID));
    System.out.println(nombre + " " + apellido + " " + email + " " + celular);

    o.setContrasenia(contrasenia);
    o.setUsuario(email);
    o.getMiembro().setNombre(nombre);
    o.getMiembro().setEmail(email);
    o.getMiembro().setApellido(apellido);
    o.getMiembro().setCelular(Long.parseLong(celular));
    withTransaction(() -> {
      RepositorioUsuario.instancia.agregar(o);

    });

    response.redirect("/miembros");
    return null;

  }
}
