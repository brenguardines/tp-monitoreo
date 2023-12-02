package ar.edu.utn.frba.dds.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lectorcsv {
  private BufferedReader lector;
  private String linea;
  private String[] datos = null;

  public Lectorcsv() {

  }

  public List<OrganismoControl> leerArchivo(String path) {
    List<OrganismoControl> avolcar = new ArrayList<>();
    try {
      lector = new BufferedReader(new FileReader(path));
      while ((linea = lector.readLine()) != null) {
        datos = linea.split(";");
        OrganismoControl reg = new OrganismoControl(datos[0],
            TipoRegulador.valueOf(datos[1]), datos[2]);
        avolcar.add(reg);
      }
      return avolcar;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}