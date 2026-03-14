package ppss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class AlquilaCochesTest {
    @Test
    public void C1_returns_ticket75_when_TURISMO20240518and10() throws MensajeException {
        //Arrange
        ServicioStub stubs1 = new ServicioStub();

        AlquilaCochesTestable sut1 = new AlquilaCochesTestable();
        sut1.setServicio(stubs1);

        CalendarioStub stubc1 = new CalendarioStub();
        sut1.calendario = stubc1;

        //Act
        Ticket resReal = sut1.calculaPrecio(
                TipoCoche.TURISMO,
                LocalDate.of(2024, 5, 18),
                10
        );

        //Assert
        Assertions.assertEquals(75.0f, resReal.getPrecio_final());
    }

    /*public void C1() {
        CalendarioStub stubc = new CalendarioStub();
        ArrayList<LocalDate> trues = new ArrayList<>(Arrays.asList(
                LocalDate.of()
        ));
        stubc.addFestivo(trues);
        ServicioStub stubs = new ServicioStub();
    }*/
    @Test
    public void C2_returns_ticket62and5_when_TURISMO20240619and7() throws MensajeException {
        ServicioStub stubs2 = new ServicioStub();
        AlquilaCochesTestable sut2 = new AlquilaCochesTestable();
        sut2.setServicio(stubs2);

        CalendarioStub stubc2 = new CalendarioStub();
        ArrayList<LocalDate> trues = new ArrayList<>(Arrays.asList(
                LocalDate.of(2024, 06, 20),
                LocalDate.of(2024, 06, 24)
        ));
        stubc2.addFestivo(trues);
        sut2.calendario = stubc2;

        Ticket resReal = sut2.calculaPrecio(
                TipoCoche.CARAVANA,
                LocalDate.of(2024,06,19),
                7
        );

        Assertions.assertEquals(62.5f, resReal.getPrecio_final());
    }
}
