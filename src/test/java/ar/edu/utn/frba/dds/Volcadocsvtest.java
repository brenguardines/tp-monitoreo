package ar.edu.utn.frba.dds;
import ar.edu.utn.frba.dds.parser.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;


public class Volcadocsvtest {
    private List<OrganismoControl> volcado;
    private Lectorcsv csv;
    @BeforeEach
    void setup() {
        csv = new Lectorcsv();
        volcado=csv.leerArchivo("pyc.csv");
    }
    @Test
    void tamanioDeLista() {
        Assertions.assertEquals(volcado.size(),4);
    }

    @Test
    void verificarObjetoRecuperado(){
        Assertions.assertEquals(volcado.get(0).getClass(), OrganismoControl.class);
    }

}
