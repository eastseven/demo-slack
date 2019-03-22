package cn.eastseven;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author d7
 */
public class SlackClient {

    public static void send(String url, String text) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("text", text);

            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            String requestJson = new ObjectMapper().writeValueAsString(map);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
            new RestTemplate().postForObject(url, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
