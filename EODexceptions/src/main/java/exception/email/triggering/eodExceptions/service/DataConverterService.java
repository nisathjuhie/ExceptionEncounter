package exception.email.triggering.eodExceptions.service;

import exception.email.triggering.eodExceptions.jsonrespone.HitsItem;
import exception.email.triggering.eodExceptions.jsonrespone.Response;
import exception.email.triggering.eodExceptions.jsonrespone.Source;
import exception.email.triggering.eodExceptions.model.ExceptionsEncountered;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataConverterService {

    public List<ExceptionsEncountered> convertData(Response input ){

        List<ExceptionsEncountered> listOfExceptions = new ArrayList<>();
        List<HitsItem> hitsItems = input.getHits().getHits();
        for(HitsItem hititems : hitsItems) {
            Source source = hititems.getSource();
            ExceptionsEncountered exceptionsEncountered = new ExceptionsEncountered();
            exceptionsEncountered.setLogMessage(source.getLogMessage());
            exceptionsEncountered.setSpringActiveProfile(source.getSpringActiveProfile());
            exceptionsEncountered.setSpringAppName(source.getSpringAppName());
            exceptionsEncountered.setStackTrace(source.getStackTrace());
            exceptionsEncountered.setExceptionName(setExceptionName(source.getLogMessage()));
            listOfExceptions.add(exceptionsEncountered);
        }
        return listOfExceptions;
    }
    public String setExceptionName(String logMessage) {
        String[] arrOfLogmessage = logMessage.split("::");
       return arrOfLogmessage[2];
    }
}
