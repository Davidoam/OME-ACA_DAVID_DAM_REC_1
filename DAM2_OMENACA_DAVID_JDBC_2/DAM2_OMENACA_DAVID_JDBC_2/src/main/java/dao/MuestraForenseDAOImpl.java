package dao;


import beans.Agencia;
import beans.DetalleSatelite;
import beans.MuestraForense;
import beans.Satelite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MuestraForenseDAOImpl extends AbstractDAO<MuestraForense> {

    private static final String AUTOR = "OMENACA_AYUSO_DAVID_DAM2";



    @Override
    public int add(Satelite satelite) {

        String sql = """
                INSERT INTO satelites 
                (nombre, orbita, peso, coste, activo, agencia_id, autor_examen)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        return executeUpdate(
                sql,
                satelite.getNombre(),
                satelite.getOrbita(),
                satelite.getPeso(),
                satelite.getCoste(),
                satelite.isActivo(),
                satelite.getAgencia().getId(),
                AUTOR
        );
    }

    @Override
    public int update(Satelite satelite) {

        String sql = """
                UPDATE satelites
                SET nombre = ?, orbita = ?, peso = ?, coste = ?, activo = ?, agencia_id = ?
                WHERE id = ?
                """;

        return executeUpdate(
                sql,
                satelite.getNombre(),
                satelite.getOrbita(),
                satelite.getPeso(),
                satelite.getCoste(),
                satelite.isActivo(),
                satelite.getAgencia().getId(),
                satelite.getId()
        );
    }

    public int updateDinamico(Satelite satelite) {

        HashMap<String, Object> campos = new HashMap<>();

        if (satelite.getNombre() != null) {
            campos.put("nombre", satelite.getNombre());
        }

        if (satelite.getOrbita() != null) {
            campos.put("orbita", satelite.getOrbita());
        }

        if (satelite.getPeso() > 0) {
            campos.put("peso", satelite.getPeso());
        }

        if (satelite.getCoste() > 0) {
            campos.put("coste", satelite.getCoste());
        }

        campos.put("activo", satelite.isActivo());

        if (satelite.getAgencia() != null) {
            campos.put("agencia_id", satelite.getAgencia().getId());
        }

        return dynamicUpdate("satelites", campos, "id", satelite.getId());
    }

    @Override
    public int delete(int id) {

        String sql = "DELETE FROM satelites WHERE id = ?";

        return executeUpdate(sql, id);
    }

    @Override
    public Satelite findById(int id) {

        Satelite satelite = null;

        String sql = """
                SELECT 
                    s.id AS satelite_id,
                    s.nombre AS satelite_nombre,
                    s.orbita,
                    s.peso,
                    s.coste,
                    s.activo,
                
                    a.id AS agencia_id,
                    a.nombre AS agencia_nombre,
                    a.pais AS agencia_pais
                
                FROM satelites s
                INNER JOIN agencias a
                    ON s.agencia_id = a.id
                WHERE s.id = ?
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                satelite = mapSateliteConAgencia(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando satélite por ID.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return satelite;
    }

    @Override
    public List<Satelite> findAll() {

        List<Satelite> satelites = new ArrayList<>();

        String sql = """
            SELECT 
                s.id AS satelite_id,
                s.nombre AS satelite_nombre,
                s.orbita,
                s.peso,
                s.coste,
                s.activo,
                s.fecha_lanzamiento,

                a.id AS agencia_id,
                a.nombre AS agencia_nombre,
                a.pais AS agencia_pais,
                a.fecha_fundacion AS agencia_fecha_fundacion

            FROM satelites s
            INNER JOIN agencias a
                ON s.agencia_id = a.id
            ORDER BY s.id
            """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                satelites.add(mapSateliteConAgencia(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando todos los satélites.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return satelites;
    }

    public List<Satelite> findByAgencia(int agenciaId) {

        List<Satelite> satelites = new ArrayList<>();

        String sql = """
                SELECT 
                    s.id AS satelite_id,
                    s.nombre AS satelite_nombre,
                    s.orbita,
                    s.peso,
                    s.coste,
                    s.activo,
                
                    a.id AS agencia_id,
                    a.nombre AS agencia_nombre,
                    a.pais AS agencia_pais
                
                FROM satelites s
                INNER JOIN agencias a
                    ON s.agencia_id = a.id
                WHERE a.id = ?
                ORDER BY s.id
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, agenciaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                satelites.add(mapSateliteConAgencia(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando satélites por agencia.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return satelites;
    }

    public Satelite findWithDetail(int id) {

        Satelite satelite = null;

        String sql = """
                SELECT 
                    s.id AS satelite_id,
                    s.nombre AS satelite_nombre,
                    s.orbita,
                    s.peso,
                    s.coste,
                    s.activo,
                
                    a.id AS agencia_id,
                    a.nombre AS agencia_nombre,
                    a.pais AS agencia_pais,
                
                    d.id AS detalle_id,
                    d.velocidad_maxima,
                    d.combustible,
                    d.vida_util,
                    d.temperatura_maxima
                
                FROM satelites s
                INNER JOIN agencias a
                    ON s.agencia_id = a.id
                INNER JOIN detalle_satelite d
                    ON d.satelite_id = s.id
                WHERE s.id = ?
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                satelite = mapSateliteConAgenciaYDetalle(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando satélite con detalle.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return satelite;
    }

    public List<Satelite> findActivosWithAgenciaAndDetalle() {

        List<Satelite> satelites = new ArrayList<>();

        String sql = """
                SELECT 
                    s.id AS satelite_id,
                    s.nombre AS satelite_nombre,
                    s.orbita,
                    s.peso,
                    s.coste,
                    s.activo,
                
                    a.id AS agencia_id,
                    a.nombre AS agencia_nombre,
                    a.pais AS agencia_pais,
                
                    d.id AS detalle_id,
                    d.velocidad_maxima,
                    d.combustible,
                    d.vida_util,
                    d.temperatura_maxima
                
                FROM satelites s
                INNER JOIN agencias a
                    ON s.agencia_id = a.id
                INNER JOIN detalle_satelite d
                    ON d.satelite_id = s.id
                WHERE s.activo = true
                ORDER BY s.id
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                satelites.add(mapSateliteConAgenciaYDetalle(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error en BONUS_QUERY_ADVANCED.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return satelites;
    }

    private Satelite mapSateliteConAgencia(ResultSet rs) throws SQLException {

        Agencia agencia = new Agencia();
        agencia.setId(rs.getInt("agencia_id"));
        agencia.setNombre(rs.getString("agencia_nombre"));
        agencia.setPais(rs.getString("agencia_pais"));

        Satelite satelite = new Satelite();
        satelite.setId(rs.getInt("satelite_id"));
        satelite.setNombre(rs.getString("satelite_nombre"));
        satelite.setOrbita(rs.getString("orbita"));
        satelite.setPeso(rs.getDouble("peso"));
        satelite.setCoste(rs.getDouble("coste"));
        satelite.setActivo(rs.getBoolean("activo"));
        satelite.setAgencia(agencia);

        return satelite;
    }

    private Satelite mapSateliteConAgenciaYDetalle(ResultSet rs) throws SQLException {

        Satelite satelite = mapSateliteConAgencia(rs);

        DetalleSatelite detalle = new DetalleSatelite();
        detalle.setId(rs.getInt("detalle_id"));
        detalle.setVelocidadMaxima(rs.getDouble("velocidad_maxima"));
        detalle.setCombustible(rs.getString("combustible"));
        detalle.setVidaUtil(rs.getInt("vida_util"));

        satelite.setDetalle(detalle);

        return satelite;
    }
}