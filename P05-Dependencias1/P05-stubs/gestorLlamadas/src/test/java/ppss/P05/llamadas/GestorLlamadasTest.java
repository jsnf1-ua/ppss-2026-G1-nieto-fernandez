package ppss.P05.llamadas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GestorLlamadasTest {
    @Test
    void C1_should_return147_when_m10_h15() {
        //Arrangee
        CalendarioStub cal = new CalendarioStub();
        cal.setResult(15);
        GestorLlamadasTestable sut = new GestorLlamadasTestable(cal);
        double resEsperado = 147;

        //Arrange
        double resReal = Assertions.assertDoesNotThrow(() ->
                sut.calculaConsumo(10));

        //Assert
        Assertions.assertEquals(resEsperado, resReal);

    }

    @Test
    void C2_should_return65_when_m10_h23() {
        //Arrange
       CalendarioStub stub = new CalendarioStub();
       stub.setResult(23);
       GestorLlamadasTestable sut = new GestorLlamadasTestable(stub);
       double resEsperado2 = 65;

       //act
       double resReal2 = Assertions.assertDoesNotThrow(() ->
               sut.calculaConsumo(10));

       //assert
        Assertions.assertEquals(resEsperado2, resReal2);
    }
}
