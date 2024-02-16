package exception.email.triggering.eodExceptions.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.email.triggering.eodExceptions.dto.ExceptionDataTble;
import exception.email.triggering.eodExceptions.jsonrespone.Response;
import exception.email.triggering.eodExceptions.model.ExceptionsEncountered;
import exception.email.triggering.eodExceptions.repository.ExceptionTrackerRepository;
import exception.email.triggering.eodExceptions.service.DataConverterService;
import exception.email.triggering.eodExceptions.service.EmailService;
import exception.email.triggering.eodExceptions.service.ExceptionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class ExceptionTrackerController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private DataConverterService dataConverterService;

    @Autowired
    private ExceptionTrackerRepository exceptionTrackerRepository;

    @Autowired
    private ExceptionsDTO exceptionsDTO;

//    @Autowired
//    ExceptionDataTble exceptionDataTble;

    @PostMapping("/savedata")
    public String savedata() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("C:\\Users\\2144296\\Downloads\\new 4.json");
            Response root = objectMapper.readValue(jsonFile, Response.class);

           List<ExceptionsEncountered> exceptionsEncountereds = dataConverterService.convertData(root);
            exceptionTrackerRepository.saveAll(exceptionsEncountereds);
            // Assuming your JSON structure has an array under "resources"
            //emailService.sendEmailWithAttachment();
            return "data saved successfully-----------------------------";

        } catch (Exception e) {
            // Handle any exceptions

            e.printStackTrace();
            return null;
        }

    }

    @GetMapping("/getCountOfException")
    public long getCountOfException() {
        try {
           return  exceptionsDTO.getCountOfException();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }



    @GetMapping("/countOfServiceAttacked")
    public int countOfServiceAttacked() {
        try {
               return exceptionsDTO.servicesAttacked();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @GetMapping("/getDetailsOfException")
    public List<ExceptionDataTble> getDetailsOfException()
    {
        try{
            return  exceptionsDTO.listOfExceptionEncounteredDocs();
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getCountOfBlockerException")
    public long getCountOfBlockerException()
    {
        try{
            return  exceptionsDTO.blockerExceptonCount();
        }catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

  @Scheduled(cron = "0 0 0 15 * ?")
  @Async
    @DeleteMapping("delete")
    public void del()
    {
        exceptionTrackerRepository.deleteAll();
    }

    @PostMapping("/tryemail")
    public void Email()
    {
        try {
            emailService.sendEmailWithAttachment();
        }
        catch(Exception e)    {
                e.printStackTrace();

            }
    }
}
