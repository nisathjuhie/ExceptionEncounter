package exception.email.triggering.eodExceptions.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.email.triggering.eodExceptions.dto.ExceptionDataTble;
import exception.email.triggering.eodExceptions.repository.ExceptionTrackerRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class ExceptionsDTO {

//    @Autowired
//    ExceptionDataTble exceptionDataTble;
    @Autowired
    ExceptionTrackerRepository exceptionTrackerRepository;

    @Autowired
    RestHighLevelClient highLevelClient;

   String zdtGteValue;
    String zdtLteValue;

//  @Value("${exceptions.blocker}")
//     List<String> exceptionsToMatch;

    public void returnTimestampValues()
    {
        ZoneId zoneId = ZonedDateTime.now().getZone() ;
        LocalDate today = LocalDate.now( zoneId  ) ;

        ZonedDateTime zdtStart = today.atStartOfDay( zoneId ) ;
        ZonedDateTime zdtStop = today.plusDays( 1 ).atStartOfDay( zoneId ) ;
       // 2023-11-22T11:18:20.462+0530
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        zdtGteValue = zdtStart.format(formatter);
        zdtLteValue = zdtStop.format(formatter);
    }
    public long getCountOfException(){
        ZoneId zoneId = ZonedDateTime.now().getZone() ;
        LocalDate today = LocalDate.now( zoneId  ) ;

        ZonedDateTime zdtStart = today.atStartOfDay( zoneId ) ;
        ZonedDateTime zdtStop = today.plusDays( 1 ).atStartOfDay( zoneId ) ;
        return exceptionTrackerRepository.countByTimestampBetween(zdtStart,zdtStop);
    }

        public int servicesAttacked()
        {
            try{

returnTimestampValues();
            Request request = new Request("GET", "/exceptiondata/_search");
            // Set the request body
                request.setJsonEntity(
                        "{" +
                                "  \"query\": {" +
                                "    \"range\": {" +
                                "      \"timestamp\": {" +
                                "        \"gte\": \""+zdtGteValue+"\"," +
                                "        \"lte\": \""+zdtLteValue+"\"" +
                                "      }" +
                                "    }" +
                                "  }," +
                                "  \"_source\": [\"springAppName\"]" +
                                "}"
                );
            // Execute the request
            Response response = highLevelClient.getLowLevelClient().performRequest(request);
            // Parse the JSON response
            List<String> listOfservicesAttacked = parseJsonResponse(response.getEntity().getContent());
            Set<String> setOfservicesAttacked = new HashSet<String>(listOfservicesAttacked);
            setOfservicesAttacked.addAll(listOfservicesAttacked);
            return setOfservicesAttacked.size();
        } catch (IOException e) {
        e.printStackTrace();
        return 0;
    }
}

public List<ExceptionDataTble> listOfExceptionEncounteredDocs()
{
    try{
        returnTimestampValues();

        Request request = new Request("GET", "/exceptiondata/_search");


    // Set the request body with a range filter on the timestamp field and terms aggregation on two fields
    request.setJsonEntity(
            "{" +
                    "  \"query\": {" +
                    "    \"range\": {" +
                    "      \"timestamp\": {" +
                    "        \"gte\": \""+zdtGteValue+"\"," +
                    "        \"lte\": \""+zdtLteValue+"\"" +
                    "      }" +
                    "    }" +
                    "  }," +

                    "  \"aggs\": {" +
                    "    \"distinct_values\": {" +
                    "\"multi_terms\":{"+
                    "      \"terms\": [" +
                    "        {\"field\":\"exceptionName\"},"+
                    "        {\"field\":\"springAppName\"}, "+
                    "{\"field\":\"springActiveProfile\"},"+
                    "{\"field\":\"stackTrace\"},"+

                    "        {\"field\":\"logMessage\"} "+
                    "        ]" +
                    "      }" +
                    "    }" +
                    "  }," +

                    "\"size\":0"+
            "}"
    );

    // Execute the request
    Response response = highLevelClient.getLowLevelClient().performRequest(request);

    // Parse the JSON response
    var distinctValues = parseDistinctValues(response.getEntity().getContent());

    return distinctValues;

} catch (IOException e) {
        e.printStackTrace();
        return  null;
    }
}

public long blockerExceptonCount()
    {
        returnTimestampValues();
        try {
            String index = "exceptiondata"; // Replace with your actual index name
            String timestampField = "timestamp"; // Replace with your actual timestamp field name
            String exceptionNameField = "exceptionName"; // Replace with your actual exception name field name
            List<String> exceptionsToMatch = Arrays.asList("ArithmeticException","ArrayIndexOutOfBoundsException","ClassNotFoundException","FileNotFoundException","IOException","InterruptedException","NoSuchFieldException","NoSuchMethodException","NullPointerException","NumberFormatException","RuntimeException","StringIndexOutOfBoundsException","IllegalArgumentException","IllegalStateException");
            SearchRequest searchRequest = new SearchRequest(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.rangeQuery(timestampField)
                            .gte(zdtGteValue) // Replace with your actual start timestamp
                            .lte(zdtLteValue)   // Replace with your actual end timestamp
                    )
                    .filter(QueryBuilders.termsQuery(exceptionNameField, exceptionsToMatch));
            searchSourceBuilder.query(boolQuery);
          //  searchSourceBuilder.aggregation(AggregationBuilders.terms("exception_counts").field(exceptionNameField));
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // Access the count of blocker exceptions from the aggregation
            long count = response.getHits().getTotalHits().value;
            return count;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }



    private static List<String> parseJsonResponse(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);

        List<String> fieldValues = new ArrayList<>();

        JsonNode hitsNode = rootNode.path("hits").path("hits");
        Iterator<JsonNode> iterator = hitsNode.elements();
        while (iterator.hasNext()) {
            JsonNode sourceNode = iterator.next().path("_source");
            String fieldValue = sourceNode.path("springAppName").asText();
            fieldValues.add(fieldValue);
        }
        return fieldValues;
    }

    private static List<ExceptionDataTble> parseDistinctValues(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);

        List<ExceptionDataTble> distinctValues = new ArrayList<>();

        JsonNode bucketsNode = rootNode.path("aggregations").path("distinct_values").path("buckets");
        Iterator<JsonNode> iterator = bucketsNode.elements();
        while (iterator.hasNext()) {
            JsonNode bucketNode = iterator.next();

            JsonNode stackTrace =bucketNode.path("key").get(1);
            JsonNode logMessage =bucketNode.path("key").get(4);
            JsonNode springAppName =bucketNode.path("key").get(3);
            JsonNode springActiveProfile =bucketNode.path("key").get(2);
            JsonNode exceptionName = bucketNode.path("key").get(0);

            // Create an instance of YourClass and add it to the list
        //   ExceptionsEncountered exceptionsEncountered = new ExceptionsEncountered(id.asText(), stackTrace.asText(), logMessage.asText(), springAppName.asText(), springActiveProfile.asText(),exceptionName.asText());
           ExceptionDataTble exceptionDataTble1 = new ExceptionDataTble(stackTrace.toString(),logMessage.toString(),springAppName.toString(),springActiveProfile.toString(),exceptionName.toString());
//            exceptionsEncountered.setField1(field1Node.asText());
//            yourInstance.setField2(field2Node.asText());
            distinctValues.add(exceptionDataTble1);
        }
        return distinctValues;
            }


   }
