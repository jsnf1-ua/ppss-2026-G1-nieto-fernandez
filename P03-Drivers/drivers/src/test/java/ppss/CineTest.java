package ppss;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {

    @Test
    @Tag("no_parametrizado")
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
    @Tag("no_parametrizado")
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
    @Tag("no_parametrizado")
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

    @Tag("parametrizado")
    @DisplayName("reservaButacas_")
    @ParameterizedTest(name = "reservaButacas_[{index}] {3}")
    @MethodSource("casosDePrueba5")
    void C5_reservaButacas(boolean[] asientos, int solicitados, boolean resEsperado5, String mensaje) throws ButacasException{
        //Arrange
        Cine cin5 = new Cine();

        //Act
        boolean resReal5 = cin5.reservaButacas(asientos, solicitados);

        //Assert
        Assertions.assertEquals(resEsperado5, resReal5, mensaje);
    }

    private static Stream<Arguments> casosDePrueba5() {
        return Stream.of(
                Arguments.of(new boolean[]{}, 0, false, "should be false when we want 0 and fila has no seats"),
                Arguments.of(new boolean[]{false, false, false, true, true}, 2, true, "should be true when we want 2 and there are 2 free seats"),
                Arguments.of(new boolean[]{true, true, true}, 1, false, "should be false when we want 1 and all seats are already reserved")
        );
    }

    @Tag("parametrizado")
    @Tag("tablaB")
    @ParameterizedTest(name = "reservaButacas_[{index}] {3}")
    @MethodSource("casosDePrueba6")
    void C6_reservaButacas(boolean[] asientos, int solicitados, boolean resEsperado6, String mensaje) throws ButacasException {
        //Arrange
        Cine cin6 = new Cine();

        //Act
        boolean resReal6 = cin6.reservaButacas(asientos, solicitados);

        //Assert
        Assertions.assertEquals(resEsperado6, resReal6, mensaje);
    }

    private static Stream<Arguments> casosDePrueba6() {
        return Stream.of(
                Arguments.of(new boolean[]{false, false, false}, 1, true, "should be true when we want 1 and there are 3 free seats"),
                Arguments.of(new boolean[]{true}, 1, false, "should be false when we want 1 and all seats are already reserved")

        );
    }
}