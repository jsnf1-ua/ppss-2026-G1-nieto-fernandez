package ppss;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioStub extends Calendario {
    private ArrayList<LocalDate> trues = new ArrayList<>();
    private ArrayList<LocalDate> errs = new ArrayList<>();

    public void addFestivo(ArrayList<LocalDate> fechas) {
        trues.addAll(fechas);
    }

    public void addErrs (ArrayList<LocalDate> fechas) {
        trues.addAll(errs);
    }

    @Override
    public boolean es_festivo(LocalDate otroDia) throws CalendarioException {
        if(errs.contains(otroDia)) {
            throw new CalendarioException("Error en dia: " + otroDia);
        }

        return trues.contains(otroDia);
    }
}
