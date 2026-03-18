package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.P06.excepciones.FailedNotifyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotifyCenterTest {
    @Test
    public void C1_should_return_Failuresduringsendingprocess_when_nottis2and3_fail() throws FailedNotifyException {
        //Arrange
        IMocksControl ctrl1 = EasyMock.createStrictControl();

        String resEsperado1 = "Failures during sending process";
        //el servidor se obtiene llamando a getServer(). Si no se  hace un mock de ese metodo, la
        // clase creará un servidor de verdad y nunca usará el mockMail1.
        NotifyCenter mockNotiParcial1 = EasyMock.partialMockBuilder(NotifyCenter.class).addMockedMethod("getServer").addMockedMethod("sendNotify").addMockedMethod("getToday").mock(ctrl1);
        MailServer mockMail1 = ctrl1.mock(MailServer.class);
        //LocalDate mockDate1 = ctrl1.mock(LocalDate.class);

        LocalDate fecha1 = LocalDate.of(2026,3,23);
        List<String> mens = List.of("email1", "email2", "email3", "email4");


        EasyMock.expect(mockNotiParcial1.getServer()).andReturn(mockMail1);
        EasyMock.expect(mockNotiParcial1.getToday()).andReturn(fecha1);
        EasyMock.expect(mockMail1.findMailItemsWithDate(fecha1)).andReturn(mens);

        mockNotiParcial1.sendNotify("email1");

        mockNotiParcial1.sendNotify("email2");
        EasyMock.expectLastCall().andThrow(new FailedNotifyException("Failures during sending process"));

        mockNotiParcial1.sendNotify("email3");
        EasyMock.expectLastCall().andThrow(new FailedNotifyException("Failures during sending process"));

        mockNotiParcial1.sendNotify("email4");

        ctrl1.replay();

        //Act
        FailedNotifyException resReal1 = Assertions.assertThrows(FailedNotifyException.class, () -> {
            mockNotiParcial1.notifyUsers(fecha1);
        });

        //Assert
        Assertions.assertEquals(resEsperado1, resReal1.getMessage());

        ctrl1.verify();
    }

    @Test
    public void C2_shoul_return_Dateerror_when_fecha_is_wrong() {
        //Arrange
        String resEsperado2 = "Date error";
        LocalDate fecha2 = LocalDate.of(2026,2,2);
        LocalDate fechaparametro = LocalDate.of(2026, 3,20);
        IMocksControl ctrl2 = EasyMock.createStrictControl();

        NotifyCenter mockNotiParc2 = EasyMock.partialMockBuilder(NotifyCenter.class).addMockedMethod("getServer").addMockedMethod("getToday").mock(ctrl2);
        MailServer mockMail2 = ctrl2.mock(MailServer.class);

        EasyMock.expect(mockNotiParc2.getServer()).andReturn(mockMail2);
        EasyMock.expect(mockNotiParc2.getToday()).andReturn(fecha2);
        //EasyMock.expect(mockMail2.findMailItemsWithDate(fechaparametro)).andThrow(new FailedNotifyException("Date error")); No se llega a ejecutar, da error!

        ctrl2.replay();

        //Act
        FailedNotifyException resReal2 = Assertions.assertThrows(FailedNotifyException.class, () -> {
            mockNotiParc2.notifyUsers(fechaparametro);
        });

        //Assert
        Assertions.assertEquals(resEsperado2, resReal2.getMessage());

        ctrl2.verify();
    }

    @Test
    public void C3_should_return_nothing_when_no_pending_mess() {
        //arrange
        LocalDate fecha3 = LocalDate.of(2026, 3, 7);
        List<String> emails3 = new ArrayList<>();

        IMocksControl ctrl3 = EasyMock.createStrictControl();

        MailServer mockMail3 = ctrl3.mock(MailServer.class);
        NotifyCenter mockNotiParcial3 = EasyMock.partialMockBuilder(NotifyCenter.class).addMockedMethod("getServer").addMockedMethod("getToday").addMockedMethod("sendNotify").mock(ctrl3);

        Assertions.assertDoesNotThrow(() -> {
            EasyMock.expect(mockNotiParcial3.getServer()).andReturn(mockMail3);
            EasyMock.expect(mockNotiParcial3.getToday()).andReturn(fecha3);
            EasyMock.expect(mockMail3.findMailItemsWithDate(fecha3)).andReturn(emails3);
        });

        ctrl3.replay();

        //Act y assert
        Assertions.assertDoesNotThrow(() ->
                mockNotiParcial3.notifyUsers(fecha3));

        //assert nohay?

        ctrl3.verify();
    }
}
