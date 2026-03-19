package ppss.P06;

import ppss.P06.excepciones.ReservaException;
import ppss.P06.excepciones.IsbnInvalidoException;
import ppss.P06.excepciones.SocioInvalidoException;
import ppss.P06.excepciones.JDBCException;
import java.util.ArrayList;

public class Reserva {
    public IOperacionBO getOperacionBO() {
        FactoriaBOs fd = new FactoriaBOs();
        return fd.getOperacionBO();
    }

    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void realizaReserva(String login, String password,
                               String socio, String [] isbns) throws ReservaException {
        ArrayList<String> errores = new ArrayList<String>();
        if(!compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)) { //D Reserva Parcial
            errores.add("ERROR de permisos");
        } else {
            IOperacionBO io = getOperacionBO();
            try {
                for(String isbn: isbns) {
                    try {
                        io.operacionReserva(socio, isbn); // D IOperacionBO total
                    } catch (IsbnInvalidoException iie) {
                        errores.add("ISBN invalido" + ":" + isbn);
                    }
                }
            } catch (SocioInvalidoException sie) {
                errores.add("SOCIO invalido");
            } catch (JDBCException je) {
                errores.add("CONEXION invalida");
            }
        }
        if (errores.size() > 0) {
            String mensajeError = "";
            for(String error: errores) {
                mensajeError += error + "; ";
            }
            throw new ReservaException(mensajeError);
        }
    }
}
