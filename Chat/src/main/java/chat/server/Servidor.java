package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author jota
 */
public class Servidor {
    private ServerSocket soquete_servidor;
    
    public Servidor(int porta) throws Exception {
        super();
        this.soquete_servidor = new ServerSocket(porta);
    }
    
    public void finalizar() throws IOException {
        this.soquete_servidor.close();
    }

    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor(15500);
        Socket soqueteCliente = null;
        
        while (true) {
            try {
                soqueteCliente = servidor.soquete_servidor.accept();
                new Thread(new TrataCliente(soqueteCliente)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}