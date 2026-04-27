package com.costellj.internshiptracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    @Value("${feedback.recipient.email}")
    private String recipientEmail;

    public void sendFeedbackNotification(String type, String message, String submittedBy) {
        try {
            Email from = new Email(fromEmail);
            Email to = new Email(recipientEmail);
            String subject = "[Internship Tracker] New " + type.replace("_", " ").toLowerCase();
            Content content = new Content("text/plain",
                "Type: " + type + "\n" +
                "From: " + submittedBy + "\n\n" +
                "Message:\n" + message
            );

            Mail mail = new Mail(from, subject, to, content);
            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);

        } catch (Exception e) {
            System.err.println("Failed to send feedback email: " + e.getMessage());
        }
    }
}