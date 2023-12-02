package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.controller.ComunidadController;
import ar.edu.utn.frba.dds.controller.IncidenteController;
import ar.edu.utn.frba.dds.controller.MiembroController;
import ar.edu.utn.frba.dds.controller.RegistroController;
import ar.edu.utn.frba.dds.controller.UsuarioController;
import ar.edu.utn.frba.dds.spark.template.handlebars.HandlebarsTemplateEngine;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.Spark;
import javax.persistence.EntityManager;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;


public class Routes implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Routes().start();
  }

  public void start() {
    Spark.port(8080);

    Spark.staticFileLocation("/public");

    Spark.after((request, response) -> {
      EntityManager em = entityManager();
      if (em.isOpen()) {
        em.close();
      }
    });

    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    RegistroController registroController = new RegistroController();
    IncidenteController incidenteController = new IncidenteController();
    MiembroController miembroController = new MiembroController();
    UsuarioController usuarioController = new UsuarioController();
    ComunidadController comunidadController = new ComunidadController();


    Spark.get("/", usuarioController::inicioSesion, engine);
    Spark.post("/", usuarioController::startLogin, engine);

    Spark.post("/sesion", usuarioController::cerrarSesion, engine);

    Spark.get("/incidentes", incidenteController::listarIncidentes, engine);

    Spark.get("/incidentes/nuevo", incidenteController::pantallaAbrirIncidente, engine);
    Spark.post("/incidentes/nuevo", incidenteController::crearIncidente, engine);

    Spark.get("/incidentes/:id", incidenteController::cerrarIncidente, engine);

    Spark.get("/rankings", incidenteController::listarRankings, engine);

    Spark.get("/sugerencias", incidenteController::sugerenciaIncidente, engine);

    Spark.get("/registro", registroController::registro, engine);

    Spark.post("/registro", registroController::crearUsuario, engine);

    Spark.get("/miembros", miembroController::configurarMiembro, engine);

    Spark.post("/miembros", miembroController::terminarRegistro, engine);

    Spark.get("/comunidades", comunidadController::listarComunidades, engine);

    Spark.get("/comunidades/nuevo", comunidadController::crearComunidad, engine);
    Spark.post("/comunidades/nuevo", comunidadController::creandoComunidad, engine);

    Spark.get("/comunidades/detalle", comunidadController::detalleComunidad, engine);
    Spark.post("/comunidades", comunidadController::eliminarComunidad, engine);

    Spark.post("/comunidades/detalle/miembro/:idComunidad", comunidadController::agregarMiembroComunidad, engine);

    Spark.post("/comunidades/detalle/servicio/:idComunidad", comunidadController::agregarServicioComunidad, engine);

    Spark.get("/comunidades/detalle/miembro/:idMiembro/:idComunidad", comunidadController::eliminarMiembroComunidad, engine);


    Spark.get("/comunidades/detalle/servicio/:idServicio/:idComunidad", comunidadController::eliminarServicioComunidad, engine);

/*
    Spark.exception(PersistenceException.class, (e, request, response) -> {
      response.redirect("/500");
    });
*/


  }


}


