package ppss.matriculacion.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class AlumnoDAOTest { //con mvn test solo ejecuta AlumnoDAOTest pq  test solo lanza surefire osea unitarios ignoorando los it.

    @Tag("Integracion-Fase1")
    @Test
    public void test1() {
        Assertions.assertTrue(true);
    }

    @Tag("Integracion-Fase1")
    @Test
    public void test2() {
        Assertions.assertTrue(true);
    }

    @Tag("Integracion-Fase1")
    @Test
    public void test3() {
        Assertions.assertTrue(true);
    }
}
