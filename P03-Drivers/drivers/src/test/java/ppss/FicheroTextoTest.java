package ppss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {

    @Test
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist() {
        //Arrange
        String menEsperado1 = "ficheroC1.txt (No existe el archivo o el directorio)";
        FicheroTexto fich1 = new FicheroTexto();
        //Act
        FicheroException exception = Assertions.assertThrows(FicheroException.class, ()->
                fich1.contarCaracteres("ficheroC1.txt"));
        //Assert
        Assertions.assertEquals(menEsperado1, exception.getMessage());
    }

    @Test
    void C2_contarCaracteres_should_return_4_when_file_has_4_chars() {
        //Arrange
        int resEsperado2 = 4;
        String nombreFichero2 = "src/test/resources/ficheroCorrecto.txt";
        FicheroTexto fich2 = new FicheroTexto();

        //Act
        int resReal2 = Assertions.assertDoesNotThrow(() ->
                fich2.contarCaracteres(nombreFichero2));

        //Assert
        Assertions.assertEquals(resEsperado2, resReal2);
    }

    @Test
    @Tag("excluido")
    void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read() {
        Assertions.fail();
    }

    @Test
    @Tag("excluido")
    void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed() {
        Assertions.fail();
    }
}