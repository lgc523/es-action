package dev.spider.es.api;

import dev.spider.es.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("index")
@Slf4j
public class IndexApi {

    @Autowired
    RestHighLevelClient client;

    @GetMapping("getDocById/{id}")
    public Object getByDocId(@PathVariable("id") String id) throws IOException {
//        GetRequest getRequest = new GetRequest("GET", "/kibana_sample_data_ecommerce/_search?q=customer_first_name:Eddie");
        GetRequest getRequest = new GetRequest("users", id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        String jsonString = JsonUtil.toJsonString(getResponse);
        log.info("{}", jsonString);
        return jsonString;
    }

}
