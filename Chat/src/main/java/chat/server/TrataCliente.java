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

    private static final String SUCESSO = "SUCESSO", FALHA = "ERRO";

    private Socket clienteSocket;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ClienteDAO clienteDAO;
    private MensagemDAO mensagemDAO;
    private int id;

    public TrataCliente(Socket soquete_cliente) throws Exception {
        this.clienteSocket = soquete_cliente;
        this.saida = new ObjectOutputStream(this.clienteSocket.getOutputStream());
        this.entrada = new ObjectInputStream(this.clienteSocket.getInputStream());
        this.clienteDAO = new ClienteDAO();
        this.mensagemDAO = new MensagemDAO();
    }

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);
    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();
    }

    public void finalizar() throws IOException {
        this.clienteSocket.close();
    }

    @Override
    public void run() {
        try {
            Mensagem mensagem;
            String operacao[];

            do {
                mensagem = (Mensagem) receber_mensagem();
                operacao = mensagem.getAction().split("_");
                switch (operacao[0]) {
                    case "CONECTAR":
                        conectar(mensagem);
                        break;

                    case "ENVIAR":
                        enviar(mensagem);
                        break;

                    case "LISTAR":
                        listar(mensagem, operacao);
                        break;

                    case "DESCONECTAR":
                        desconectar(mensagem);
                        break;
                    default:
                        throw new AssertionError();
                }

            } while (!mensagem.getAction().equals(Mensagem.DESCONECTAR));
            finalizar();
        } catch (SocketException ex) {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void conectar(Mensagem mensagem) throws Exception {
        Cliente cliente = clienteDAO.getByName(mensagem.getNome());

        if (cliente == null) {
            cliente = new Cliente(mensagem.getNome(), true);
            if (clienteDAO.insert(cliente)) {
                this.id = cliente.getId();
                enviar_mensagem(SUCESSO);
            } else {
                enviar_mensagem(FALHA);
            }
        } else {
            if (cliente.isConectado()) {
                enviar_mensagem(FALHA);
            } else {
                cliente.setConectado(true);
                clienteDAO.setOnline(cliente);
                this.id = cliente.getId();
                enviar_mensagem(SUCESSO);
            }
        }
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

    private void listar(Mensagem mensagem, String operacao[]) {

    }

    private void desconectar(Mensagem mensagem) {
        Cliente cliente = clienteDAO.getByName(mensagem.getNome());
        cliente.setConectado(false);
        clienteDAO.setOnline(cliente);
    }
}
