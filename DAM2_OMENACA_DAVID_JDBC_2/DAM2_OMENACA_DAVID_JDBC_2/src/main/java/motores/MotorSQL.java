package motores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MotorSQL {

    protected Connection conn;
    protected String url;
    protected String user;
    protected String password;

    public void conectar() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión abierta correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }
}