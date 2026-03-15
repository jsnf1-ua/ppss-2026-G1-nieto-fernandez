package ppss;

public class Factoria {
    private static IOperacionBO servicio = null;

    public static IOperacionBO create() {
        if(servicio  != null) {
            return servicio;
        } else {
            return new Operacion();
        }
    }

    public static void setServicio(IOperacionBO srv) {
        servicio = srv;
    }
}
