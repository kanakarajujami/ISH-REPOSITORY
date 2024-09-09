package com.nt.utils;

import java.io.File;


import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
@Component
public class EmailUtil {
    @Autowired
	private  JavaMailSender sender;
    public void sendEmail(String toEmail,String sub,String body,File file) throws Exception {
                     MimeMessage message=sender.createMimeMessage(); 
                     MimeMessageHelper helper=new MimeMessageHelper(message, true);
                     helper.setTo(toEmail);
                     helper.setSentDate(new Date());
                     helper.setSubject(sub);
                     helper.setText(body,true);
                     helper.addAttachment("pdf file", file);
                   helper.setCc("bhaskarbolem42@gmail.com");
                   helper.setBcc("kanakarajujamio9@gmail.com");
                     sender.send(message);
                     
    }
}
