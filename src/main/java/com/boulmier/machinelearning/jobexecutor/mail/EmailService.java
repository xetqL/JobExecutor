package com.boulmier.machinelearning.jobexecutor.mail;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.jobexecutor.encrypted.Credential;
import com.boulmier.machinelearning.jobexecutor.encrypted.CredentialProvider;
import java.io.File;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import org.codemonkey.simplejavamail.*;

public class EmailService {
    
    private static final String smtp_server = "smtp.gmail.com";
    private static String user, password;
    private static final int port = 465;
    /**
     * send an email to clientEmail with an included file
     *
     * @param result
     * @param clientEmail
     */
    public static void sendWith(File result, String clientEmail) {
        Credential cred = CredentialProvider.provideCredential(JobExecutor.decryptKey, CredentialProvider.CredentialSource.EMAIL);
        Email e = new Email();
        DataSource ds = new FileDataSource(result);
        e.setFromAddress("hepiacloud", cred.getUser());
        e.addRecipient("Client", clientEmail, Message.RecipientType.TO);
        e.addAttachment("job_result", ds);
        e.setTextHTML("<h1> Here is the result of your job execution on hepiacloud</h1><br/><p>please doesn't respond to this email</p>");
        e.setSubject("Result of your job execution on hepiacloud");
        new Mailer(smtp_server,port,cred.getUser(), cred.getPassword(),  TransportStrategy.SMTP_SSL).sendMail(e);
    }
}
