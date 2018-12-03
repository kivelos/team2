package dev.java.controller1;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class AttachmentController {

    public static Properties getProperty() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }

    public static Session getSession(final String from, final String password) {
        Session session = Session.getDefaultInstance(getProperty(), new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        return session;
    }

    public static MimeMessage getMimeMsg(final String from, final String password) {
        Session session = getSession(from, password);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSender(new InternetAddress(from));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public static void setToAddress(MimeMessage mimeMessage, String to) {
        if (to != null) {
            try {
                String[] toArry = to.split(",");
                for (int i = 0; i < toArry.length; i++) {
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toArry[i]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void setCCAddress(MimeMessage mimeMessage, String cc) {
        if (cc != null) {
            try {
                String[] toArry = cc.split(",");
                for (int i = 0; i < toArry.length; i++) {
                    mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(toArry[i]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void setMessage(MimeMessage mimeMessage, String message) {
        if (message != null) {
            try {
                mimeMessage.setText(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setSubject(MimeMessage mimeMessage, String subject) {
        if (subject != null) {
            try {
                mimeMessage.setSubject(subject);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileName(String filenPath) {
        String[] file = filenPath.split("/");
        String fileName = file[file.length - 1];
        return fileName;
    }

    private static List<MimeBodyPart> setMultipleAttMnt(String attchment) {
        List<MimeBodyPart> messageBodyList = new ArrayList<MimeBodyPart>();
        try {
            String[] attchList = attchment.split(",");

            for (int i = 0; i < attchList.length; i++) {

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attchList[i]);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(getFileName(attchList[i]));
                messageBodyList.add(messageBodyPart);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return messageBodyList;
    }

    private static void setAttachment(MimeMessage mimeMessage, String attchment, String message) {
        if (attchment != null) {
            try {
                Multipart multipart = new MimeMultipart();

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message);

                multipart.addBodyPart(messageBodyPart);

                List<MimeBodyPart> mimeList = setMultipleAttMnt(attchment);

                for (int i = 0; i < mimeList.size(); i++) {
                    multipart.addBodyPart(mimeList.get(i));
                }
                mimeMessage.setContent(multipart);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendMail(final String from, final String password, String to, String subject,
                                  String message) {
        String successMessage = "";
        MimeMessage mimeMessage = getMimeMsg(from, password);
        try {
            setToAddress(mimeMessage, to);
            setSubject(mimeMessage, subject);
            setMessage(mimeMessage, message);
            Transport.send(mimeMessage);
            successMessage = "message sent successfully";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return successMessage;
    }

    public static String sendMailWithCC(String from, String password, String to, String cc, String subject,
                                        String message) {
        String successMessage = "";
        MimeMessage mimeMessage = getMimeMsg(from, password);
        try {
            setToAddress(mimeMessage, to);
            setCCAddress(mimeMessage, cc);
            setSubject(mimeMessage, subject);
            setMessage(mimeMessage, message);
            Transport.send(mimeMessage);
            successMessage = "message sent successfully with cc";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return successMessage;
    }

    public static String sendMailWithAttachment(String from, String password, String to, String cc, String subject,
                                                String message, String attchment) {

        String successMessage = "";
        MimeMessage mimeMessage = getMimeMsg(from, password);
        try {
            setToAddress(mimeMessage, to);
            setCCAddress(mimeMessage, cc);
            setSubject(mimeMessage, subject);
            setAttachment(mimeMessage, attchment, message);
            Transport.send(mimeMessage);
            successMessage = "message sent successfully with Attachment";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return successMessage;
    }
}
