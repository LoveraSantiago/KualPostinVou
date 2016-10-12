package lovera.kualpostinvou.conexao.callbacks;

import java.util.List;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.utils.FactoryModelos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBackPostagens implements Callback<List<PostagemR>>{

    private final MsgFromConexaoModelo msg;

    public CallBackPostagens(MsgFromConexaoModelo msg) {
        this.msg = msg;
    }

    @Override
    public void onResponse(Call<List<PostagemR>> call, Response<List<PostagemR>> response) {
        List<PostagemR> postagens = response.body();
        if(postagens.size() > 0){

            boolean usuarioPostou = false;
            long codigoPessoa = Aplicacao.getPessoaLogada().getPessoa().getCod();
            for(PostagemR postagem : postagens){
                if(postagem.getCodAutor() == codigoPessoa){
                    usuarioPostou = true;
                    break;
                }
            }
            PostagemR postagemResult = postagens.get(0);
            postagemResult.setTipo(FactoryModelos.geradorTipo());
            this.msg.passarPostagem(postagemResult, usuarioPostou);
        }
    }

    @Override
    public void onFailure(Call<List<PostagemR>> call, Throwable t) {

    }
}
