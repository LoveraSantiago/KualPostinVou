package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.avaliacao;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.utils.Distancia;

class Controller {

    public void showDialogCadastrarTempoDeAtendimento(){
        if(validarPermissoesCadastroAtendimento()){
            if(this.jaCadastrouTempo){
                this.views.getAvTempoDialog().show(this.jaCadastrouTempo);
                PostagemR postagemTemp = (PostagemR) this.postagem;
                this.conexaoModelo.getConteudoPostagem(Aplicacao.getPessoaLogada().getToken(), postagemTemp.getCodPostagem(), postagemTemp.getConteudos().get(0).getCodConteudoPostagem());
            }
            else{
                Localizacao localizacaoAtualizada = this.helperGPS.getLocalizacaoAtualizada();
                Distancia distancia = new Distancia();
                double distanciaLocal = distancia.calcularKmDistancia(localizacaoAtualizada, this.estabelecimento);

                if(distanciaLocal < 5){
                    this.views.getAvTempoDialog().show(this.jaCadastrouTempo);
                }
                else{
                    this.views.getDialogs().showDialogDistanteEstabelecimento();
                }
            }
        }
    }

    private boolean validarPermissoesCadastroAtendimento(){
        boolean temToken = Aplicacao.getPessoaLogada().hasToken();
        boolean temLocalizacao = this.helperGPS.temLastLocation();

        if(temToken && temLocalizacao){
            this.helperGPS.ligarLocationUpdate();
            return true;
        }
        else{
            if(Aplicacao.getPessoaLogada().isServiceTokenEmAndamento()){
                this.views.getDialogs().showDialogAguardeLogin();
                return false;
            }
            else{
                this.views.getDialogs().showDialogPermissoes(temToken, temLocalizacao);
                return false;
            }
        }
    }
}
