package ppss.P06.excepciones;

public class IsbnInvalidoException extends RuntimeException {
    public IsbnInvalidoException(String message) {
        super(message);
    }
}
