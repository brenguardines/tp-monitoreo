package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Incidente;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.HOURS;

public class CriterioporCantidadIncidentes implements CriterioRankings {

  Map<String, Integer> cantIncidentes;

  @Override
  public Map<Entidad, Integer> aplicarCriterio(List<Entidad> entidades, List<Incidente> incidentes) { //uso la funcion y si cumple lo agrego a la lista

    Map<Entidad, Integer> entidadesMayorCantIncidentes = new HashMap<>();

    //Filtra los incidentes que se realizaron en la ultima semana
    List<Incidente> incidentesDeLaSemana = incidentes
      .stream().filter(i -> i.getApertura().isAfter(LocalDateTime.now().minusDays(7))).toList();

    for (Entidad enti : entidades) {

      //Filtra los incidentes de la ultima semana por entidad
      List<Incidente> incidentesDeCadaEntidad = incidentesDeLaSemana
        .stream().filter(i -> i.getEstablecimiento().getEntidad().getId() == enti.getId()).toList();

      System.out.println("Incidentes en la ultima semana " + incidentesDeCadaEntidad.size());
      int contadorIncidentes = 0;

      for (Incidente i : incidentesDeCadaEntidad) {
        contadorIncidentes += comprobaciones(i, incidentesDeCadaEntidad
            .stream().filter(is -> i.getServicio().getId() == is.getServicio().getId()).toList());
      }
      entidadesMayorCantIncidentes.put(enti, contadorIncidentes);

    }

    Map<Entidad, Integer> ordenado = entidadesMayorCantIncidentes.entrySet()
        .stream().sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
            (oldValue, newValue) -> oldValue, LinkedHashMap::new)); //Ordena el Map de mayor a menor

    System.out.println("Los valores se ordenaron");
    System.out.println("=============================");
    entidades.forEach(e -> System.out.println(e.getNombre() + " Cantidad contada "
        + ordenado.get(e)));

    return ordenado;
  }

  public int comprobaciones(Incidente i, List<Incidente> mismoServicio) {
    int respuesta = 0;
    if (mismoServicio.get(0).getId() == i.getId()) {
      respuesta = 1; //se verifica si coincide con el primer
      // elemento de la lista(es el mismo pq no hay otro antes)
      System.out.println("El incidente es el primero de la lista");
      return respuesta;

    }
    for (Incidente servicio : mismoServicio) {

      if (servicio.getId() == i.getId()) {
        System.out.println("El incidente no esta dentro de las 24 horas de otro incidente");
        return 1;
      }

      if (servicio.getVigente()) {

        if (i.getApertura().isBefore(servicio.getApertura().plusHours(24))) {
          System.out.println("Esta dentro de las 24 horas de un incidente abierto");
          return 0;
        } else {
          System.out.println("No esta dentro de las 24 horas de un incidente abierto");
          respuesta = 1;
        }
      } else {

        long horasEntreServicioyI = HOURS.between(servicio.getApertura(), i.getApertura());

        System.out.println(i.getApertura() + " " + servicio.getApertura());

        System.out.println("Horas " + horasEntreServicioyI + " " +
            servicio.getId() + " " + i.getId());

        if (HOURS.between(servicio.getApertura(), servicio.getCierre()) >
            horasEntreServicioyI && horasEntreServicioyI < 24) {
          System.out.println("El incidente sucedio dentro del margen de " +
              "cierre de otro incidente y dentro de las 24 horas");
          return 0;
        } else {
          respuesta = 1;
        }
      }
    }
    return respuesta;
  }

  @Override
  public boolean verificarSegunCriterio(Incidente incidente) {
    return incidente.estaVigente() && (tiempoDeAperturaAlMomento(incidente) > 24);
  }

  public long tiempoDeAperturaAlMomento(Incidente incidente) {
    return HOURS.between(LocalDateTime.now(), incidente.getApertura());
  }

  //public void aumentarContadorEntidad(String nombreEntidad) { }

}


