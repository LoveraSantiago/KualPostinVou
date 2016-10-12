package lovera.kualpostinvou.modelos.utils;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.HoraMinuto;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.Tipo;
import lovera.kualpostinvou.modelos.TipoObjeto;
import lovera.kualpostinvou.views.utils.Utils_View;

public class FactoryModelos {

    public static Grupo geradorDeGrupo(String codUnidade){
        Grupo grupoResult = new Grupo();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CU" + codUnidade + "T");
        stringBuilder.append(Utils_View.dateToString(Calendar.getInstance().getTime(), "ddMMyyyy"));
        grupoResult.setDescricao(stringBuilder.toString());
        grupoResult.setCodObjeto(100);//100 -> Estabelecimento de Saude

        return grupoResult;
    }

    public static Postagem geradorDePostagem(Grupo grupo, TipoObjeto tipoObjeto){
        Postagem postagemResult = new Postagem();

        postagemResult.setAutor(geradorDeAutor());
        postagemResult.setCodPessoaDestino(postagemResult.getAutor().getCodPessoa());
        postagemResult.setCodGrupoDestino(grupo.getCodGrupo());
        postagemResult.setTipo(geradorTipo());
        postagemResult.setCodTipoObjetoDestino(100);//100 -> Estabelecimento de Saude
        postagemResult.setCodObjetoDestino(tipoObjeto.getCodTipoObjeto());

        return postagemResult;
    }

    public static Autor geradorDeAutor(){
        Autor autorResult = new Autor();
        autorResult.setCodPessoa(Aplicacao.getPessoaLogada().getPessoa().getCod());
        return autorResult;
    }

    public static Tipo geradorTipo(){
        Tipo tipoResult = new Tipo();
        tipoResult.setCodTipoPostagem(75);//nota tempo de espera
        return tipoResult;
    }

    public static TipoObjeto geradorTipoObjeto(Grupo grupo){
        TipoObjeto tipoObjetoResult = new TipoObjeto();
        tipoObjetoResult.setDescricao(grupo.getDescricao());
        tipoObjetoResult.setOwnerTabelaObjeto(grupo.getDescricao());
        tipoObjetoResult.setTabelaObjeto(grupo.getDescricao());
        return tipoObjetoResult;
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

    public static Localizacao geradorLocalizacao(Location location){
        return geradorLocalizacao(location.getLatitude(), location.getLongitude());
    }

    public static Localizacao gerardorLocalizacao(Localizacao localizacao){
        return geradorLocalizacao(localizacao.getLatitude(), localizacao.getLongitude());
    }

    public static ConteudoPostagem geradorConteudoPostagem(Postagem postagem, int tempo){
        ConteudoPostagem conteudoResult = new ConteudoPostagem();
        conteudoResult.setCodConteudo(postagem.getCodPostagem());
        conteudoResult.setValor(tempo);
        return conteudoResult;
    }

    public static HoraMinuto geradorHoraMinuto(int tempoMinutos){
        HoraMinuto horaMinutoResult = new HoraMinuto(tempoMinutos / 60, tempoMinutos % 60);
        return horaMinutoResult;
    }
}
