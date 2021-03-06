package lovera.kualpostinvou.modelos;

public class Profissional {

    private String descricaoAtividadeProfissional;
    private int quantidadeProfissionais;

    public String getDescricaoAtividadeProfissional() {
        return descricaoAtividadeProfissional;
    }

    public void setDescricaoAtividadeProfissional(String descricaoAtividadeProfissional) {
        this.descricaoAtividadeProfissional = descricaoAtividadeProfissional;
    }

    public int getQuantidadeProfissionais() {
        return quantidadeProfissionais;
    }

    public void setQuantidadeProfissionais(int quantidadeProfissionais) {
        this.quantidadeProfissionais = quantidadeProfissionais;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(String.format("%3d", this.quantidadeProfissionais) + " - " + this.descricaoAtividadeProfissional )
                                  .toString();
    }
}
