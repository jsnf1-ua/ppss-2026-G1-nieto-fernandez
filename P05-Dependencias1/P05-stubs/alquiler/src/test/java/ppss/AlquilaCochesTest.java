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

    @Test
    public void C3_returns_3err_when_1821and22() throws MensajeException {
        AlquilaCochesTestable sut3 = new AlquilaCochesTestable();

        CalendarioStub stubc3 = new CalendarioStub();
        ArrayList<LocalDate> errs = new ArrayList<>(Arrays.asList(
                LocalDate.of(2024,04,18),
                LocalDate.of( 2024,04,21),
                LocalDate.of( 2024,04,22)
        ));
        stubc3.addErrs(errs);
        sut3.calendario = stubc3;

        ServicioStub stubs3 = new ServicioStub();
        sut3.setServicio(stubs3);

        MensajeException resReal3 = Assertions.assertThrows(MensajeException.class, () -> {
            sut3.calculaPrecio (
                            TipoCoche.TURISMO,
                            LocalDate.of(2024,04,17),
                            8
            );
        });

        String resEsperado3 = "Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ";

        Assertions.assertEquals(resEsperado3, resReal3.getMessage());
    }
}
