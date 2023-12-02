package ar.edu.utn.frba.dds.parser;

import com.opencsv.CSVReader;
import org.apache.commons.csv.CSVPrinter;
import ar.edu.utn.frba.dds.Entidad;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;

import java.util.ArrayList;

public class Rankingscsv {
  public static Rankingscsv rank;
  public List<String[]> entidades;

  public Rankingscsv() {
    entidades = new ArrayList<>();
  }

  public void crearRankingscsv(Map<Entidad, Integer> ranking, String nombre) {
    File f = new File(crearPath(nombre));

    try (FileWriter fileWriter = new FileWriter(f);
         CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Nombre", "Tipo", "Resultado"))) {
      List<Entidad> entidades = ranking.keySet().stream().toList();
      List<Integer> resultados = ranking.values().stream().toList();
      int i = 0;
      for (Entidad entidad : entidades) {

        csvPrinter.printRecord(entidad.getNombre(), entidad.getTipo(), resultados.get(i));
        i++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static Rankingscsv getInstance() {
    if (rank == null) {
      return new Rankingscsv();
    }
    return rank;
  }

  public String crearPath(String nombre) {
    return ("./" + nombre + ".csv");
  }

  public String[] fila(Entidad e) {
    String[] w = {e.getNombre(), e.getTipo()};
    return w;
  }

  public List<Map<String, Object>> listarCSV(String filtro){
    String csvFilePath = "";
    switch (Integer.parseInt(filtro)) {
      case 1:
        csvFilePath = "RankingPromedio.csv";
        break;
      case 2:
        csvFilePath = "RankingPorCantidad.csv";
        break;
      default:
        break;
    }

    try {
      CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));

      List<String[]> allRows = csvReader.readAll();
      csvReader.close();

      String[] headers = allRows.get(0);

      List<Map<String, Object>> dataList = new ArrayList<>();

      for (int i = 1; i < allRows.size(); i++) {
        String[] currentRow = allRows.get(i);
        Map<String, Object> dataMap = new HashMap<>();

        for (int j = 0; j < headers.length; j++) {
          dataMap.put(headers[j], currentRow[j]);
        }

        dataList.add(dataMap);
      }

      return dataList;
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<Map<String, Object>>();
    }
  }
}


