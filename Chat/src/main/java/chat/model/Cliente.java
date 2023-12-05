package chat.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author jota
 */
public class Cliente implements Serializable {
    
    private Socket soquete;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private int id;
    private String nome;
    private boolean conectado;
    
    public Cliente(String endereco, int porta, String nome) throws Exception {
        super();
        this.soquete = new Socket(endereco, porta);
        this.saida = new ObjectOutputStream(this.soquete.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete.getInputStream());
        this.nome = nome;
    }

    public Cliente(int id, String nome, boolean conectado) {
        this.id = id;
        this.nome = nome;
        this.conectado = conectado;
    }
    
    public Cliente(String nome, boolean conectado){
        this.nome = nome;
        this.conectado = conectado;
    }
    
    public int getId() {
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

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean on) {
        this.conectado = on;
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