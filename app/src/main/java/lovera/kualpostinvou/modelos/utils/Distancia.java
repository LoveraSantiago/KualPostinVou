package lovera.kualpostinvou.modelos.utils;

import java.util.List;

import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;

public class Distancia {

    private static double RAIO_TERRA = 6371;

    public static double calcularKmDistancia(Localizacao localizacao, double latitude, double longitude){
        return calcularKmDistancia(localizacao.getLatitude(), latitude, localizacao.getLongitude(), longitude, 0, 0);
    }

    public static double calcularKmDistancia(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static void calcularKmDistancia(List<Estabelecimento> listaEstabelecimentos, Localizacao origem){
        for(Estabelecimento estabelecimento : listaEstabelecimentos){
            double result = calcularKmDistancia(origem, estabelecimento.getLat(), estabelecimento.getLongi());
            estabelecimento.setDistancia(result);
        }
    }
}
