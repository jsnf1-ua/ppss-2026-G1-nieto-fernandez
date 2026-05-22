 package ppss;

import com.mysql.cj.jdbc.JdbcConnection;
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
  private ClienteDAO clienteDAO;
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {
    String cadena = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false";
    String driver = "com.mysql.cj.jdbc.Driver";
    String usuario = "ppss_user";
    String contraseña = "ppss-2025";
    databaseTester = new JdbcDatabaseTester(driver, cadena, usuario, contraseña);
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1, "John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //Iniciamo DB
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-vacia.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //SUT
    Assertions.assertDoesNotThrow(() -> clienteDAO.insert(cliente));

    //REcuperam DB y esperado
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");

    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

   @Test
   public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
     Cliente cliente =  new Cliente(1,"John", "Smith");
     cliente.setDireccion("1 Main Street");
     cliente.setCiudad("Anycity");

     //Inicializam DB
     IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
     databaseTester.setDataSet(dataSet);
     databaseTester.onSetup();

     //SUT
     Assertions.assertDoesNotThrow(() -> clienteDAO.delete(cliente));

     //Recogem datos y tabla esperada
     IDataSet databaseDataSet = connection.createDataSet();
     ITable actuaTable = databaseDataSet.getTable("cliente");

     IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-vacia.xml");
     ITable expectedTable = expectedDataSet.getTable("cliente");

     Assertion.assertEquals(expectedTable, actuaTable);
   }

   @Test
   public void D5_update_should_update_John_when_John_is_in_table() throws Exception {
     Cliente cliente = new Cliente(1, "John", "Smith");
     cliente.setCiudad("NewCity");
     cliente.setDireccion("Other Street");

     //Iniciam db
     IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
     databaseTester.setDataSet(dataSet);
     databaseTester.onSetup();

     //SUT
     Assertions.assertDoesNotThrow(() -> clienteDAO.update(cliente));

     //tabla actual res esperado
     IDataSet databaseDataSet = connection.createDataSet();
     ITable actuaTable = databaseDataSet.getTable("cliente");

     IDataSet expectedDataset = new FlatXmlDataFileLoader().load("/cliente-un-registro-updated.xml");
     ITable expectedTable = expectedDataset.getTable("cliente");

     Assertion.assertEquals(expectedTable, actuaTable);
   }

   @Test
   public void D3_insert_should_return_exception_when_client_is_in_table() throws Exception {
     Cliente cliente = new Cliente(1, "Pepe", "Marfil");
     cliente.setCiudad("Alicante");
     cliente.setDireccion("Gran vía 1");
     String menEsperado = "Duplicate entry '1' for key 'cliente.PRIMARY'";

     //DB
     IDataSet dataset = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
     databaseTester.setDataSet(dataset);
     databaseTester.onSetup();

     //SUT
     SQLException ex = Assertions.assertThrows(SQLException.class, () ->
             clienteDAO.insert(cliente));

     /*//Esperado ***********
     IDataSet databaseDataSet = connection.createDataSet();
     ITable actualTable = databaseDataSet.getTable("cliente");

     IDataSet expectedDataSet = new FlatXmlDataFileLoader("/tablita");
     ITable expectedTable = expectedDataSet.getTable("cliente");
     */
     Assertions.assertEquals(menEsperado, ex.getMessage());
   }
}
