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
            campos.put("estado", muestraForense.getEstadoCustodia());
        }

        if (muestraForense.getCentroForense() != null) {
            campos.put("centro_id", muestraForense.getCentroForense().getId());
        }

        if (muestraForense.getInforme() != null) {
            campos.put("informe_id", muestraForense.getInforme().getId());
        }

        return dynamicUpdate("satelites", campos, "id", muestraForense.getId());
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
                                                                                        m.id AS muestra_id,
                                                                                        m.codigo_caso,
                                                                                        m.tipo_muestra,
                                                                                        m.fecha_recogida,
                                                                                        m.estado_custodia,
                                                                                    
                                                                                        c.id AS centro_id,
                                                                                        c.nombre AS agencia_nombre,
                                                                                        c.pais AS agencia_pais,
                                                                                        c.nivel_seguridad,
                                                                                    
                                                                                    FROM MUESTRAS_FORENSES m
                                                                                    INNER JOIN CENTROS_FORENSES c
                                                                                        ON m.centro_id = c.id;
                                                                  
                WHERE m.id = ?
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                muestraForense = mapMuestraForenseConCentro(rs);
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
                                m.id AS muestra_id,
                                m.codigo_caso,
                                m.tipo_muestra,
                                m.fecha_recogida,
                                m.estado_custodia,
                            
                                c.id AS centro_id,
                                c.nombre AS agencia_nombre,
                                c.pais AS agencia_pais,
                                c.nivel_seguridad,
                            
                            FROM MUESTRAS_FORENSES m
                            INNER JOIN CENTROS_FORENSES c
                                ON m.centro_id = c.id;
                ORDER BY m.id
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                muestraForenses.add(mapMuestraForenseConCentro(rs));
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
                    m.id AS muestra_id,
                    m.codigo_caso,
                    m.tipo_muestra,
                    m.fecha_recogida,
                    m.estado_custodia,
                                
                    c.id AS centro_id,
                    c.nombre AS agencia_nombre,
                    c.pais AS agencia_pais,
                    c.nivel_seguridad,
                                
                FROM MUESTRAS_FORENSES m
                INNER JOIN CENTROS_FORENSES c
                    ON m.centro_id = c.id;
                    
                WHERE c.id = ?
                ORDER BY m.id
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, agenciaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                satelites.add(mapMuestraForenseConCentro(rs));
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

    public MuestraForense findWithInforme(int id) {

        MuestraForense muestraForense = null;

        String sql = """
                SELECT
                                               m.id AS muestra_id,
                                               m.codigo_caso,
                                               m.tipo_muestra,
                                               m.fecha_recogida,
                                               m.estado_custodia,
                                           
                                               c.id AS centro_id,
                                               c.nombre AS agencia_nombre,
                                               c.pais AS agencia_pais,
                                               c.nivel_seguridad,
                                           
                                               i.id AS informe_id,
                                               i.adn_positivo,
                                               i.nivel_riesgo,
                                               i.conclusion
                                           
                                           FROM MUESTRAS_FORENSES m
                                           INNER JOIN CENTROS_FORENSES c
                                               ON m.centro_id = c.id
                                           INNER JOIN INFORMES_FORENSES i
                                               ON i.muestra_id = m.id;
                WHERE s.id = ?
                """;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                muestraForense = mapMuestraConCentroYInforme(rs);
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

    private MuestraForense mapMuestraConCentroYInforme(ResultSet rs) throws SQLException {

        MuestraForense satelite = mapMuestraForenseConCentro(rs);

        InformeForense informeForense = new InformeForense();
        informeForense.setId(rs.getInt("informe_id"));
        informeForense.setAdnPositivo(rs.getBoolean("adn_positivo?"));
        informeForense.setNivelRiesgo(rs.getInt("nivel_riesgo"));
        informeForense.setConclusion(rs.getString("conclusion"));

        satelite.setInforme(informeForense);

        return satelite;
    }
}