package lovera.kualpostinvou.modelos.utils;

import java.util.List;

import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;

public class Distancia {

    public double calcularKmDistancia(Localizacao origem, Estabelecimento estabelecimento) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(estabelecimento.getLat() - origem.getLatitude());
        Double lonDistance = Math.toRadians(estabelecimento.getLongi() - origem.getLongitude());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(origem.getLatitude())) * Math.cos(Math.toRadians(estabelecimento.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = 0 - 0;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance) / 1000;
    }

    public void calcularKmDistancia(List<Estabelecimento> listaEstabelecimentos, Localizacao origem){
        for(Estabelecimento estabelecimento : listaEstabelecimentos){
            double result = calcularKmDistancia(origem, estabelecimento);
            estabelecimento.setDistancia(result);
        }
    }
}
