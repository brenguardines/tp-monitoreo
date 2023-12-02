package ar.edu.utn.frba.dds.serviciovalidacioncontrasenia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeerArchivoContrasenia {

  public static List<String> listaPeoresContrasenias()  {
    List<String> peoresContrasenias = new ArrayList<>();
    String path = "10000PeoresContrasenias.txt";

    File file = new File(path);

    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String contrasenia = scanner.nextLine();
        peoresContrasenias.add(contrasenia);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return peoresContrasenias;
  }
}
