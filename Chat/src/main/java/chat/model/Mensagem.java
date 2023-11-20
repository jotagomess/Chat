package chat.model;

import java.io.Serializable;
/**
 *
 * @author jota
 */
public class Mensagem implements Serializable {

    private String nome;
    private String texto;
    private String tipo;

    public Mensagem(String nome, String texto) {
        super();
        this.nome = nome;
        this.texto = texto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return nome+": " + texto + "\n\n";
    }

}