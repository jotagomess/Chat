package chat.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author jota
 */
public class Cliente {
    
    private Socket soquete;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private String nome;
    private int id;
    private boolean on;
    
    public Cliente(String endereco, int porta, String nome) throws Exception {
        super();
        this.soquete = new Socket(endereco, porta);
        this.saida = new ObjectOutputStream(this.soquete.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete.getInputStream());
        this.nome = nome;
    }
    
    public Cliente() {
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isOnline() {
        return on;
    }

    public void setOnline(boolean online) {
        this.on = online;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);
    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();
    }

    public void finalizar() throws IOException {
        this.soquete.close();
    }
}