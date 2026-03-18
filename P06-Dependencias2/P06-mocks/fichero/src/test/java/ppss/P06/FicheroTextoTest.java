package ppss.P06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import ppss.P06.excepciones.FicheroException;
import java.io.IOException;

public class FicheroTextoTest {
    @Test
    public void C1_should_throw_FicheroException_when_cant_read_file() throws IOException {
        String resEsperado1 =  "src/test/resources/ficheroC1.txt (Error al leer el archivo)";
        IMocksControl ctrl1 = EasyMock.createStrictControl();

        FileReader mockFi1 = ctrl1.mock(FileReader.class);
        FicheroTexto mockParcial1 = EasyMock.partialMockBuilder(FicheroTexto.class).addMockedMethod("createFileReader").mock(ctrl1);

        String fich = "src/test/resources/ficheroC1.txt"; //en el 2 mock, al leer con el filereadeer
        Assertions.assertDoesNotThrow(() ->
                EasyMock.expect(mockParcial1.createFileReader(fich)).andReturn(mockFi1)); //andReturn(mockFi1) **********************************************************************************
        EasyMock.expect(mockFi1.read()).andThrow(new IOException()); //IOEX**************

        ctrl1.replay();

        //Act
        FicheroException resreal1 = Assertions.assertThrows(FicheroException.class, () -> {
            mockParcial1.contarCaracteres(fich);
        });

        //Assert
        Assertions.assertEquals(resEsperado1, resreal1.getMessage());

        ctrl1.verify();
    }

    @Test
    public void C2_should_throw_FicheroException_when_cosing_file() throws IOException {
        //arrange
        String resEsperado2 = "src/test/resources/ficheroC2.txt (Error al cerrar el archivo)";
        IMocksControl ctrl2 = EasyMock.createStrictControl();

        FicheroTexto mockParcial2 = EasyMock.partialMockBuilder(FicheroTexto.class).addMockedMethod("createFileReader").mock(ctrl2);
        FileReader mockFi2 = ctrl2.mock(FileReader.class);

        String fich2 = "src/test/resources/ficheroC2.txt";
        EasyMock.expect(mockParcial2.createFileReader(fich2)).andReturn(mockFi2);

        Assertions.assertDoesNotThrow(() ->
                EasyMock.expect(mockFi2.read()).andReturn(-1));

        mockFi2.close(); //como es void se llama a la funcion void y se pone la sig línea***********************************+
        EasyMock.expectLastCall().andThrow(new IOException());

        ctrl2.replay();

        //Act
        FicheroException resReal2 = Assertions.assertThrows(FicheroException.class, () -> {
                mockParcial2.contarCaracteres(fich2);
        });

        //Assert
        Assertions.assertEquals(resEsperado2, resReal2.getMessage());

        ctrl2.verify();
    }
}
