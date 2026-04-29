 package ppss;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

 /* IMPORTANTE:
     Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
     vamos a usar "throws Esception" en los métodos, para que el código quede más
     legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit
     con un assertDoesNotThrow()
     Es decir, que vamos a primar la legibilidad de los tests.
     Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
     invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).

 mysql -u ppss_user -p
 ppss-2025
 USE DBUNIT;
 ******************************** PONER THROWS EXCEPTIONS ********************************
 */
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {

    String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false";
    databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
            cadena_conexionDB,
            "ppss_user",
            "ppss-2025");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-vacia.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
     //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-vacia.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_insert_should_return_exception_when_client_is_in_table() throws SQLException, Exception {
    Cliente cliente = new Cliente(1, "Pepe", "Marfil");
    cliente.setCiudad("Alicante");
    cliente.setDireccion("Gran vía 1");
    String men = "Duplicate entry '1' for key 'cliente.PRIMARY'";

    //BD
    IDataSet dataset = new FlatXmlDataFileLoader().load("/cliente-dos-registros.xml");
    databaseTester.setDataSet(dataset);
    databaseTester.onSetup();

    //SUT
    SQLException ex = Assertions.assertThrows(SQLException.class, ()->
            clienteDAO.insert(cliente));

    //Assert
    Assertions.assertEquals(men, ex.getMessage());
  }

  @Test
   public void D4_delete_should_return_exception_when_client_does_not_exist() throws Exception, SQLException {
    Cliente cliente = new Cliente(3, "Pepe3", "Jimenez");
    cliente.setDireccion("No dir");
    cliente.setCiudad("no ciudad");
    String mes = "Delete failed!";

    //DB
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-dos-registros.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //SUT
    SQLException ex = Assertions.assertThrows(SQLException.class, () ->
            clienteDAO.delete(cliente));

    //Assert
    Assertions.assertEquals(mes, ex.getMessage());
  }

  @Test
  public void D5_update_should_update_John_when_John_is_in_table() throws Exception {
    Cliente cliente = new Cliente(1, "John", "Smith");
    cliente.setCiudad("NewCity");
    cliente.setDireccion("Other Street");

    //DB
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //SUT
    Assertions.assertDoesNotThrow(() -> clienteDAO.update(cliente));

    //Recuperación datos
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");

    //Res esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro-updated.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    //Assert
    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D6_retrieve_should_return_John_when_John_is_in_table() throws Exception{
    int id = 1;

    //DB
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //SUT
    Assertions.assertDoesNotThrow(() -> clienteDAO.retrieve(id));

    //Recuperación datos
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");

    //Res esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    //ASsert
    Assertion.assertEquals(expectedTable, actualTable);
  }
}
