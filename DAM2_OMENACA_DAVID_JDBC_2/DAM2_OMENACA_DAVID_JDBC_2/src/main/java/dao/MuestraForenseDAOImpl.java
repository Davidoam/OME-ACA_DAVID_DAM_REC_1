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

public class MuestraForenseDAOImpl extends AbstractDAO<MuestraForense> {

    private static final String AUTOR = "OMENACA_AYUSO_DAVID_DAM2";



    @Override
    public int add(MuestraForense muestraForense) {

        String sql = """
                INSERT INTO muestraForense 
                (codigo_caso, tipo_muestra, fecha_recogida, estado_custodia, centro_id, autor_examen)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        return executeUpdate(
                sql,
                muestraForense.getCodigoCaso(),
                muestraForense.getTipoMuestra(),
                muestraForense.getFechaRecogida(),
                muestraForense.getEstadoCustodia(),
                muestraForense.getCentroForense().getId(),
                AUTOR
        );
    }

    @Override
    public int update(MuestraForense muestraForense) {

        String sql = """
                UPDATE satelites
                SET codigo_caso = ?, tipo_muestra = ?, fecha_recogida = ?, estado_custodia = ?, centro_id = ?
                WHERE id = ?
                """;

        return executeUpdate(
                sql,
                muestraForense.getCodigoCaso(),
                muestraForense.getTipoMuestra(),
                muestraForense.getFechaRecogida(),
                muestraForense.getEstadoCustodia(),
                muestraForense.getCentroForense().getId(),
                muestraForense.getId()
        );
    }

    public int updateDinamico(MuestraForense muestraForense) {

        HashMap<String, Object> campos = new HashMap<>();

        if (muestraForense.getCodigoCaso() > 0) {
            campos.put("codigo_caso", muestraForense.getCodigoCaso());
        }

        if (muestraForense.getTipoMuestra() != null) {
            campos.put("tipo_muestra", muestraForense.getTipoMuestra());
        }

        if (muestraForense.getFechaRecogida() > 0) {
            campos.put("fecha_recogida", muestraForense.getFechaRecogida());
        }

        if (muestraForense.getEstadoCustodia() != null) {
            campos.put("coste", muestraForense.getEstadoCustodia());
        }

        campos.put("activo", satelite.isActivo());

        if (muestraForense.getAgencia() != null) {
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
    public MuestraForense findById(int id) {

        MuestraForense muestraForense = null;

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
                muestraForense = mapSateliteConAgencia(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando satélite por ID.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return muestraForense;
    }

    @Override
    public List<MuestraForense> findAll() {

        List<MuestraForense> muestraForenses = new ArrayList<>();

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
                muestraForenses.add(mapSateliteConAgencia(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando todos los satélites.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return muestraForenses;
    }

    public List<MuestraForense> findByAgencia(int agenciaId) {

        List<MuestraForense> satelites = new ArrayList<>();

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

    public MuestraForense findWithDetail(int id) {

        MuestraForense muestraForense = null;

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
                muestraForense = mapSateliteConAgenciaYDetalle(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error buscando satélite con detalle.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return muestraForense;
    }

    public List<MuestraForense> findActivosWithAgenciaAndDetalle() {

        List<MuestraForense> muestraForenses = new ArrayList<>();

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
                muestraForenses.add(mapSateliteConAgenciaYDetalle(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error en BONUS_QUERY_ADVANCED.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return muestraForenses;
    }

    private MuestraForense mapMuestraForenseConCentro(ResultSet rs) throws SQLException {

        CentroForense centroForense = new CentroForense();
        InformeForense informeForense = new InformeForense();

        centroForense.setId(rs.getInt("centroForense_id"));
        centroForense.setNombre(rs.getString("centroForense_nombre"));
        centroForense.setPais(rs.getString("centroForense_pais"));

        MuestraForense muestraForense = new MuestraForense();
        muestraForense.setId(rs.getInt("muestraForense_id"));
        muestraForense.setCodigoCaso(rs.getInt("codigo_caso"));
        muestraForense.setTipoMuestra(rs.getString("tipo_muestra"));
        muestraForense.setFechaRecogida(rs.getInt("fecha_recogida"));
        muestraForense.setEstadoCustodia(rs.getString("estado_custodia"));
        muestraForense.setCentroForense(centroForense);
        muestraForense.setInforme(informeForense);

        return muestraForense;
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