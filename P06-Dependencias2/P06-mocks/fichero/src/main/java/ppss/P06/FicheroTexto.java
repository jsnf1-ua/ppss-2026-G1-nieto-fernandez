package ppss.P06;

import java.io.FileReader;
import ppss.P06.excepciones.FicheroException;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FicheroTexto {
    public FileReader createFileReader(String fich) throws FileNotFoundException {
        return new FileReader(fich);
    } //FileReader mock total, es externo.
    // Mock parcial createFileReader.

    public int contarCaracteres(String nombreFichero) throws FicheroException {
        int contador = 0;
        FileReader fichero = null;
        try {
            fichero = createFileReader(nombreFichero); //D
            int i=0;
            while (i != -1) {
                i = fichero.read(); //D
                contador++;
            }
            contador--;
        } catch (FileNotFoundException e1) {
            throw new FicheroException(nombreFichero +
                    " (No existe el archivo o el directorio)");
        } catch (IOException e2) {
            throw new FicheroException(nombreFichero +
                    " (Error al leer el archivo)");
        }
        try {
            fichero.close(); //D
        } catch (IOException e) {
            throw new FicheroException(nombreFichero +
                    " (Error al cerrar el archivo)");
        }
        return contador;
    }
}
