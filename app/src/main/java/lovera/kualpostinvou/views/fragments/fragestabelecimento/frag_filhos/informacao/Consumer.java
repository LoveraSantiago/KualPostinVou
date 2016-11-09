package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao;

import lovera.kualpostinvou.modelos.Estabelecimento;

public class Consumer {

    private final FragFilho_Informacao fragment;

    private Estabelecimento estabelecimento;

    public Consumer(FragFilho_Informacao fragment) {
        this.fragment = fragment;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }
}
