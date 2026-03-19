package ppss.P06;


import ppss.P06.excepciones.IsbnInvalidoException;
import ppss.P06.excepciones.SocioInvalidoException;
import ppss.P06.excepciones.JDBCException;

public interface IOperacionBO {
    public void operacionReserva(String socio, String isbn)
            throws IsbnInvalidoException, JDBCException, SocioInvalidoException;
}
