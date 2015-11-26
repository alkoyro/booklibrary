package by.es.mail;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 10.02.12
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class MailSender {
    public static final String DEFAULT_EMAIL_SENDER_ADRESS = "robot@megabooks.by";
    public static final String SUBJECT_PASSWORD_RESTORE = "Restoring user password from 'megabooks.by'";

    private static final String ES_SMTP_HOST = "192.168.0.4";
    private static final Logger log = Logger.getLogger(MailSender.class.getName());

    private String smtpHost = ES_SMTP_HOST;

    public void sendMail(String recipient, String subject, String message) {
        String[] recipients = {recipient};
        sendMail(recipients, subject, message, DEFAULT_EMAIL_SENDER_ADRESS);
    }

    public void sendMail(String recipient, String subject, String message, String senderAddress) {
        String[] recipients = {recipient};
        sendMail(recipients, subject, message, senderAddress);
    }

    public void sendMail(String[] recipients, String subject, String message) {
        sendMail(recipients, subject, message, DEFAULT_EMAIL_SENDER_ADRESS);
    }

    public void sendMail(String[] recipients, String subject, String message, String senderAddress) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);


        try {
            Message msg = new MimeMessage(session);
            InternetAddress[] addresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addresses[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setFrom(new InternetAddress(senderAddress));
            msg.setSentDate(new Date());
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        } catch (MessagingException e) {
            log.severe(e.getMessage());
        }

    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }
}
