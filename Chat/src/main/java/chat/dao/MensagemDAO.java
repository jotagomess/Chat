package chat.dao;

import chat.tools.FactoryPostgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import chat.model.Mensagem;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jota
 */
public class MensagemDAO {

    private Connection conexao;

    public MensagemDAO() {
        this.conexao = FactoryPostgres.getConexaoPostgres();
    }

    public boolean insertGlobal(Mensagem mensagem) {
        String sql = "INSERT INTO chat.global(texto, id_remetente) VALUES (?,?) returning id";
        
        try (PreparedStatement trans = conexao.prepareStatement(sql)) {
            trans.setString(1, mensagem.getTexto());
            trans.setInt(2, (int) mensagem.getRemetente());

            ResultSet result = trans.executeQuery();
            if (result.next()) {
                mensagem.setId(result.getInt("id"));
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MensagemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    public boolean insertPrivada(Mensagem mensagem) {
        String sql = "INSERT INTO chat.privada(id_remetente, id_destinatario, texto) VALUES (?, ?, ?) returning id";
        
        try (PreparedStatement trans = conexao.prepareStatement(sql)) {
            trans.setInt(1, (int) mensagem.getRemetente());
            trans.setInt(2, (int) mensagem.getDestinatario());
            trans.setString(3, mensagem.getTexto());

            ResultSet result = trans.executeQuery();
            if (result.next()) {
                mensagem.setId(result.getInt("id"));
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MensagemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    public ArrayList<Mensagem> selectGlobal() {
        ArrayList<Mensagem> mensagens = new ArrayList<>();
        String sql = "SELECT G.id, G.texto, C.nome FROM chat.global G JOIN chat.clientes C ON C.id = G.id_remetente ORDER BY id";
        
        try (PreparedStatement trans = conexao.prepareStatement(sql)) {
            ResultSet resultado = trans.executeQuery();

            while (resultado.next()) {
                Mensagem mensagem = new Mensagem(resultado.getString("C.nome"),
                        resultado.getInt("G.id"),
                        resultado.getString("G.texto"));
                mensagens.add(mensagem);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MensagemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensagens;
    }

    public ArrayList<Mensagem> selectPrivada(int id_cliente) {
        ArrayList<Mensagem> mensagens = new ArrayList<>();
        String sql = "SELECT P.id, P.texto, C.nome FROM chat.privada P JOIN clientes C ON "
                + "C.id = P.id_remetente OR C.id = P.id_destinatario WHERE C.id = ?"
                + "ORDER BY P.id";
        
        try (PreparedStatement trans = conexao.prepareStatement(sql)) {
            trans.setInt(1, id_cliente);
            
            ResultSet resultado = trans.executeQuery();
            while (resultado.next()) {
               Mensagem mensagem = new Mensagem(resultado.getString("C.nome"),
                        resultado.getInt("G.id"),
                        resultado.getString("G.texto"));
                mensagens.add(mensagem);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MensagemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensagens;
    }
}