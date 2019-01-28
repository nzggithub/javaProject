package client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("/hi")
    public String hi(@RequestParam String json){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
        JSONObject result = restTemplate.postForObject("http://SERVICE-HI/hi?name=阿a", formEntity, JSONObject.class);
        return result.toString();
    }
}
