package dao;

import beans.Agencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgenciaDAOImpl extends AbstractDAO<Agencia> {

    private static final String AUTOR = "OMENACA_AYUSO_DAVID_DAM2";

    @Override
    public int add(Agencia agencia) {

        String sql = """
                INSERT INTO agencias (nombre, pais, fecha_fundacion, autor_examen)
                VALUES (?, ?, ?, ?)
                """;

        return executeUpdate(
                sql,
                agencia.getNombre(),
                agencia.getPais(),
                AUTOR
        );
    }

    @Override
    public int update(Agencia agencia) {

        String sql = """
                UPDATE agencias
                SET nombre = ?, pais = ?, fecha_fundacion = ?
                WHERE id = ?
                """;

        return executeUpdate(
                sql,
                agencia.getNombre(),
                agencia.getPais(),
                agencia.getId()
        );
    }

    public int updateDinamico(Agencia agencia) {

        HashMap<String, Object> campos = new HashMap<>();

        if (agencia.getNombre() != null) {
            campos.put("nombre", agencia.getNombre());
        }

        if (agencia.getPais() != null) {
            campos.put("pais", agencia.getPais());
        }

        return dynamicUpdate("agencias", campos, "id", agencia.getId());
    }

    @Override
    public int delete(int id) {

        String sql = "DELETE FROM agencias WHERE id = ?";

        return executeUpdate(sql, id);
    }

    @Override
    public Agencia findById(int id) {

        Agencia agencia = null;

        String sql = """
                SELECT id, nombre, pais
                FROM agencias
                WHERE id = ?
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                agencia = mapAgencia(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando agencia por ID.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return agencia;
    }

    @Override
    public List<Agencia> findAll() {

        List<Agencia> agencias = new ArrayList<>();

        String sql = """
                SELECT id, nombre, pais
                FROM agencias
                ORDER BY id
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                agencias.add(mapAgencia(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando todas las agencias.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return agencias;
    }

    private Agencia mapAgencia(ResultSet rs) throws SQLException {

        Agencia agencia = new Agencia();

        agencia.setId(rs.getInt("id"));
        agencia.setNombre(rs.getString("nombre"));
        agencia.setPais(rs.getString("pais"));

        return agencia;
    }
}
