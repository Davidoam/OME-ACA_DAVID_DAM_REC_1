package dao;


import motores.MotorFactory;
import motores.MotorSQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractDAO<T> implements DAO<T> {

    protected MotorSQL motor;

    public AbstractDAO() {
        this.motor = MotorFactory.create(MotorFactory.POSTGRE);
    }

    protected int executeUpdate(String sql, Object... params) {

        int filasAfectadas = 0;

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            filasAfectadas = ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            System.out.println("Error ejecutando actualización SQL.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return filasAfectadas;
    }

    protected int dynamicUpdate(String tableName, Map<String, Object> fields, String whereColumn, Object whereValue) {

        if (fields == null || fields.isEmpty()) {
            System.out.println("No hay campos para actualizar.");
            return 0;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName).append(" SET ");

        int contador = 0;

        for (String column : fields.keySet()) {
            sql.append(column).append(" = ?");

            contador++;

            if (contador < fields.size()) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE ").append(whereColumn).append(" = ?");

        try {
            motor.conectar();

            PreparedStatement ps = motor.getConn().prepareStatement(sql.toString());

            int index = 1;

            for (Object value : fields.values()) {
                ps.setObject(index, value);
                index++;
            }

            ps.setObject(index, whereValue);

            int filas = ps.executeUpdate();

            ps.close();

            return filas;

        } catch (SQLException e) {
            System.out.println("Error en update dinámico.");
            e.printStackTrace();
        } finally {
            motor.desconectar();
        }

        return 0;
    }
}