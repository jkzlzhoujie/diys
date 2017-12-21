package cn.temobi.complex.service.mail;

import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.List;

public class SimpleMailMessage implements MimeMessagePreparator {
    public final static String KDINS_MAIL_NICKNAME = "淘流量产品团队";
    private String to;
    private String from;
    private String fromNickName = KDINS_MAIL_NICKNAME;
    private String subject;
    private String plainText;
    private String html;
    private List<MailAttachFiles> mailAttachFiles;

    @Override
    public void prepare(javax.mail.internet.MimeMessage mimeMessage) throws Exception {
        mimeMessage.setFrom(new InternetAddress(from, fromNickName, "UTF-8"));
        mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        mimeMessage.setSubject(subject, "UTF-8");
        MimeMultipart mainMultipart = new MimeMultipart("mixed");
        mainMultipart.addBodyPart(createContent());
        if (mailAttachFiles != null) {
            for (MailAttachFiles fileBean : mailAttachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                DataHandler dataHandler = new DataHandler(fileBean.getAttachFileDataSource());
                attachPart.setFileName(MimeUtility.encodeWord(fileBean.getAttachFileName(), "UTF-8", null));
                attachPart.setDataHandler(dataHandler);
                mainMultipart.addBodyPart(attachPart);
            }
        }
        mimeMessage.setContent(mainMultipart);
    }

    private BodyPart createContent() throws MessagingException {
        BodyPart content = new MimeBodyPart();
        MimeMultipart contentMultipart = new MimeMultipart("alternative");
        if (plainText != null) {
            BodyPart plainTextPart = new MimeBodyPart();
            plainTextPart.setContent(plainText, "text/plain;charset=UTF-8");
            contentMultipart.addBodyPart(plainTextPart);
        }
        if (html != null) {
            BodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html;charset=UTF-8");
            contentMultipart.addBodyPart(htmlPart);
        }
        content.setContent(contentMultipart);
        return content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<MailAttachFiles> getMailAttachFiles() {
        return mailAttachFiles;
    }

    public void setMailAttachFiles(List<MailAttachFiles> mailAttachFiles) {
        this.mailAttachFiles = mailAttachFiles;
    }

}
