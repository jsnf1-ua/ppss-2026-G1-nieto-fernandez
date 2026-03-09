package ppss.P05.llamadas;

public class GestorLlamadasTestable extends GestorLlamadas {
    Calendario cal;

    public GestorLlamadasTestable(Calendario stub) {
        this.cal = stub;
    }
    @Override
    public Calendario getCalendario() {
        return cal;
    }
}
