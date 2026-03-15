package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.SocioInvalidoException;
import ppss.excepciones.JDBCException;

public interface IOperacionBO {
    public void operacionReserva(String socio, String isbn)
            throws IsbnInvalidoException, JDBCException, SocioInvalidoException;
}
