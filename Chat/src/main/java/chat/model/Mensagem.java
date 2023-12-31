package chat.model;

import java.io.Serializable;
/**
 *
 * @author jota
 */
public class Mensagem implements Serializable {
    
    public static final String CONECTAR = "CONECTAR_", DESCONECTAR = "DESCONECTAR_", ENVIAR = "ENVIAR_";
    public static final String LISTAR_GERAL = "LISTAR_GERAL", LISTAR_DM = "LISTAR_DM", LISTAR_USERS = "LISTAR_USERS";
    
    private int id;
    private String nome;
    private String texto;
    private String action;
    private int idRemetente;
    private int idDestinatario;
    
    private String destinatario;
    
    public Mensagem(String nome, String texto) {
        super();
        this.nome = nome;
        this.texto = texto;
    }
    
    public Mensagem(String nome, int id, String texto) {
        this.nome = nome;
        this.id = id;
        this.texto = texto;
    }

    public Mensagem(String nome , String texto, String destisnatario) {
        this.nome = nome;
        this.texto = texto;
        this.destinatario = destisnatario;
    }
    
    public Mensagem(int id, int remetente, String texto) {
        this.idDestinatario = 0;
        this.id = id;
        this.idRemetente = remetente;
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
        return nome+": " + texto + "\n";
    }

    public long getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(int remetente) {
        this.idRemetente = remetente;
    }

    public long getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(int destinatario) {
        this.idDestinatario = destinatario;
    }

    public String getDestinatario() {
        return destinatario;
    }
}