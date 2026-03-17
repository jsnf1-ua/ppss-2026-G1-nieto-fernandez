package ppss.P06;

import java.util.Random;
import ppss.P06.excepciones.ClienteWebServiceException;

public class Premio {
    private static final float PROBABILIDAD_PREMIO = 0.1f;
    public Random generador = new Random(System.currentTimeMillis());
    public ClienteWebService cliente = new ClienteWebService();

    public String compruebaPremio() {
        if(generaNumero() < PROBABILIDAD_PREMIO) { //D -> Es de la propia clase, y como no puedo predecir su res: Mock parcial.
            try {
                String premio = cliente.obtenerPremio(); //D -> Como es dep externa, mock normal.
                return "Premiado con " + premio;
            } catch(ClienteWebServiceException e) {
                return "No se ha podido obtener el premio";
            }
        } else {
            return "Sin premio";
        }
    }

    // Genera numero aleatorio entre 0 y 1
    public float generaNumero() {
        return generador.nextFloat();
    }

    /*Primero se llama a generaNumero().
    Solo si el número es menor a 0.1
     entonces se llama a cliente.obtenerPremio(),
     no se puede pedir el premio antes de saber el número
     HAy dos mocks involucrados en el mismo proceso.

     Como tengo varios mocks y el orden importa a nivel global,
     NO sirve usar .strictMock() por separado.
     Se necesita un "Director de orquesta".
     Por lo tanto, IMocksControl*/
}
