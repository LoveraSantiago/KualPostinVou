package lovera.kualpostinvou.modelos.utils;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.Tipo;
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
        postagemResult.setTipo(geradorTipo(codGrupo));
        postagemResult.setCodTipoObjetoDestino(codGrupo);
        postagemResult.setCodObjetoDestino(codGrupo);

        return postagemResult;
    }

    public static Postagem geradorDePostagem(Grupo grupo){
        return geradorDePostagem(grupo.getCodGrupo());
    }

    public static Autor geradorDeAutor(){
        Autor autorResult = new Autor();
        autorResult.setCodPessoa(Aplicacao.getPessoaLogada().getPessoa().getCod());
        return autorResult;
    }

    public static Tipo geradorTipo(int codGrupo){
        Tipo tipoResult = new Tipo();
        tipoResult.setCodTipoPostagem(codGrupo);
        return tipoResult;
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

    public static ConteudoPostagem gerarConteudoPostagem(Postagem postagem, int tempo){
        ConteudoPostagem conteudoResult = new ConteudoPostagem();
        conteudoResult.setCodPostagem(postagem.getCodPostagem());
        conteudoResult.setValor(tempo);
        return conteudoResult;
    }
}
