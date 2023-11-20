package chat.model;

import java.io.Serializable;
/**
 *
 * @author jota
 */
public class Mensagem implements Serializable {

    private String nome;
    private long destinatario;
    private long rementente;
    private String texto;
    private String tipo;

    public Mensagem(String opercacao, String texto) {
        super();
        this.texto = texto;
        this.tipo = opercacao;
    }

    public Mensagem(long destinatario, long remetente, String texto) {
        this.destinatario = destinatario;
        this.rementente = remetente;
        this.texto = texto;
    }

    public Mensagem(long remetente, String texto) {
        this.destinatario = 0;
        this.rementente = remetente;
        this.texto = texto;
    }
    
    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String operacao) {
        this.tipo = operacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(long destinatario) {
        this.destinatario = destinatario;
    }

    public long getRemetente() {
        return rementente;
    }

    public void setRemetente(long remetente) {
        this.rementente = remetente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        return "[" + tipo + "] = " + texto + ";";
    }

}