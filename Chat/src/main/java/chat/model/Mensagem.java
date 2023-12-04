package chat.model;

import java.io.Serializable;
/**
 *
 * @author jota
 */
public class Mensagem implements Serializable {
    
    private int id;
    private String nome;
    private String texto;
    private String action;
    private int remetente;
    private int destinatario;
    
    public Mensagem(String nome, int id, String texto) {
        super();
        this.nome = nome;
        this.id = id;
        this.texto = texto;
    }

    public Mensagem(int id , String texto, int remetente, int destinatario) {
        this.id = id;
        this.destinatario = destinatario;
        this.remetente = remetente;
        this.texto = texto;
    }

    public Mensagem(int id, int remetente, String texto) {
        this.destinatario = 0;
        this.id = id;
        this.remetente = remetente;
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    @Override
    public String toString() {
        return nome+": " + texto + "\n\n";
    }

    public long getRemetente() {
        return remetente;
    }

    public void setRemetente(int remetente) {
        this.remetente = remetente;
    }

    public long getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(int destinatario) {
        this.destinatario = destinatario;
    }
    
}