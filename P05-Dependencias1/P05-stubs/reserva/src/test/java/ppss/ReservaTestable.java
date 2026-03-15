package ppss;

public class ReservaTestable extends Reserva{
    private boolean valor;

    public void setCompruebaPermisos(boolean valor) {
        this.valor = valor;
    }

    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        return valor;
    }


}
