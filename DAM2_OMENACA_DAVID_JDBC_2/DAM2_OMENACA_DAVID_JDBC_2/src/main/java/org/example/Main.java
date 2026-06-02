package org.example;

import beans.CentroForense;
import beans.InformeForense;
import beans.MuestraForense;
import dao.MuestraForenseDAOImpl;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        MuestraForenseDAOImpl muestraForenseDAO = new MuestraForenseDAOImpl();

        System.out.println("===== TEST 1: ADD SATELLITE =====");

        CentroForense centroForense = new CentroForense();
        centroForense.setId(1);

        MuestraForense muestraForense = new MuestraForense();
        muestraForense.setCodigoCaso(100);
        muestraForense.setTipoMuestra("Muestra-10");
        muestraForense.setFechaRecogida(2020-11-11);
        muestraForense.setEstadoCustodia("Custodiado");
        muestraForense.setInforme(new InformeForense());


        int insertados = muestraForenseDAO.add(muestraForense);
        System.out.println("Filas insertadas: " + insertados);


        muestraForenseDAO.findById(1);

        muestraForenseDAO.findByAgencia(1);

        muestraForenseDAO.findWithInforme(1);

        System.out.println("===== TEST 4: FIND ALL Forenses =====");
        ArrayList<MuestraForense> muestraForenses = (ArrayList<MuestraForense>) muestraForenseDAO.findAll();

        for (MuestraForense muestraForense1 : muestraForenses) {
            System.out.println(muestraForense1);
        }

        muestraForenseDAO.update(muestraForenses.set(1, muestraForense));
        
    }
}
