package chat.server;

import chat.dao.ClienteDAO;
import chat.dao.MensagemDAO;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import chat.model.Cliente;
import chat.model.Mensagem;
import java.net.SocketException;

/**
 *
 * @author jota
 */
public class TrataCliente implements Runnable {

    private static final String SUCESSO = "SUCESSO", FALHA = "ERRO", USER_EXISTS = "USER_EXISTS";

    private Socket soquete_cliente;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ClienteDAO clienteDAO;
    private MensagemDAO mensagemDAO;
    private int id;

    public TrataCliente(Socket soquete_cliente) throws Exception {
        super();
        this.id = 0;
        this.soquete_cliente = soquete_cliente;
        this.clienteDAO = new ClienteDAO();
        this.mensagemDAO = new MensagemDAO();
        this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
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
            String operacao[];
            OUTER:
            do {
                mensagem = (Mensagem) receber_mensagem();
                operacao = mensagem.getAction().split("_");

                switch (operacao[0]) {
                    case "DESCONECTAR":
                        desconectar();
                        break OUTER;
                    
                    case "ENVIAR":
                        enviar(mensagem);
                        break;

                    case "CONECTAR":
                        conectar(mensagem);
                        break;

                    case "LISTAR":
                        listar(mensagem, operacao);
                        break;

                    default:
                        System.err.println("Erro");
                        break;
                }
            } while (!mensagem.getAction().equals(Mensagem.DESCONECTAR));
            finalizar();
        } catch (SocketException ex) {
            if (this.id != 0) {
                clienteDAO.setConection(this.id, false);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        clienteDAO.setConection(this.id, false);
    }

    private void desconectar() throws Exception {
        clienteDAO.setConection(this.id, false);
    }

    private void enviar(Mensagem mensagem) throws Exception {
        ArrayList<Cliente> clientesConectados = clienteDAO.getWithFilter();
        mensagem.setIdRemetente(this.id);

        for (Cliente c : clientesConectados) {
            if (mensagem.getIdDestinatario() != 0) {
                if (c.getNome().equals(mensagem.getDestinatario())) {
                    mensagem.setIdDestinatario(c.getId());
                    if (mensagemDAO.insertPrivada(mensagem)) {
                        c.enviar_mensagem(mensagem);
                        enviar_mensagem(SUCESSO);
                    } else {
                        enviar_mensagem(FALHA);
                    }
                }
            } else {
                if (!c.getNome().equals(mensagem.getNome())) {
                    if (mensagemDAO.insertGlobal(mensagem)) {
                        c.enviar_mensagem(mensagem);
                        enviar_mensagem(SUCESSO);
                    } else {
                        enviar_mensagem(FALHA);
                    }
                }
            }
        }
    }

    private void conectar(Mensagem mensagem) throws Exception {
        Cliente cliente = clienteDAO.getByName(mensagem.getNome());

        if (cliente == null) {
            cliente = new Cliente(mensagem.getNome(), true);
            cliente = clienteDAO.insert(cliente);
            if (cliente != null) {
                this.id = cliente.getId();
                enviar_mensagem(SUCESSO);
                System.out.println(this.id);
            } else {
                enviar_mensagem(FALHA);
            }
        } else {
            if (cliente.isConectado()) {
                enviar_mensagem(USER_EXISTS);
            } else {
                clienteDAO.setConection(this.id, true);
                this.id = cliente.getId();
                enviar_mensagem(SUCESSO);
            }
        }
    }

    private void listar(Mensagem mensagem, String operacao[]) {

    }
}