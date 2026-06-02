package dao;

import beans.CentroForense;
import beans.InformeForense;
import beans.MuestraForense;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CentroForenseDAOImpl extends AbstractDAO<CentroForense> {

    private static final String AUTOR = "OMENACA_AYUSO_DAVID_DAM2";

    @Override
    public int add(CentroForense centroForense) {

        String sql = """
                INSERT INTO centroForense (nombre, pais, nivel_seguridad, autor_examen)
                VALUES (?, ?, ?, ?)
                """;

        return executeUpdate(
                sql,
                centroForense.getNombre(),
                centroForense.getPais(),
                AUTOR
        );
    }

    @Override
    public int update(CentroForense centroForense) {

        String sql = """
                UPDATE centroForense
                SET nombre = ?, pais = ?, fecha_fundacion = ?
                WHERE id = ?
                """;

        return executeUpdate(
                sql,
                centroForense.getNombre(),
                centroForense.getPais(),
                centroForense.getId()
        );
    }

    public int updateDinamico(CentroForense centroForense) {

        HashMap<String, Object> campos = new HashMap<>();

        if (centroForense.getNombre() != null) {
            campos.put("nombre", campos.get(centroForense.getNombre()));
        }

        if (centroForense.getPais() != null) {
            campos.put("pais", campos.get(centroForense.getPais()));
        }

        return dynamicUpdate("centro_forense", campos, "id", centroForense.getId());
    }

    @Override
    public int delete(int id) {

        String sql = "DELETE FROM agencias WHERE id = ?";

        return executeUpdate(sql, id);
    }

    @Override
    public CentroForense findById(int id) {

        CentroForense centroForense = null;

        String sql = """
                SELECT id, nombre, pais
                FROM centroForense
                WHERE id = ?
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                centroForense = mapCentroForense(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando agencia por ID.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return centroForense;
    }

    @Override
    public List<CentroForense> findAll() {

        List<CentroForense> centroForenses = new ArrayList<>();

        String sql = """
                SELECT id, nombre, pais
                FROM centroForense
                ORDER BY id
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                centroForenses.add(mapCentroForense(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando todas las agencias.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return centroForenses;
    }

    private CentroForense mapCentroForense(ResultSet rs) throws SQLException {

        CentroForense centroForense = new CentroForense();

        centroForense.setId(rs.getInt("id"));
        centroForense.setNombre(rs.getString("nombre"));
        centroForense.setPais(rs.getString("pais"));

        return centroForense;
    }
}
