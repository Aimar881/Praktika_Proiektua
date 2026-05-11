package org.example.noten.util;

import javax.persistence.EntityManager;

public class TestConexion {

    public static void main(String[] args) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            System.out.println("✅ Conexión con la base de datos OK");
            em.close();
            JPAUtil.close();
        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}