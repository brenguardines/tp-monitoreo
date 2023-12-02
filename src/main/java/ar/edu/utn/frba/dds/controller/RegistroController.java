package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.apimail.ApiMail;
import ar.edu.utn.frba.dds.apimail.ServicioNotificacionMail;
import ar.edu.utn.frba.dds.repositorio.RepositorioMiembros;
import ar.edu.utn.frba.dds.repositorio.RepositorioUsuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import java.time.LocalDateTime;

public class RegistroController implements WithSimplePersistenceUnit {
  public ModelAndView registro(Request request, Response response) {
    return new ModelAndView(null, "registro.html.hbs");
  }

  public ModelAndView crearUsuario(Request request, Response response) {
    //TODO: ver los datos que te deberán llegar ahora y hacer que cuando creas un usuario se le asocie un miembro
    String email = request.queryParams("correo");
    String contrasenia = request.queryParams("contrasenia");
    String repiteContrasenia = request.queryParams("repetirContrasenia");
    String categoria=request.queryParams("repetirContrasenia");
    String nombre=request.queryParams("nombre");
    String apellido=request.queryParams("apellido");
    String celular=request.queryParams("celular");
    String codAdmin=request.queryParams("codAdmin");

    System.out.println(contrasenia+"  "+codAdmin+" "+repiteContrasenia);
    if(contrasenia.equals(repiteContrasenia)&&codAdmin.equals("12345")){
      Usuario u=new Usuario(contrasenia, LocalDateTime.now(),email,5);
      Miembro m=new Miembro(nombre,apellido,email,Long.parseLong(celular),new ServicioNotificacionMail(new ApiMail()));
      m.verificarCategoria(categoria);
      m.setCodAdmin(Long.parseLong(codAdmin));
      withTransaction(() -> {
        RepositorioMiembros.instancia.agregar(m);
        u.setMiembro(RepositorioMiembros.instancia.buscarPorMail(email));
        RepositorioUsuario.instancia.agregar(u);
      });

      Usuario o= RepositorioUsuario.instancia.buscarPorMail(email);
      System.out.println("=========================================");
      System.out.println(o.getMiembro().getId()+" "+o.getMiembro().getCodAdmin()+" "+o.getMiembro().isEsAdministrador());
      System.out.println("=========================================");

      Session session = request.session(true);
      session.attribute("usuarioID", o.getId());

      response.redirect("/incidentes?estado=Activos");

    }
    return null;
  }

}
