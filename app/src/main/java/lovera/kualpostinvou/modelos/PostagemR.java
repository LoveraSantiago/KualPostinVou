package lovera.kualpostinvou.modelos;

import java.util.List;

public class PostagemR extends Postagem{

    private long codAutor;
    private long codPostagem;

    public List<Conteudo> getConteudos() {
        return conteudos;
    }

    public void setConteudos(List<Conteudo> conteudos) {
        this.conteudos = conteudos;
    }

    private List<Conteudo> conteudos;

    public long getCodAutor() {
        return codAutor;
    }

    public void setCodAutor(long codAutor) {
        this.codAutor = codAutor;
    }

    public long getCodPostagem() {
        return codPostagem;
    }

    public void setCodPostagem(long codPostagem) {
        this.codPostagem = codPostagem;
    }
}
