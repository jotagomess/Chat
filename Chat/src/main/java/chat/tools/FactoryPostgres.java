package chat.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jota
 */
public class FactoryPostgres {
    private static Connection c;
    
    public static Connection getConexaoPostgres() {
        if(c == null) {
            try {
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Houve um erro na conexão!", "Falha na conexão", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return c;
    }
}