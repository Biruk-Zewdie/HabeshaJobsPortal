package com.biruk.habeshaJobs.Mailing;

import jakarta.mail.MessagingException;


public interface EmailService {

    void sendMail (final AbstractEmailContext email) throws MessagingException;
}
