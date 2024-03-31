package exception.email.triggering.eodExceptions.service;


import exception.email.triggering.eodExceptions.dto.ExceptionDataTble;
import exception.email.triggering.eodExceptions.model.ExceptionsEncountered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    ExceptionsDTO exceptionsDTO;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${receptient.emails}")
    private String[] to;

    @Value("${receptient.subject}")
    private String subject;
    private final TemplateEngine templateEngine;
    @Autowired
    public EmailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Scheduled(cron = "0 0 */24 * * *") // Run every 24 hours
    @Async
    public void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String body ="Total no of exception: "+ exceptionsDTO.getCountOfException() +"\nNo of services attacked: "+ exceptionsDTO.servicesAttacked() + "\nNo of Blocker Exceptions occured: "+ exceptionsDTO.blockerExceptonCount();

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        updateExceptionTable();
        FileSystemResource file = new FileSystemResource(new File("C:/Users/Downloads/EODexceptions/EODexceptions/src/main/resources/templates/exceptionTable.html"));
        helper.addAttachment("exceptionTable.html", file);

        mailSender.send(message);
    }

    public void updateExceptionTable(List<ExceptionDataTble> exceptions) {
        // Create a Thymeleaf context and add the new data
        Context context = new Context();
        context.setVariable("exceptions", exceptions);

        // Process the Thymeleaf template with the new data
        String htmlContent = templateEngine.process("exceptionTable", context);

        // Write the updated HTML content to a file
        writeHtmlToFile(htmlContent);
    }

    private void writeHtmlToFile(String htmlContent) {
        // Set the path where you want to store the HTML file
        Path filePath = Paths.get("C:/Users/Downloads/EODexceptions/EODexceptions/src/main/resources/templates/exceptionTable.html");

        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            // Write the HTML content to the file
            writer.write(htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public void updateExceptionTable() {
        // Call your Java method to fetch new exception data
        List<ExceptionDataTble> newData = exceptionsDTO.listOfExceptionEncounteredDocs();
//
//        // Update the exception table with the new data
     updateExceptionTable(newData);
    }


}
