package ppss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MatriculaAlumnoTest {
    @Test
    public void C1_should_return_exceptions_when_dni0000000asignaturasTMDZZFBDYYYP1() {
        //Arrange
        OperacionStub stub1 = new OperacionStub();
        MatriculaAlumnoTestable sut1 = new MatriculaAlumnoTestable(stub1);

        JustificanteMatricula resEsperado1 = new JustificanteMatricula();

        ArrayList<String> asignaturasEsperadas1 = new ArrayList<>();
        asignaturasEsperadas1.add("MD");
        asignaturasEsperadas1.add("FBD");
        resEsperado1.setAsignaturas(asignaturasEsperadas1);

        resEsperado1.setDni("00000000T");


        ArrayList<String> errsEsperados1 = new ArrayList<>();
        errsEsperados1.add("Asignatura ZZ no existe");
        errsEsperados1.add("Asignatura YYY no existe");
        errsEsperados1.add("Asignatura P1 ya cursada");
        resEsperado1.setErrores(errsEsperados1);

        //Act
        String[] entrada1 ={"MD", "ZZ", "FBD", "YYY", "P1"};
        JustificanteMatricula resReal1 = Assertions.assertDoesNotThrow(() ->
                sut1.validaAsignaturas("00000000T", entrada1));

        //assert
        Assertions.assertAll( //Se compara cada campo prque Los objetos no tienen equals y no se puede cambiar porque lo dice el enunciado!!!!!!!!
                () -> Assertions.assertEquals(resEsperado1.getDni(), resReal1.getDni()),
                () -> Assertions.assertEquals(resEsperado1.getAsignaturas(), resReal1.getAsignaturas()),
                () -> Assertions.assertEquals(resEsperado1.getErrores(), resReal1.getErrores())
        );
    }

    @Test
    public void C2() {
        //arrange
        OperacionStub stub2 = new OperacionStub();
        JustificanteMatricula resEsperado2 = new JustificanteMatricula();

        MatriculaAlumnoTestable sut2 = new MatriculaAlumnoTestable(stub2);

        ArrayList<String> asignaturasEsperadas2 = new ArrayList<>();
        asignaturasEsperadas2.add("PPSS");
        asignaturasEsperadas2.add("ADA");
        asignaturasEsperadas2.add("P3");

        ArrayList<String> errsEsperados2 = new ArrayList<>();

        resEsperado2.setDni("00000000T");
        resEsperado2.setAsignaturas(asignaturasEsperadas2);
        resEsperado2.setErrores(errsEsperados2);

        //Act
        String[] entrada2 = {"PPSS", "ADA", "P3"};
        JustificanteMatricula resReal2 = Assertions.assertDoesNotThrow(()->
                sut2.validaAsignaturas("00000000T", entrada2));

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(resEsperado2.getDni(), resReal2.getDni()),
                () -> Assertions.assertEquals(resEsperado2.getAsignaturas(), resReal2.getAsignaturas()),
                () -> Assertions.assertEquals(resEsperado2.getErrores(), resReal2.getErrores())
        );
    }
}
