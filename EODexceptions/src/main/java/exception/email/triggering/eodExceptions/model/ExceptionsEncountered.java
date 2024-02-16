package exception.email.triggering.eodExceptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "exceptiondata")
public class ExceptionsEncountered {

    @Id
    @Field(type = Keyword)
    private String id;
    @Field(type = Keyword)
    private String stackTrace;
    @Field(type = Keyword)
    private String logMessage;
    @Field(type = Keyword)
    private String springAppName;
    @Field(type = Keyword)
    private String springActiveProfile;
    @Field(type = Keyword)
    private String exceptionName;

    @Field(type = FieldType.Date, store = true,
            format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private ZonedDateTime timestamp = ZonedDateTime.now();

    public ExceptionsEncountered(String id, String stackTrace, String logMessage, String springAppName, String springActiveProfile, String exceptionName) {
        this.id = id;
        this.stackTrace = stackTrace;
        this.logMessage = logMessage;
        this.springAppName = springAppName;
        this.springActiveProfile = springActiveProfile;
        this.exceptionName = exceptionName;
    }

//    public ExceptionsEncountered(String asText, String asText1, String asText2, String asText3, String asText4, String asText5) {
//        this.id = asText;
//        this.stackTrace = asText1;
//        this.logMessage = asText2;
//        this.springAppName = asText3;
//        this.springActiveProfile = asText4;
//        this.exceptionName = asText5;
//
//    }
}
