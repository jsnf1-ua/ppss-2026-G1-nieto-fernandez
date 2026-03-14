package ppss;

import java.time.LocalDate;

public class AlquilaCochesTestable extends AlquilaCoches {
    private Servicio serviciodoble;

    public void setServicio(Servicio servicio) {
        this.serviciodoble = servicio;
    }

    @Override
    protected IService getIservice() {
        return serviciodoble; //para el testable.
    }


}
