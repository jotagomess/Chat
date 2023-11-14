package control;

import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Mensagem;
import model.Mensagem;

/**
 *
 * @author jota
 */
public class TrataCliente implements Runnable {
    
    private Socket soquete_cliente;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ArrayList<Mensagem> mensagens;
    private ArrayList<Socket> clientesConectados;

    public TrataCliente(Socket soquete_cliente, ArrayList<Socket> clientesConectados) throws Exception {
        this.soquete_cliente = soquete_cliente;
        this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
        this.mensagens = mensagens;
        this.clientesConectados = clientesConectados;
    }

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);
    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();
    }

    public void finalizar() throws IOException {
        this.soquete_cliente.close();
    }

    @Override
    public void run() {
        Mensagem mensagem;
        
        try {
            do {
                mensagem = (Mensagem) receber_mensagem();
                
                if (mensagem.getTexto().equals("sair")) {
                    break;
                }
                
            } while (mensagem.getTexto().equals("sair"));
            finalizar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}