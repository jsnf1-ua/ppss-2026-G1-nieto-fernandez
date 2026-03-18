package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.P06.excepciones.ClienteWebServiceException;

public class PremioTest {
    @Test
    public void CA_should_return_PremiadoconentradafinalChampions_when_random_is_0_07() {
        //Arrange
        String resEsperado1 = "Premiado con entrada final Champions";
        IMocksControl ctrl1 = EasyMock.createStrictControl();
        ClienteWebService mockCli1 = ctrl1.mock(ClienteWebService.class);
        Premio mockPremioParc1 = EasyMock.partialMockBuilder(Premio.class).addMockedMethod("generaNumero").mock(ctrl1);

        EasyMock.expect(mockPremioParc1.generaNumero()).andReturn(0.07f);
        Assertions.assertDoesNotThrow(() ->
                EasyMock.expect(mockCli1.obtenerPremio()).andReturn("entrada final Champions"));

        ctrl1.replay();

        mockPremioParc1.cliente = mockCli1;

        //ACt
        String resReal1 = mockPremioParc1.compruebaPremio();

        //Assert
        Assertions.assertEquals(resEsperado1, resReal1);

        ctrl1.verify();
    }

    @Test
    public void CB_should_return_Nosehapodidoobtenerelpremio_when_random_is_0_05_and_exception_is_thrown() {
        //Arrange
        String resEspereado2 = "No se ha podido obtener el premio";
        IMocksControl ctrl2 = EasyMock.createStrictControl();
        ClienteWebService mockCli2 = ctrl2.mock(ClienteWebService.class);

        Premio mockPremioParc2 = EasyMock.partialMockBuilder(Premio.class).addMockedMethod("generaNumero").mock(ctrl2);

        EasyMock.expect(mockPremioParc2.generaNumero()).andReturn(0.05f);

        EasyMock.expect(mockCli2.obtenerPremio()).andThrow(new ClienteWebServiceException());

        ctrl2.replay();

        mockPremioParc2.cliente = mockCli2;

        //Act
        String resReal2 = mockPremioParc2.compruebaPremio();

        //Assert
        Assertions.assertEquals(resEspereado2, resReal2);

        ctrl2.verify();
    }

    @Test
    public void CC_should_return_Sinpremio_when_0_48() {
        //Arrange
        String resEsperado3 = "Sin premio";

        IMocksControl ctrl3 = EasyMock.createStrictControl();

        ClienteWebService mockCli3 = ctrl3.mock(ClienteWebService.class);
        Premio mockPremioPrarc3 = EasyMock.partialMockBuilder(Premio.class).addMockedMethod("generaNumero").mock(ctrl3);

        EasyMock.expect(mockPremioPrarc3.generaNumero()).andReturn(0.48f);

        //Assertions.assertDoesNotThrow(() -> //ESTO NO Q NO SE LLAMA!
        //        EasyMock.expect(mockCli3.obtenerPremio()).andReturn("nada"));

        ctrl3.replay();

        mockPremioPrarc3.cliente = mockCli3;

        //Act
        String resReal3 = mockPremioPrarc3.compruebaPremio();

        //assert
        Assertions.assertEquals(resEsperado3, resReal3);

        ctrl3.verify();
    }
}
