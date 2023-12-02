package ar.edu.utn.frba.dds.servicio;

public enum TipoServicio {
  BANIO(null),
  ESCALERA_MECANICA(CategoriaTipoServicio.MEDIO_ELEVACION),
  ASCENSOR(CategoriaTipoServicio.MEDIO_ELEVACION);

  private CategoriaTipoServicio categoria;

  TipoServicio(CategoriaTipoServicio categoria) {
    this.categoria = categoria;
  }
}
