package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.Comunidad;
import ar.edu.utn.frba.dds.Incidente;
import ar.edu.utn.frba.dds.Miembro;
import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.repositorio.*;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.hibernate.Hibernate;
import spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComunidadController implements WithSimplePersistenceUnit {
  public ModelAndView listarComunidades(Request request, Response response) {

    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if(usuarioID==null){
      response.redirect("/");
    }
    Usuario u=RepositorioUsuario.instancia.getById(Integer.parseInt(usuarioID));

    if(u.getMiembro().isEsAdministrador()){
      List<Comunidad> lista = RepositorioComunidades.instancia.listar();

      System.out.println("===========================================================================");
      for (Comunidad m : lista) {
        System.out.println(m.getNombre() + " " + m.getDescripcion());
      }
      System.out.println("===========================================================================");

      Map<String, Object> listado = new HashMap<>();

      listado.put("comunidades", lista);

      return new ModelAndView(listado, "listadoComunidades.html.hbs");
    }

    return new ModelAndView(null, "listadoComunidadesUsuario.html.hbs");
  }

  public ModelAndView crearComunidad(Request request, Response response) {
    return new ModelAndView(null, "crearComunidad.html.hbs");
  }

  public ModelAndView creandoComunidad(Request request, Response response) {
    String nombre = request.queryParams("nombre");
    String descripcion = request.queryParams("descripcion");

    Comunidad c = new Comunidad(nombre, descripcion);
    withTransaction(() -> {
      RepositorioComunidades.instancia.agregar(c);
    });
    response.redirect("/comunidades");
    return null;
  }

  public ModelAndView eliminarComunidad(Request request, Response response) {
    String id = request.queryParams("valor");
    withTransaction(() -> {
      RepositorioComunidades.instancia.eliminarComunidad(Integer.parseInt(id));
    });

    response.redirect("/comunidades");
    return null;
  }

  public ModelAndView detalleComunidad(Request request, Response response) {
    String id = request.queryParams("valor");
    Comunidad c = RepositorioComunidades.instancia.buscar(Integer.parseInt(id));


    Set<Miembro> listaM = c.getMiembros();
    System.out.println(
        "===========================================================================");
    for (Miembro m : listaM) {
      System.out.println(m.getNombre() + " " + m.getEmail());
    }
    System.out.println(
        "===========================================================================");
    Set<Servicio> listaS = c.getServiciosInteres();
    List<Miembro> listaMC = RepositorioMiembros.instancia.
        listar().stream().filter(m -> estaEnListaMiembro(m, listaM)).toList();
    List<Servicio> listaSN = RepositorioServicio.instancia.
        listar().stream().filter(s -> estaEnListaServicio(s, listaS)).toList();

    Map<String, Object> listado = new HashMap<>();

    listado.put("miembros", listaM);
    listado.put("servicios", listaS);
    listado.put("miembrosCandidatos", listaMC);
    listado.put("serviciosNuevos", listaSN);
    listado.put("nombreComunidad", c.getNombre());
    listado.put("idComunidad", id);


    return new ModelAndView(listado, "detalleComunidad.html.hbs");
  }

  public ModelAndView agregarMiembroComunidad(Request request, Response response) {
    String id = request.params(":idComunidad");
    String idMiembro = request.queryParams("miembro");
    Comunidad c = RepositorioComunidades.instancia.buscar(Integer.parseInt(id));
    Miembro m = RepositorioMiembros.instancia.buscar(Integer.parseInt(idMiembro));

    c.getMiembros().add(m);
    withTransaction(() -> {

      RepositorioComunidades.instancia.agregar(c);

    });
    Comunidad comunidadActualizada = RepositorioComunidades.instancia.buscar(Integer.parseInt(id));
    System.out.println(
        "===========================================================================");
    for (Miembro i : comunidadActualizada.getMiembros()) {
      System.out.println(i.getNombre() + " " + i.getEmail());
    }
    System.out.println(
        "===========================================================================");

    response.redirect("/comunidades");
    return new ModelAndView(null, "detalleComunidad.html.hbs");
  }

  public ModelAndView agregarServicioComunidad(Request request, Response response) {
    String id = request.params(":idComunidad");
    String idServicio = request.queryParams("servicio");
    Comunidad c = RepositorioComunidades.instancia.buscar(Integer.parseInt(id));

    Servicio m = RepositorioServicio.instancia.buscar(Integer.parseInt(idServicio));
    c.getServiciosInteres().add(m);

    withTransaction(() -> {
      RepositorioComunidades.instancia.agregar(c);
    });

    response.redirect("/comunidades");
    return new ModelAndView(null, "listadoComunidades.html.hbs");
  }

  public ModelAndView eliminarMiembroComunidad(Request request, Response response) {
    String id = request.params(":idMiembro");
    String idComunidad = request.params(":idComunidad");

    Comunidad c = RepositorioComunidades.instancia.buscar(Integer.parseInt(idComunidad));

    c.getMiembros().removeIf(m -> m.getId() == Integer.parseInt(id));

    withTransaction(() -> {
      RepositorioComunidades.instancia.agregar(c);
    });
    response.redirect("/comunidades");
    return new ModelAndView(null, "listadoComunidades.html.hbs");
  }

  public ModelAndView eliminarServicioComunidad(Request request, Response response) {
    String id = request.params(":idServicio");
    String idComunidad = request.params(":idComunidad");

    Comunidad c = RepositorioComunidades.instancia.buscar(Integer.parseInt(idComunidad));

    c.getServiciosInteres().removeIf(s -> s.getId() == Integer.parseInt(id));

    withTransaction(() -> {
      RepositorioComunidades.instancia.agregar(c);
    });

    response.redirect("/comunidades");
    return new ModelAndView(null, "listadoComunidades.html.hbs");
  }

/*
    public ModelAndView agregarMiembroPantalla(Request request, Response response){
        String id= request.queryParams("comunidad");
        Comunidad c=RepositorioComunidades.instancia.buscar(Integer.parseInt(id));
        List<Miembro> listaM=c.getMiembros();
        List<Miembro> listaMC=RepositorioMiembros.instancia.listar().stream().filter(mi->estaEnListaMiembro(mi,listaM)).toList();

        Map<String, Object> listado = new HashMap<>();

        listado.put("miembrosCandidatos",listaMC);
        listado.put("idComunidad",id);

        return new ModelAndView(listado, "agregarMiembro.html.hbs");
    }



    public ModelAndView agregarMiembro(Request request, Response response){

        String idComunidad= request.queryParams("comunidad");

        String idMiembro=request.queryParams("miembro");
        Comunidad c=RepositorioComunidades.instancia.buscar(Integer.parseInt(idComunidad));
        Miembro m=RepositorioMiembros.instancia.buscar(Integer.parseInt(idMiembro));
        c.getMiembros().add(m);

        withTransaction(() -> {
            RepositorioComunidades.instancia.agregar(c);
        });

        response.redirect("/detalleComunidad?valor="+idComunidad);
        return new ModelAndView(null, "detalleComunidad.html.hbs");
    }
    */

  public boolean estaEnListaMiembro(Miembro m, Set<Miembro> lista) {
    for (Miembro mi : lista) {
      if (mi.getId() == m.getId()) {
        return false;
      }
    }
    return true;
  }

  public boolean estaEnListaServicio(Servicio m, Set<Servicio> lista) {
    for (Servicio mi : lista) {
      if (mi.getId() == m.getId()) {
        return false;
      }
    }
    return true;
  }
}