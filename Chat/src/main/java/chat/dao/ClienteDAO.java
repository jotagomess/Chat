package chat.dao;

import chat.tools.FactoryPostgres;
import chat.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jota
 */
public class ClienteDAO {
    private Connection c;

    public ClienteDAO() {
        this.c = FactoryPostgres.getConexaoPostgres();
    }
    
    public Cliente insert(Cliente cliente) {
        String sql = "INSERT INTO chat.clientes(nome, conectado) VALUES (?,?) returning id";
        
        try(PreparedStatement trans = this.c.prepareStatement(sql)){
            trans.setString(1, cliente.getNome());
            trans.setBoolean(2, cliente.isConectado());
            
            ResultSet resultado = trans.executeQuery();
            if(resultado.next()) {
                cliente.setId(resultado.getInt("id")+1);
                return cliente;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public ArrayList<Cliente> getWithFilter() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, conectado FROM chat.clientes WHERE conectado = true ORDER BY conectado";
        
        try(PreparedStatement trans = this.c.prepareStatement(sql)) {
            ResultSet resultado = trans.executeQuery();
            
            while (resultado.next()) {                
                Cliente cliente = new Cliente();
                cliente.setConectado(resultado.getBoolean("conectado"));
                cliente.setId(resultado.getInt("id")+1);
                cliente.setNome(resultado.getString("nome"));
                clientes.add(cliente);
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return clientes;
    }
    
    public ArrayList<Cliente> getAll() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, conectado FROM chat.clientes ORDER BY id";
        
        try(PreparedStatement trans = this.c.prepareStatement(sql)) {
            ResultSet resultado = trans.executeQuery();
            
            while (resultado.next()) {                
                Cliente cliente = new Cliente();
                cliente.setConectado(resultado.getBoolean("conectado"));
                cliente.setId(resultado.getInt("id")+1);
                cliente.setNome(resultado.getString("nome"));
                clientes.add(cliente);
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return clientes;
    }
    
    public Cliente getByName(String nome) {
        String sql = "SELECT nome, id, conectado FROM chat.clientes WHERE nome = ?";
        
        try(PreparedStatement trans = this.c.prepareStatement(sql)) {
            trans.setString(1, nome);
            
            ResultSet resultado = trans.executeQuery();
            if(resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setConectado(resultado.getBoolean("conectado"));
                cliente.setId(resultado.getInt("id")+1);
                cliente.setNome(nome);
                return cliente;
            }else {
                return null;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public Cliente getById(int id) {
        String sql = "SELECT nome, id, conectado FROM chat.clientes WHERE id = ?";
        
        try(PreparedStatement trans = this.c.prepareStatement(sql)) {
            trans.setInt(1, id);
            
            ResultSet resultado = trans.executeQuery();
            if(resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setConectado(resultado.getBoolean("conectado"));
                cliente.setId(id);
                cliente.setNome(resultado.getString("nome"));
                return cliente;
            }else {
                return null;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public boolean setConection(int id, boolean conectado) {
        String sql = "UPDATE chat.clientes SET conectado = ? WHERE id = ?";
        
        try(PreparedStatement trans = this.c.prepareStatement(sql)) {
            trans.setBoolean(1,conectado);
            trans.setInt(2, id);
            
            trans.execute();
            return true;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}