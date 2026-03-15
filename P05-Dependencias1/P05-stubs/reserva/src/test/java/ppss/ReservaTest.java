package ppss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;
import ppss.excepciones.ReservaException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ReservaTest {
    @Test
    public void C1_shoulf_return_esceptio1_when_notpermiso() {
        ReservaTestable sut1 = new ReservaTestable();
        sut1.setCompruebaPermisos(false);

        OperacionStub stub1 = new OperacionStub();
        ArrayList<String> isbns1 = new ArrayList<String>();
        isbns1.add("1111");
        stub1.setIsbns(isbns1);

        Factoria.setServicio(stub1);

        String [] entrada1 = {"1111"};
        String resEsperado1 = "ERROR de permisos; ";
        //Act
        ReservaException resReal1 = Assertions.assertThrows(ReservaException.class, () ->
                sut1.realizaReserva("xxxx", "xxxx", "Luis", entrada1));

        //assert
        Assertions.assertEquals(resEsperado1, resReal1.getMessage());
    }

    @Test
    public void C2_should_notexceptions_when_compruebapermisosOk() {
        ReservaTestable sut2 = new ReservaTestable();
        sut2.setCompruebaPermisos(true);

        OperacionStub stub2 = new OperacionStub();
        ArrayList<String> isbns2 = new ArrayList<>(Arrays.asList("1111", "2222"));
        stub2.setIsbns(isbns2);

        Factoria.setServicio(stub2);

        String[] entrada2 = {"1111", "2222"};
        //Act
        Assertions.assertDoesNotThrow(() ->
                sut2.realizaReserva("ppss", "ppss", "Luis", entrada2));

    }

    @Test
    public void C3_should_exception2_when_isbnswrong() throws SocioInvalidoException, JDBCException, IsbnInvalidoException {
        ReservaTestable sut3 = new ReservaTestable();
        sut3.setCompruebaPermisos(true);

        OperacionStub stub3 = new OperacionStub();
        ArrayList<String> isbns3 = new ArrayList<>(Arrays.asList("1111","2222"));
        stub3.setIsbns(isbns3);

        Factoria.setServicio(stub3);

        String [] entrada3 = {"1111", "33333", "44444"};
        String resEsperado3 =  "ISBN invalido:33333; ISBN invalido:44444; ";
        //Act
        ReservaException resReal3 = Assertions.assertThrows(ReservaException.class, () ->
                sut3.realizaReserva("ppss", "ppss", "Luis", entrada3));

        //assert
        Assertions.assertEquals(resEsperado3, resReal3.getMessage());
    }

    @Test
    public void C4_should_exception3_when_notsocio() throws SocioInvalidoException, JDBCException, IsbnInvalidoException {
        ReservaTestable sut4 = new ReservaTestable();
        sut4.setCompruebaPermisos(true);

        OperacionStub stub4 = new OperacionStub();
        ArrayList<String> isbns4 = new ArrayList<>();
        isbns4.add("1111");
        stub4.setIsbns(isbns4);
        stub4.setSoc(true);

        Factoria.setServicio(stub4);

        String [] entrada4 = {"1111"};
        String resEsperado4 = "SOCIO invalido; ";

        //Act
        ReservaException resReal4 = Assertions.assertThrows(ReservaException.class, () ->
                sut4.realizaReserva("ppss", "ppss", "Pepe", entrada4));

        //assert
        Assertions.assertEquals(resEsperado4, resReal4.getMessage());
    }

    @Test
    public void C5_should_exception4_when_noConex() throws SocioInvalidoException, JDBCException, IsbnInvalidoException {
        ReservaTestable sut5 = new ReservaTestable();
        sut5.setCompruebaPermisos(true);

        OperacionStub stub5 = new OperacionStub();
        ArrayList<String> isbns5 = new ArrayList<>();
        isbns5.add("1111");
        stub5.setIsbns(isbns5);
        stub5.setConex(true);

        Factoria.setServicio(stub5);

        String [] entrada5 = {"1111", "2222"};
        String resEsperado5 = "CONEXION invalida; ";

        //Act
        ReservaException resReal5 = Assertions.assertThrows(ReservaException.class, () ->
                sut5.realizaReserva("ppss", "ppss", "Luis", entrada5));

        //assert
        Assertions.assertEquals(resEsperado5, resReal5.getMessage());
    }
}
