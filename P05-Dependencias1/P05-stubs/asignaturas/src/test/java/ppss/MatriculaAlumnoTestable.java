package ppss;

public class MatriculaAlumnoTestable extends MatriculaAlumno {
    Operacion operacion;

    public MatriculaAlumnoTestable(Operacion stub) {
        this.operacion = stub;
    }

    @Override
    protected Operacion getOperacion() {
        return operacion;
    }
}
