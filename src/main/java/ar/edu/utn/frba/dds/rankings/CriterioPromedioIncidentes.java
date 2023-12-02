package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Incidente;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CriterioPromedioIncidentes implements CriterioRankings {

  @Override
  public Map<Entidad, Integer> aplicarCriterio(List<Entidad> entidades,
                                               List<Incidente> incidentes) { //uso la funcion y si cumple lo agrego a la lista

    Map<Entidad, Integer> entidadesMayorPromedioTiempo = new HashMap<>();
    List<Incidente> incidentesCerradosSemana = incidentes.stream()
        .filter(i -> !i.getVigente()).toList();

    for (Entidad enti : entidades) {
      List<Incidente> incidentesDeCadaEntidad = incidentesCerradosSemana.stream()
          .filter(i -> i.getEstablecimiento().getEntidad().getId() == enti.getId()).toList();

      System.out.println("Cantidad de incidentes de entidad " + enti.getNombre()
          + " son " + incidentesDeCadaEntidad.size());

      System.out.println("Incidentes cerrados " + incidentesDeCadaEntidad.size());

      //List<Long> difCierreApertura = new ArrayList<>();
      long sumatoria = 0;
      for (Incidente i : incidentesDeCadaEntidad) {
        sumatoria += i.tiempoDeCierre();
      }
      System.out.println("La sumatoria es " + sumatoria);
      int promedio = Math.round(sumatoria / incidentesDeCadaEntidad.size());
      System.out.println("El promedio es " + promedio);
      entidadesMayorPromedioTiempo.put(enti, promedio);

    }


    Map<Entidad, Integer> ordenado = entidadesMayorPromedioTiempo
        .entrySet().stream().sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
            (oldValue, newValue) -> oldValue, LinkedHashMap::new)); //Ordena el Map de mayor a menor

    System.out.println("Los valores se ordenaron");

    return ordenado;

  }

  @Override
  public boolean verificarSegunCriterio(Incidente incidente) {
    return !incidente.getVigente();
  }


}
