package exception.email.triggering.eodExceptions.repository;

import exception.email.triggering.eodExceptions.model.ExceptionsEncountered;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


@Repository
public interface ExceptionTrackerRepository extends ElasticsearchRepository<ExceptionsEncountered,String>{
    @Query("{\"range\":{\"timestamp\":{\"gte\":\"?0\",\"lte\":\"?1\"}}}")
    long countByTimestampBetween(ZonedDateTime startTimestamp, ZonedDateTime endTimestamp);

}
