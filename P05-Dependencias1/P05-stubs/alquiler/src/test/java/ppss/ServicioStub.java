package ppss;

public class ServicioStub extends Servicio{
    private float precio = 10.0f;

    public void setPrecio(float precio) { //metodo por si se quiere cambiar aunq valgan todos 10.
        this.precio = precio;
    }

    @Override
    public float consultaPrecio(TipoCoche tipo) {
        return precio;
    }
}
