package exception.email.triggering.eodExceptions.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    private static final int ONE_MINUTE = 60 * 1000;
    private static final int ONE_SECOND = 1000;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        HttpHost host = new HttpHost("localhost", 9200);
            RestClientBuilder.RequestConfigCallback requestConfigCallback = requestConfigBuilder -> requestConfigBuilder
                .setConnectionRequestTimeout(0)
                .setSocketTimeout(ONE_MINUTE)
                .setConnectTimeout(ONE_SECOND * 5);

        RestClientBuilder builder = RestClient.builder(host)
                .setRequestConfigCallback(requestConfigCallback);

        return new RestHighLevelClient(builder);
    }
}
