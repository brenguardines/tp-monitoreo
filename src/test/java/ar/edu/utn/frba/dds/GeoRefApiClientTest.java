package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.api.GeoRefApi;
import ar.edu.utn.frba.dds.api.GeoRefApiClient;
import ar.edu.utn.frba.dds.api.ServicioGeoRefApi;
//import com.accuweather.AccuWeatherAPI;
import ar.edu.utn.frba.dds.localizacion.Departamento;
import ar.edu.utn.frba.dds.localizacion.Localizacion;
import ar.edu.utn.frba.dds.localizacion.Municipio;
import ar.edu.utn.frba.dds.localizacion.Provincia;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;


public class GeoRefApiClientTest {


    @Test
    void traigoProvinciaBuenosAires() {
        GeoRefApiClient api = Mockito.mock(GeoRefApiClient.class);
        GeoRefApi servicio = new ServicioGeoRefApi(api);

        String resultado = "[\n" +
            "        {\n" +
            "            \"centroide_lat\": -36.6769415180527,\n" +
            "            \"centroide_lon\": -60.5588319815719,\n" +
            "            \"id\": \"06\",\n" +
            "            \"nombre\": \"Buenos Aires\"\n" +
            "        }\n" +
            "  ]";

        try {
            JSONArray jsonArrayProvinciaBuenosAires = new JSONArray(resultado);
            Mockito.when(api.traerJsonProvincias()).thenReturn(jsonArrayProvinciaBuenosAires);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        List<Localizacion> provincias = servicio.traerProvincias();
        List<Localizacion> localizaciones = new ArrayList<Localizacion>();
        Localizacion localizacionBsAs = new Provincia(06, "Buenos Aires");
        localizaciones.add(localizacionBsAs);

        Assertions.assertNotNull(provincias);
        Assertions.assertEquals(provincias.get(0).getNombre(), localizaciones.get(0).getNombre());
    }

    @Test
    void traigoDepartamentosTandil() {
        GeoRefApiClient api = Mockito.mock(GeoRefApiClient.class);
        GeoRefApi servicio = new ServicioGeoRefApi(api);

        String resultado = "[{\n" +
            "            \"centroide\": {\n" +
            "                \"lat\": -37.3356554181577,\n" +
            "                \"lon\": -59.181805577778\n" +
            "            },\n" +
            "            \"id\": \"791\",\n" +
            "            \"nombre\": \"Tandil\",\n" +
            "            \"provincia\": {\n" +
            "                \"id\": \"06\",\n" +
            "                \"nombre\": \"Buenos Aires\"\n" +
            "            }\n" +
            "        }]";

        try {
            List<JSONArray> jsonArrayDepartamentosTandil = new ArrayList<>();
            JSONArray departamentoTandil = new JSONArray(resultado);
            jsonArrayDepartamentosTandil.add(departamentoTandil);
            Mockito.when(api.traerJsonDepartamentos()).thenReturn(jsonArrayDepartamentosTandil);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        List<Localizacion> departamentos = servicio.traerDepartamentos();
        List<Localizacion> localizaciones = new ArrayList<Localizacion>();
        Provincia provinciaBuenosAires = new Provincia(06,"Buenos Aires");
        Localizacion localizacionTandil = new Departamento(791, "Tandil",provinciaBuenosAires);
        localizaciones.add(localizacionTandil);

        Assertions.assertNotNull(departamentos);
        Assertions.assertEquals(departamentos.get(0).getNombre(), localizaciones.get(0).getNombre());
    }

    @Test
    void traigoMunicipioYavi() {
        GeoRefApiClient api = Mockito.mock(GeoRefApiClient.class);
        GeoRefApi servicio = new ServicioGeoRefApi(api);

        String resultado = "[{\n" +
            "            \"centroide\": {\n" +
            "                \"lat\": -22.1949119799291,\n" +
            "                \"lon\": -65.3412864273208\n" +
            "            },\n" +
            "            \"id\": \"273\",\n" +
            "            \"nombre\": \"Yavi\",\n" +
            "            \"provincia\": {\n" +
            "                \"id\": \"38\",\n" +
            "                \"nombre\": \"Jujuy\"\n" +
            "            }\n" +
            "        }]";

        try {
            List<JSONArray> jsonArrayMunicipioYavi = new ArrayList<>();
            JSONArray municipioYavi = new JSONArray(resultado);
            jsonArrayMunicipioYavi.add(municipioYavi);
            Mockito.when(api.traerJsonMunicipios()).thenReturn(jsonArrayMunicipioYavi);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        List<Localizacion> municipios = servicio.traerMunicipios();
        List<Localizacion> localizaciones = new ArrayList<Localizacion>();
        Provincia provinciaJujuy = new Provincia(38,"Jujuy");
        Localizacion localizacionYavi = new Municipio(273, "Yavi",provinciaJujuy);
        localizaciones.add(localizacionYavi);

        Assertions.assertNotNull(municipios);
        Assertions.assertEquals(municipios.get(0).getNombre(), localizaciones.get(0).getNombre());
    }


}
