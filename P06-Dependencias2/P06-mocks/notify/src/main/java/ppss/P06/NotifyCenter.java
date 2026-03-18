package ppss.P06;

import java.time.LocalDate;
import java.util.List;
import ppss.P06.excepciones.FailedNotifyException;

public class NotifyCenter {
    private String login = "root";
    private String password = "7l65a43";
    public MailServer getServer() {
        return new MailServer(login, password);
    }

    public void sendNotify(String email) throws FailedNotifyException{
        throw new UnsupportedOperationException("Not supported yet");
    }

    public LocalDate getToday() {
        return LocalDate.now();
    }

    public void notifyUsers(LocalDate fecha) throws FailedNotifyException {
        int failed = 0;
        MailServer server = getServer();
        List<String> emails;
        LocalDate today = getToday(); //D REFACTOR
        if (fecha.isBefore(today) || fecha.isEqual(today)) {
            emails = server.findMailItemsWithDate(fecha); //D toal
            for (String email : emails) {
                try {
                    sendNotify(email); //D parcial
                } catch (FailedNotifyException ex) {
                    failed++;
                }
            }
        } else {
            throw new FailedNotifyException("Date error");
        }
        if (failed >0) {
            throw new FailedNotifyException("Failures during sending process");
        }
    }
}
