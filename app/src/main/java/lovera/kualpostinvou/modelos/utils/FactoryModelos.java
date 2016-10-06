package lovera.kualpostinvou.modelos.utils;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.views.utils.Utils_View;

public class FactoryModelos {

    public static Grupo geradorDeGrupo(String codUnidade){
        Grupo grupoResult = new Grupo();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CU" + codUnidade + "T");
        stringBuilder.append(Utils_View.dateToString(Calendar.getInstance().getTime(), "ddMMyyyy"));
        grupoResult.setDescricao(stringBuilder.toString());
        return grupoResult;
    }

    public static Postagem geradorDePostagem(int codGrupo){
        Postagem postagemResult = new Postagem();

        postagemResult.setAutor(geradorDeAutor());
        postagemResult.setCodGrupoDestino(codGrupo);

        return postagemResult;
    }

    public static Autor geradorDeAutor(){
        Autor autorResult = new Autor();
        autorResult.setCodPessoa(Aplicacao.getPessoaLogada().getPessoa().getCod());
        return autorResult;
    }

    public static Localizacao geradorLocalizacao(double latitude, double longitude){
        Localizacao localizacaoResult = new Localizacao();
        localizacaoResult.setLatitude(latitude);
        localizacaoResult.setLongitude(longitude);
        return localizacaoResult;
    }

    public static Localizacao geradorLocalizacao(Estabelecimento estabelecimento){;
        return geradorLocalizacao(estabelecimento.getLat(), estabelecimento.getLongi());
    }

    public static Localizacao geradorLocalizacao(LatLng latLng){
        return geradorLocalizacao(latLng.latitude, latLng.longitude);
    }

    public static Localizacao geradorLocalizacao(Address address){
        return geradorLocalizacao(address.getLatitude(), address.getLongitude());
    }
}
