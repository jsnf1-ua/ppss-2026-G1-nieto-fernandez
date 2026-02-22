package ppss;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {

    @Test
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() {
        //Arrange
        boolean[] asientos1 = {};
        int solicitados1 = 3;
        String menEsperado = "No se puede procesar la solicitud";
        Cine cin1 = new Cine();

        //Act
        ButacasException exception = Assertions.assertThrows(ButacasException.class, ()->
            cin1.reservaButacas(asientos1, solicitados1));

        //Assert
        Assertions.assertEquals(menEsperado, exception.getMessage());
    }

    @Test
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero() {
        //Arrange
        boolean[] asientos2 = {};
        int solicitados2 = 0;
        boolean resEsperado2 = false;
        Cine cin2 = new Cine();

        //Act
        boolean resReal2 = Assertions.assertDoesNotThrow(() ->
                cin2.reservaButacas(asientos2, solicitados2));

        //Assert
        Assertions.assertEquals(resEsperado2, resReal2);
    }

    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2() {
        //Arrange
        boolean[] asientos3 = {false, false, false, true, true};
        int solicitados3 = 2;
        boolean resEsperado3 = true;
        boolean[] asientosEsperados = {true, true, false, true, true};
        Cine cin3 = new Cine();

        //Act
        boolean resReal3 = Assertions.assertDoesNotThrow(() ->
            cin3.reservaButacas(asientos3, solicitados3));


        //Assert
        Assertions.assertAll("C3_dataArray debe cambiar",
                () -> Assertions.assertEquals(resEsperado3,resReal3),
                () -> Assertions.assertArrayEquals(asientosEsperados, asientos3)
        );

    }

    @Test
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1() {
        //arrange
        boolean[] asientos4 = {true, true, true};
        int solicitados4 = 1;
        boolean resEsperado4 = false;
        Cine cin4 = new Cine();

        //act
        boolean resReal4 = Assertions.assertDoesNotThrow(() ->
                cin4.reservaButacas(asientos4, solicitados4));

        //assert
        Assertions.assertEquals(resEsperado4, resReal4);
    }
}