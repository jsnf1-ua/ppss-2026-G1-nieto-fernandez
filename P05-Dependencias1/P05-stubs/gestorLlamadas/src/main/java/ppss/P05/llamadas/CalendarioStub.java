package ppss.P05.llamadas;

public class CalendarioStub extends Calendario {
    int result;

    public void setResult(int salida) {
        this.result = salida;
    }

    @Override
    public int getHoraActual() {
        return result;
    }
}
