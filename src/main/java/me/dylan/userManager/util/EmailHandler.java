package me.dylan.userManager.util;

import me.dylan.userManager.db.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailHandler {
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String PORT = "587";
    private static final String SENDER_EMAIL = "usermanager.info@gmail.com";
    private static final String PASSWORD = "Info1234";

    public static void sendEmail(String receiver, String subject, String content){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL, PASSWORD);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendConfirmation(User user){
        String link = "http://localhost:8180/user_manager/rest/users/register/confirm?" +
                "username=" + user.getName() + "&confirmID=" + user.getCurrentID();
        String html = "<p>Hi,<br>\n" +
                      "<br>\n" +
                      "Click this <a href=\"{link}\">link</a> to confirm your account.<br>\n" +
                      "If the link doesn't show, copy \"" + link + "\" into your webbrowser." +
                      "<br>\n" +
                      "Greetings<br></p>";
        sendEmail(user.getEmail(), "Confirmation of account", html);
    }
}
