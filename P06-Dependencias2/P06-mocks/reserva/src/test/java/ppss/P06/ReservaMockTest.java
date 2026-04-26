package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.P06.excepciones.ReservaException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReservaMockTest {

    private IMocksControl ctrl;
    private Reserva mockResParc;
    private IOperacionBO mockIO;

    @BeforeEach
    public void setUp () {
        ctrl = EasyMock.createStrictControl();
        mockIO = ctrl.mock(IOperacionBO.class);
        mockResParc = EasyMock.partialMockBuilder(Reserva.class).addMockedMethod("getOperacionBO").addMockedMethod("compruebaPermisos").mock(ctrl);
    }

    @Test
    public void C1() {
        //arrange
        String login = "xxxx";
        String pass = "ppss";
        String soc = "Pepe";
        String [] isbns = new String[0];
        String resEsperado1 = "ERROR de permisos; ";
        EasyMock.expect(mockResParc.compruebaPermisos(login, pass, Usuario.BIBLIOTECARIO)).andReturn(false);

        ctrl.replay();

        //act
        ReservaException resReal1 = Assertions.assertThrows(ReservaException.class, ()-> {
                    mockResParc.realizaReserva(login, pass, soc, isbns);
                });

        //assert
        Assertions.assertEquals(resEsperado1, resReal1.getMessage());
        ctrl.verify();
    }

    @Test
    public void C2() {
        //arrange
        String login = "xxxx";
        String pass = "ppss";
        String soc = "Pepe";
        String [] isbns = {"22222", "33333"};

        EasyMock.expect(mockResParc.compruebaPermisos(login, pass, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(mockResParc.getOperacionBO()).andReturn(mockIO);

        //act
        Assertions.assertDoesNotThrow(() -> {
            mockIO.operacionReserva(soc, "22222");
            EasyMock.expectLastCall();

            mockIO.operacionReserva(soc, "33333");
            EasyMock.expectLastCall();
        });

        ctrl.replay();
        //assert
        Assertions.assertDoesNotThrow(() -> {
            mockResParc.realizaReserva(login, pass, soc, isbns);
        });

        ctrl.verify();
    }

    @Test
    public void C3() {
        //Arrange
        String [] isbns = {"22222"};
        String login = "ppss";
        String pass = "ppss";
        String soc = "Luis";
        String resEsperado = "ERROR de permisos";
        IMocksControl ctrl = EasyMock.createStrictControl();
        Reserva mockResParcial = EasyMock.partialMockBuilder(Reserva.class).addMockedMethod("compruebaPermisos").addMockedMethod("getOperacionBO").mock(ctrl);
        IOperacionBO mocOp = ctrl.mock(IOperacionBO.class);

        EasyMock.expect(mockResParcial.compruebaPermisos(login, pass, Usuario.BIBLIOTECARIO)).andReturn(false);

        ctrl.replay();

        //Act
        Assertions.assertThrows(ReservaException.class, ()->
                        mockResParcial.realizaReserva(login, pass, soc, isbns));

        ctrl.verify();
    }
}
