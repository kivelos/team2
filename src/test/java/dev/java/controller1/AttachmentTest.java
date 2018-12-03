package dev.java.controller1;

public class AttachmentTest {

    public static void main(String[] args) {
        String from = "*****@gmail.com"; //вместо звездочек почта
        String password = "*****"; //тут пароль вмето звездочек, хз как сделать чтобы он был не в коде
        String to = "*****@gmail.com,*****@gmail.com"; //та же почта
        String cc = "*****@gmail.com,*****@gmail.com";
        String subject = "Email from Java";
        String message = "Email code checking!!!";
        String attchment = "D://mail-test/*****.txt,D://mail-test/*****.txt"; //тут путь до файла, надо будет на путь в бд поменять

        String successMessage = " ";

        successMessage = AttachmentController.sendMail(from, password, to, subject, message);
        System.out.println(successMessage);

        successMessage = AttachmentController.sendMailWithCC(from, password, to, cc, subject, message);
        System.out.println(successMessage);

        successMessage = AttachmentController.sendMailWithAttachment(from, password, to, cc, subject, message, attchment);
        System.out.println(successMessage);
    }
}
