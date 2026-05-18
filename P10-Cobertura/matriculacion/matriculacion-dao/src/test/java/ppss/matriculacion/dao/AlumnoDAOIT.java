package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import ppss.matriculacion.dao.JDBCAlumnoDAO;

import org.junit.jupiter.api.*;
import ppss.matriculacion.to.AlumnoTO;

import java.sql.SQLException;
import java.time.LocalDate;

public class AlumnoDAOIT {
    private IAlumnoDAO alumnoDAO;
    private IDatabaseTester databaseTester;
    private IDatabaseConnection databaseconn;

    @BeforeEach
    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(
                "com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/matriculacion?useSSL=false", "ppss_user", "ppss-2025");

        databaseconn = databaseTester.getConnection();
        alumnoDAO = new FactoriaDAO().getAlumnoDAO();
    }

    @Tag("Integracion-Fase1")
    @Test
    public void A1_addAlumno_should_add_Elena_when_Elena_is_not_in_table() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Aguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985, 2, 22));

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //SUT
        Assertions.assertDoesNotThrow(() -> alumnoDAO.addAlumno(alumno));

        //Recuperar datos
        IDataSet databaseDataSet = databaseconn.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //Res esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla-esperada-testA1.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        //Assert
        Assertion.assertEquals(expectedTable, actualTable); //NO ES ASSERTIONS SINO ASSERTION EN SINGULAR *************************************************************
    }

    @Tag("Integracion-Fase1")
    @Test
    public void A2_addAlumno_shoud_return_error_when_no_connection_with_DB() throws  Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));
        String men = "Error al conectar con BD";

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //SUT
        DAOException ex = Assertions.assertThrows(DAOException.class, () ->
                alumnoDAO.addAlumno(alumno));

        //Assert
        Assertions.assertEquals(men, ex.getMessage());
    }

    @Tag("Integracion-Fase1")
    @Test
    public void A3_addAlumno_shoud_return_error_when_name_is_null() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNombre(null);
        alumno.setNif("44444444D");
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));
        String men = "Error al conectar con BD";

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //SUT
        DAOException ex = Assertions.assertThrows(DAOException.class, () ->
                alumnoDAO.addAlumno(alumno));

        //Assert
        Assertions.assertEquals(men, ex.getMessage());
    }

    @Tag("Integracion-Fase1")
    @Test
    public void A4_addAlumno_should_return_Error_when_alumno_null() throws Exception {
        AlumnoTO alumno = null;

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
        String men = "Alumno nulo";

        //SUT
        DAOException ex = Assertions.assertThrows(DAOException.class, () ->
                alumnoDAO.addAlumno(alumno));

        //ASsert
        Assertions.assertEquals(men, ex.getMessage());
    }

    @Tag("Integracion-Fase1")
    @Test
    public void A5_addAlumno_should_return_Error_when_nif_null() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNombre("Pedro Garcia Lopez");
        alumno.setNif(null);
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));
        String men = "Error al conectar con BD";

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //SUT
        DAOException ex = Assertions.assertThrows(DAOException.class, ()->
                alumnoDAO.addAlumno(alumno));

        //Assert
        Assertions.assertEquals(men, ex.getMessage());
    }

    @Tag("Integracion-Fase1")
    @Test
    public void B1_delAlumno_should_return_tabla_esperada_when_nif_exists() throws Exception {
        String nif = "11111111A";

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //SUT
        Assertions.assertDoesNotThrow(() -> alumnoDAO.delAlumno(nif));

        //recojo datos
        IDataSet databaseDataSet = databaseconn.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //Res esperadp
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla-esperada-testB1.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        //Assert
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Tag("Integracion-Fase1")
    @Test
    public void B2_delAlumno_should_return_Error_when_nif_not_in_table() throws Exception {
        String nif = "33333333C";
        String men = "No se ha borrado ningun alumno";

        //DB
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //SUT
        DAOException ex = Assertions.assertThrows(DAOException.class, ()->
                alumnoDAO.delAlumno(nif));

        //Assert
        Assertions.assertEquals(men, ex.getMessage());
    }
}
