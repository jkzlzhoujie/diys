package cn.temobi.complex.service.mail;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.TemplateException;

public class SendMailService implements Cloneable, Serializable  {
    private static final long serialVersionUID = 7227872593631462901L;
    private static Logger log = LoggerFactory.getLogger(SendMailService.class);
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private JavaMailSender sender;
    private String form;

    @SuppressWarnings("unchecked")
	public String generateMailMessage(String templateFile, Map data) {
        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration().getTemplate(templateFile), data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean sendMail(SimpleMailMessage simpleMailMessage) {
        try {
            if (null != simpleMailMessage && StringUtils.isNotEmpty(form))
                simpleMailMessage.setFrom(form);
            sender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            log.error("send mail errors>>" + e.getMessage());
            return false;
        }
    }
    
    public Boolean sendMailWithAttach(SimpleMailMessage simpleMailMessage,String attachPath,String attachName) {
        try {
            if (null != simpleMailMessage && StringUtils.isNotEmpty(form))
                simpleMailMessage.setFrom(form);
            MimeMessage msg = sender.createMimeMessage();
            List<MailAttachFiles> mailAttachFiles = new ArrayList<MailAttachFiles>();
            MailAttachFiles file = new MailAttachFiles();
            file.setAttachFileDataSource(new FileDataSource(new File(attachPath)));
            file.setAttachFileName(attachName);
            mailAttachFiles.add(file);
            simpleMailMessage.setMailAttachFiles(mailAttachFiles);
            simpleMailMessage.prepare(msg);
            /**
            MimeMessageHelper helper = new MimeMessageHelper(msg, true,"UTF-8");
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setText(simpleMailMessage.getHtml(),true);
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setSentDate(new Date());
            //夹带附件
            FileSystemResource file = new FileSystemResource(new File(attachPath));
            helper.addAttachment(attachName, file);
            **/
            sender.send(msg);
            return true;
        } catch (Exception e) {
            log.error("send mail errors>>" + e.getMessage());
            return false;
        }
    }


    @SuppressWarnings("unchecked")
	public SimpleMailMessage getSimpleMailMessage(String subject, String to, String templateFile, Map data) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setHtml(generateMailMessage(templateFile, data));
        return simpleMailMessage;
    }

    public JavaMailSender getSender() {
        return sender;
    }

    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public FreeMarkerConfigurer getFreeMarkerConfigurer() {
        return freeMarkerConfigurer;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}

