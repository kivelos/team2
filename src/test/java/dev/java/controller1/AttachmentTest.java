package dev.java.controller1;

public class AttachmentTest {

    public static void main(String[] args) {
        String from = "*****@gmail.com";
        String password = "*****";
        String to = "*****@gmail.com,*****@gmail.com";
        String cc = "*****@gmail.com,*****@gmail.com";
        String subject = "Email from Java";
        String message = "Email code checking!!!";
        String attchment = "D://mail-test/*****.txt,D://mail-test/*****.txt";

        String successMessage = " ";

        successMessage = AttachmentController.sendMail(from, password, to, subject, message);
        System.out.println(successMessage);

        successMessage = AttachmentController.sendMailWithCC(from, password, to, cc, subject, message);
        System.out.println(successMessage);

        successMessage = AttachmentController.sendMailWithAttachment(from, password, to, cc, subject, message, attchment);
        System.out.println(successMessage);
    }
}
