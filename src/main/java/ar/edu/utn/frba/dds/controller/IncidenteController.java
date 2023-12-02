package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.*;
import ar.edu.utn.frba.dds.parser.Rankingscsv;
import ar.edu.utn.frba.dds.repositorio.*;
import ar.edu.utn.frba.dds.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class IncidenteController implements WithSimplePersistenceUnit {

  public ModelAndView listarIncidentes(Request request, Response response) {
    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if (usuarioID == null) {
      response.redirect("/");
    }
    System.out.println(usuarioID);

    String filtro = request.queryParams("estado");
    System.out.println(filtro);

    if (filtro == null) {
      response.redirect("/incidentes?estado=Activos");
    }

    List<Incidente> lista = RepositorioIncidentes.instancia.buscarPorEstado(filtro);

    Map<String, Object> listado = new HashMap<>();

    List<IncidenteVista> listaFormatteada = new ArrayList<>();

    lista.forEach(l -> {
      String fechaApertura = "";
      String fechaCierre = "";
      if (l.getApertura() != null) {
        fechaApertura = this.darFormato(l.getApertura());
      }
      if (l.getCierre() != null) {
        fechaCierre = this.darFormato(l.getCierre());
      }

      IncidenteVista elementoFormateado = new IncidenteVista();
      elementoFormateado.setId(l.getId());
      elementoFormateado.setObservacion(l.getObservacion());
      elementoFormateado.setMiembroApertura(l.getMiembroApertura());
      elementoFormateado.setEstablecimiento(l.getEstablecimiento());
      elementoFormateado.setServicio(l.getServicio());
      elementoFormateado.setVigente(l.getVigente());
      elementoFormateado.setApertura(fechaApertura);
      elementoFormateado.setCierre(fechaCierre);

      listaFormatteada.add(elementoFormateado);
    });

    listado.put("incidentes", listaFormatteada);
    listado.put("filtroSelected", filtro);

    return new ModelAndView(listado, "listadoIncidentes.html.hbs");
  }

  public static String darFormato(LocalDateTime date) {
    DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
    return date.format(CUSTOM_FORMATTER);
  }

  public ModelAndView listarRankings(Request request, Response response) {
    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");

    if (usuarioID == null) {
      response.redirect("/");
    }
    String filtro = request.queryParams("valor");

    String eleccion = "Elegir ranking";
    if (filtro == null) {
      Map<String, Object> apertura = new HashMap<>();

      apertura.put("elegido", eleccion);
      return new ModelAndView(apertura, "rankingIncidentes.html.hbs");
    }
    if (filtro.equals("1")) {
      eleccion = "Por Promedio de Incidentes";
    } else {
      eleccion = "Por cantidad de Incidentes";
    }

    List<Map<String, Object>> list = new Rankingscsv().listarCSV(filtro);
    Map<String, Object> apertura = new HashMap<>();

    apertura.put("rankings", list);
    apertura.put("elegido", eleccion);

    return new ModelAndView(apertura, "rankingIncidentes.html.hbs");
  }

  public ModelAndView pantallaAbrirIncidente(Request request, Response response) {
    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if (usuarioID == null) {
      response.redirect("/");
    }
    String selectedEstablecimiento = request.queryParams("establecimiento");

    if (selectedEstablecimiento == null) {
      List<Establecimiento> lista = RepositorioEstablecimiento.instancia.listar();
      Map<String, Object> apertura = new HashMap<>();
      apertura.put("establecimientos", lista);
      apertura.put("nombreEstablecimiento", "");
      return new ModelAndView(apertura, "aperturaIncidentes.html.hbs");
    } else {
      List<Establecimiento> lista = RepositorioEstablecimiento.instancia.listar();
      List<Servicio> listaServicio = RepositorioServicio.instancia.
          listarSegunEstablecimiento(Integer.parseInt(selectedEstablecimiento));

      Map<String, Object> apertura = new HashMap<>();

      Establecimiento establecimientoSelected = lista.stream().filter(l -> l.getId() == Integer.parseInt(selectedEstablecimiento)).collect(Collectors.toList()).get(0);
      System.out.println(establecimientoSelected.getNombre()+" "+establecimientoSelected.getId());
      System.out.println("=============================================");

      apertura.put("establecimientos", lista);
      apertura.put("servicios", listaServicio);
      apertura.put("establecimientoSelect", establecimientoSelected.getId());
      apertura.put("nombreEstablecimiento", establecimientoSelected.getNombre());

      return new ModelAndView(apertura, "aperturaIncidentes.html.hbs");
    }
  }

  public ModelAndView crearIncidente(Request request, Response response) {

    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");

    String observacion = request.queryParams("observacion");
    String idEstablecimiento = request.queryParams("establecimientoId");
    String idServicio = request.queryParams("servicio");

    System.out.println("========================================================");
    System.out.println("Observacion "+observacion+" EStablecimiento "+idEstablecimiento+" Servicio "+idServicio);

    System.out.println("========================================================");

    if (idServicio == null) {
      return new ModelAndView(null, "aperturaIncidentes.html.hbs");
    }

    withTransaction(() -> {
      RepositorioIncidentes.instancia.agregar(
          new Incidente(RepositorioServicio.instancia.buscar(Integer.parseInt(idServicio))
              , observacion,
              RepositorioEstablecimiento.instancia.buscar(Integer.parseInt(idEstablecimiento)),
              RepositorioUsuario.instancia.getById(Integer.parseInt(usuarioID)).getMiembro()));
    });

    response.redirect("/incidentes");
    return null;
  }


  public ModelAndView cerrarIncidente(Request request, Response response) {

    String id = request.params(":id");


      Incidente i = RepositorioIncidentes.instancia.buscar(Integer.parseInt(id));
      i.cerrarIncidente();

      withTransaction(() -> {
        RepositorioIncidentes.instancia.agregar(i);
      });

    response.redirect("/incidentes?estado=Activos");
    return null;
  }

  public ModelAndView sugerenciaIncidente(Request request, Response response) {
    Session session = request.session();
    String usuarioID = session.attribute("usuarioID");
    if (usuarioID == null) {
      response.redirect("/");
    }

    Usuario u = RepositorioUsuario.instancia.getById(Integer.parseInt(usuarioID));

    Miembro m = RepositorioMiembros.instancia.buscar(u.getMiembro().getId());
    Posicion p = m.obtenerposicion();

    List<Establecimiento> listadoE = RepositorioEstablecimiento.instancia.listar();

    List<Establecimiento> filtrados = listadoE.stream().
        filter(e -> m.estaCercaDeEstablecimiento(p, e.getUbicacion())).toList();
    System.out.println("Cantidad " + filtrados.size());

    List<Incidente> lista = RepositorioIncidentes.instancia.listar();
    System.out.println("Cantidad incidentes " + lista.size());

    List<Incidente> cercanos = new ArrayList<>();
    for (Establecimiento e : filtrados) {
      for (Incidente i : lista) {
        if (i.getEstablecimiento().getId() == e.getId() && i.getVigente()) {
          cercanos.add(i);
        }
      }
    }

    Map<String, Object> apertura = new HashMap<>();
    apertura.put("notificaciones", cercanos);

    return new ModelAndView(apertura, "revisionDeIncidentes.html.hbs");
  }
}