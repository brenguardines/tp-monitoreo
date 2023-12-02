package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Incidente;
import java.util.List;
import java.util.Map;

public interface CriterioRankings {
  public Map<Entidad, Integer> aplicarCriterio(List<Entidad> entidades, List<Incidente> incidentes);

  public boolean verificarSegunCriterio(Incidente incidente);


}
