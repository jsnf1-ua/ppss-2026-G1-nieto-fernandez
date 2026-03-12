package ppss;

public class OperacionStub extends Operacion{

    @Override
    public void compruebaMatricula(String dni, String matricula) throws AsignaturaIncorrectaException, AsignaturaCursadaException{
        if(matricula.equals("YYY") || matricula.equals("ZZ")) {
            throw new AsignaturaIncorrectaException();
        }

        if(dni.equals("0000000T") && (matricula.equals("P1") || matricula.equals("ADA") || matricula.equals("FFI"))) {
            throw new AsignaturaCursadaException();
        }
    }
}
