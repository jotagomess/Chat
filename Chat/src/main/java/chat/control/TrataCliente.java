package chat.control;

import chat.dao.ClienteDAO;
import chat.dao.MensagemDAO;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import chat.model.Mensagem;
import chat.model.Mensagem;
import java.net.SocketException;

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
    private ClienteDAO clientDAO;
    private MensagemDAO msgDAO;

    public TrataCliente(Socket soquete_cliente, ArrayList<Socket> clientesConectados) throws Exception {
        this.mensagens = mensagens;
        this.soquete_cliente = soquete_cliente;
        
        this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
        
        this.clientesConectados = clientesConectados;
        this.clientDAO = new ClienteDAO();
        this.msgDAO = new MensagemDAO();
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
        try {
            Mensagem mensagem;
            String comand[];

            do {
                mensagem = (Mensagem) receber_mensagem();
                comand = mensagem.getAction().split(";");
                switch (comand[0]) {
                    case "CONECTAR":
                      
                    break;
                    case "DESCONECTAR":
                    
                    break;
                    case "ENVIAR":
                    
                    break;
                    case "LISTAR":
                        
                    break;
                    
                    default:
                        throw new AssertionError();
                }

            } while (!mensagem.getTexto().equals("DESCONECTAR"));
            finalizar();
        } catch (SocketException ex) {
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}