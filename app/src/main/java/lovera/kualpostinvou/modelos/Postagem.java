package lovera.kualpostinvou.modelos;

import lovera.kualpostinvou.modelos.utils.Autor;

public class Postagem {

    private Autor autor;
    private int codPostagem;
    private int codGrupoDestino;
    private int codObjetoDestino;
    private int codPessoaDestino;
    private int codTipoObjetoDestino;
    private double latitude;
    private double longitude;
    private PostagemRelacionada postagemRelacionada;
    private Tipo tipo;

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getCodPostagem() {
        return codPostagem;
    }

    public void setCodPostagem(int codPostagem) {
        this.codPostagem = codPostagem;
    }

    public int getCodGrupoDestino() {
        return codGrupoDestino;
    }

    public void setCodGrupoDestino(int codGrupoDestino) {
        this.codGrupoDestino = codGrupoDestino;
    }

    public int getCodObjetoDestino() {
        return codObjetoDestino;
    }

    public void setCodObjetoDestino(int codObjetoDestino) {
        this.codObjetoDestino = codObjetoDestino;
    }

    public int getCodPessoaDestino() {
        return codPessoaDestino;
    }

    public void setCodPessoaDestino(int codPessoaDestino) {
        this.codPessoaDestino = codPessoaDestino;
    }

    public int getCodTipoObjetoDestino() {
        return codTipoObjetoDestino;
    }

    public void setCodTipoObjetoDestino(int codTipoObjetoDestino) {
        this.codTipoObjetoDestino = codTipoObjetoDestino;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public PostagemRelacionada getPostagemRelacionada() {
        return postagemRelacionada;
    }

    public void setPostagemRelacionada(PostagemRelacionada postagemRelacionada) {
        this.postagemRelacionada = postagemRelacionada;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
