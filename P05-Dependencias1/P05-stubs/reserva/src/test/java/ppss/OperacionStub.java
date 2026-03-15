package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

import java.util.ArrayList;

public class OperacionStub implements IOperacionBO{
    ArrayList<String> isbns = new ArrayList<>();
    private boolean socinv = false;
    private boolean jdbc = false;

    public void setIsbns(ArrayList<String> isbns) {
        this.isbns = isbns;
    }
    public void setSoc(boolean val) {
        this.socinv = val;
    }
    public void setConex(boolean val) {
        this.jdbc = val;
    }

    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        if(socinv) {
            throw new SocioInvalidoException("SOCIO invalido");
        }

        if(jdbc) {
            throw new JDBCException("CONEXION invalida");
        }

        if(!isbns.contains(isbn)) {
            throw new IsbnInvalidoException("ISBN invalido" + ":" + isbn);
        }
    }

}
