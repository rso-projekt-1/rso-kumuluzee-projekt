import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import rso.project.video.persistence.Video;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class RecommenderBean {

    @Inject
    @DiscoverService(value="video-service",environment = "dev", version = "*")
    private Optional<String> basePath;


    private Logger log = LogManager.getLogger(RecommenderBean.class.getName());

    private ObjectMapper objectMapper;

    private HttpClient httpClient;

    @PostConstruct
    private void init() {
        HttpClientBuilder tmp = HttpClientBuilder.create();
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(2000);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(2000);
        tmp.setDefaultRequestConfig(requestBuilder.build());
        httpClient = tmp.build();
        objectMapper = new ObjectMapper();
    }

    public Video recommendRandom(){
        if(basePath.isPresent()){
                try {
                    String request_uri = basePath.get() + "/v1/video/";
                    //request_uri = basePath.get()+"/v1/orders/";

                    System.out.println(request_uri);
                    HttpGet request = new HttpGet(request_uri);
                    HttpResponse response = httpClient.execute(request);

                    int status = response.getStatusLine().getStatusCode();
                    log.info("Status " + status);
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        if(entity != null) {
                            List<Video> vids = getObjects(EntityUtils.toString(entity));
                            int ind = (int) (Math.random ()* vids.size());
                            return vids.get(ind);
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    log.error("Error: " + basePath.get());
                } catch (IOException e) {
                    String msg = e.getClass().getName() + " occured: " + e.getMessage();
                    System.out.println(msg);
                    throw new InternalServerErrorException(msg);
                }

        }else{
            log.warn("Video-service not discovered.");
        }
        return null;
    }


    private List<Video> getObjects(String json) throws IOException {
        return json == null ? new ArrayList<>() : objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Video.class));
    }
}
