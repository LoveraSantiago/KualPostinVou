package lovera.kualpostinvou.conexao;

import android.net.Uri;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.conexao.callbacks.CallBackAutenticar;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarInstalacao;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarPessoa;
import lovera.kualpostinvou.conexao.callbacks.CallBackCadastrarCPostagem;
import lovera.kualpostinvou.conexao.callbacks.CallBackConteudoPostagem;
import lovera.kualpostinvou.conexao.callbacks.CallBackEditConteudoPostagem;
import lovera.kualpostinvou.conexao.callbacks.CallBackGrupo;
import lovera.kualpostinvou.conexao.callbacks.CallBackGrupos;
import lovera.kualpostinvou.conexao.callbacks.CallBackImgPerfil;
import lovera.kualpostinvou.conexao.callbacks.CallBackMedia;
import lovera.kualpostinvou.conexao.callbacks.CallBackPostagem;
import lovera.kualpostinvou.conexao.callbacks.CallBackPostagens;
import lovera.kualpostinvou.conexao.callbacks.CallBackTipoObjeto;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.conexao.endpoints.EndPointsMetaModelo;
import lovera.kualpostinvou.conexao.utils.FactoryConexao;
import lovera.kualpostinvou.conexao.utils.HelperParams_EndPModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.GrupoR;
import lovera.kualpostinvou.modelos.Media;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.modelos.Instalacao;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.TipoObjeto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConexaoMetaModelo {

    private static String URL_BASE = "http://mobile-aceite.tcu.gov.br";

    private final Retrofit retrofit;

    private final EndPointsMetaModelo endpointMetaModelo;

    private final MsgFromConexaoModelo msg;

    private final HelperParams_EndPModelo helper;

    public ConexaoMetaModelo(MsgFromConexaoModelo msg) {
        this.msg = msg;
        this.helper = new HelperParams_EndPModelo();

        this.retrofit = FactoryConexao.factoryRetrofit(URL_BASE);
        this.endpointMetaModelo = retrofit.create(EndPointsMetaModelo.class);
    }

    public void downloadImageNaUrl(Uri uri){
        this.endpointMetaModelo.downloadImageNaUrl(uri.toString())
                      .enqueue(new CallBackImgPerfil());
    }

    public void autenticar(Pessoa pessoa, StringBuilder token, ErrorObj error){
        Map<String, String> mapParams = this.helper.factoryMap_EndP_Autenticar(null, pessoa.getEmail(), null, pessoa.getTokenFacebook(), pessoa.getTokenGoogle(), pessoa.getTokenTwitter());
        Call<Pessoa> call = this.endpointMetaModelo.autenticar(mapParams);
        try {
            Response<Pessoa> response = call.execute();
            CallBackAutenticar.procedimentoSincrono(this.retrofit, response, pessoa, token, error);
        } catch (IOException e) {
            error.setReasonPhrase("Exception conexaoMetaModelo -> autenticar");
            e.printStackTrace();
        }
    }

    public void cadastrarInstalacao(String appToken, Instalacao instalacao, StringBuilder result, ErrorObj error){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarInstalacao(appToken, instalacao);
        try {
            Response<ResponseBody> response = call.execute();
            CallBackCadastrarInstalacao callBackCadastrarInstalacao = new CallBackCadastrarInstalacao(this.retrofit);
            callBackCadastrarInstalacao.procedimentoSincrono(response, result, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarPessoa(Pessoa pessoa, StringBuilder location, ErrorObj error){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarPessoa(pessoa);
        try {
            Response<ResponseBody> response = call.execute();
            CallBackCadastrarPessoa callBackCadastrarPessoa = new CallBackCadastrarPessoa(this.retrofit);
            callBackCadastrarPessoa.procedimentoSincrono(response, location, error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getGrupo(Grupo grupo, int resultCode){
        Map<String, String> mapParams = this.helper.factoryMap_EndPGrupos(grupo);
        Call<List<GrupoR>> call = this.endpointMetaModelo.getGrupos(mapParams);
        call.enqueue(new CallBackGrupos(this.msg, this.retrofit, resultCode));
    }

    public void cadastrarGrupo(String appToken, Grupo grupo, int resultCode){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarGrupo(appToken, grupo);
        call.enqueue(new CallBackGrupo(this.msg, grupo, resultCode));
    }

    public void cadastrarPostagem(String codApp, String appToken, Postagem postagem){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarPostagem(codApp, appToken, postagem);
        call.enqueue(new CallBackPostagem(this.msg, postagem));
    }

    public void cadastrarPostagem(int codApp, String appToken, Postagem postagem){
        cadastrarPostagem(String.valueOf(codApp), appToken, postagem);
    }

    public void getPostagens(String appToken, long codGrupo){
        Call<List<PostagemR>> call = this.endpointMetaModelo.getPostagens(appToken, codGrupo);
        call.enqueue(new CallBackPostagens(this.msg));
    }

    public void cadastrarConteudoPostagem(String appToken, String codPostagem, ConteudoPostagem conteudoPostagem){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarConteudoPostagem(appToken, codPostagem, conteudoPostagem);
        call.enqueue(new CallBackCadastrarCPostagem(this.msg, conteudoPostagem));
    }

    public void cadastrarConteudoPostagem(String appToken, long codPostagem, ConteudoPostagem conteudoPostagem){
        cadastrarConteudoPostagem(appToken, String.valueOf(codPostagem), conteudoPostagem);
    }

    public void getConteudoPostagem(String appToken, String codPostagem, String codConteudo){
        Call<ConteudoPostagem> call = this.endpointMetaModelo.getConteudoPostagem(appToken, codPostagem, codConteudo);
        call.enqueue(new CallBackConteudoPostagem(this.msg));
    }

    public void getConteudoPostagem(String appToken, long codPostagem, long codConteudo){
        getConteudoPostagem(appToken, String.valueOf(codPostagem), String.valueOf(codConteudo));
    }

    public void editConteudoPostagem(String appToken, String codPostagem, String codConteudo, ConteudoPostagem conteudoPostagem){
        Call<ResponseBody> call = this.endpointMetaModelo.editConteudoPostagem(appToken, codPostagem, codConteudo, conteudoPostagem);
        call.enqueue(new CallBackEditConteudoPostagem(this.msg, conteudoPostagem));
    }

    public void editConteudoPostagem(String appToken, long codPostagem, long codConteudo, ConteudoPostagem conteudoPostagem){
        editConteudoPostagem(appToken, String.valueOf(codPostagem), String.valueOf(codConteudo), conteudoPostagem);
    }

    public void getMedia(String codTipoPostagem, String codTipoObjetoDestino, String codObjetoDestino){
        Call<Media> call = this.endpointMetaModelo.getMedia(codTipoPostagem, codTipoObjetoDestino, codObjetoDestino);
        call.enqueue(new CallBackMedia(this.msg));
    }

    public void getMedia(long codTipoPostagem, long codTipoObjetoDestino, long codObjetoDestino){
        getMedia(String.valueOf(codTipoPostagem), String.valueOf(codTipoObjetoDestino), String.valueOf(codObjetoDestino));
    }

    public void cadastrarTipoObjeto(String appToken, TipoObjeto tipoObjeto){
        Call<ResponseBody> call = this.endpointMetaModelo.cadastrarTipoObjeto(appToken, tipoObjeto);
        call.enqueue(new CallBackTipoObjeto(this.msg, tipoObjeto));
    }
}
