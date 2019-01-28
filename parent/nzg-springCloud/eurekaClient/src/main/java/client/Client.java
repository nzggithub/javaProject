package client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/a")
public class Client {

    public static void main(String[] args) {
//        String[] arr = {"--server.port=19000"};
        SpringApplication.run(Client.class, args);

//        System.out.println(System.getProperty("local.server.port"));
//        Random random = new Random();
//        for(int i=0;i<1000;i++){
//            System.out.println(random.nextInt(10));
//        }
    }


//    @Value("${server.port}")
//    String port;

    @RequestMapping("/eoca/v1.0/cagw/vims/lists")
    public String home(@RequestBody String str, @RequestHeader("X-Auth-Token") String encoding) {
        System.out.println(1);
        return "{\"message\":\"query successful\",\"code\":\"xx\",\"total\":3,\"data\":[{\"name\":\"VIM_EOCA_1\",\"vid\":\"2a34vim_cmcc_20180808\",\"dataCenter\":\"DC_EOCA_1\",\"auth_URL\":\"http://127.0.0.1:22222/test/tokens\",\"tenantName\":\"ericsson\",\"domainName\":\"Default\",\"userName\":\"ericsson\",\"password\":\"foobar\",\"region\":\"\",\"urlType\":\"public\"},{\"name\":\"VIM_EOCA_2\",\"vid\":\"2a37vim_cmcc_20180809\",\"dataCenter\":\"DC_EOCA_1\",\"AUTH_URL\":\"http://127.0.0.1:22222/test/tokens\",\"tenantName\":\"ericsson\",\"domainName\":\"Default\",\"userName\":\"ericsson\",\"password\":\"foobar\",\"urlType\":\"public\",\"region\":\"regionOne\"},{\"name\":\"VIM_EOCA_8\",\"vid\":\"2a38vim_cmcc_20180810\",\"dataCenter\":\"DC_EOCA_1\",\"AUTH_URL\":\"http://127.0.0.1:22222/test/tokens\",\"tenantName\":\"ericsson\",\"domainName\":\"Default\",\"userName\":\"ericsson\",\"password\":\"foobar\",\"urlType\":\"internal\",\"region\":\"\"}]}";
    }

    @RequestMapping("/eoca/v1.0/cagw/vims/authinfo")
    public String home1(@RequestHeader("X-Auth-Token") String encoding, @RequestParam String VID) {
        System.out.println(2);
        return "{\"message\":\"VIM auth info query successful\",\"code\":\"\",\"total\":4,\"data\":[{\"vid\":\"13811178910\",\"name\":\"ericsson\",\"dataCenter\":\"DC_EOCA_1\",\"auth_URL\":\"http://127.0.0.1:22222/test/tokens\",\"params\":\"{\\\"auth\\\":{\\\"identity\\\":{\\\"password\\\":{\\\"user\\\":{\\\"password\\\":\\\"foobar\\\",\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}},\\\"methods\\\":[\\\"password\\\"]},\\\"scope\\\":{\\\"project\\\":{\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}}}}\",\"urlType\":\"public\",\"region\":\"RegionOne \"},{\"vid\":\"13811178911\",\"name\":\"vim1\",\"dataCenter\":\"datacenter1\",\"auth_URL\":\"http://127.0.0.1:22222/test/tokens\",\"params\":\"{\\\"auth\\\":{\\\"identity\\\":{\\\"password\\\":{\\\"user\\\":{\\\"password\\\":\\\"foobar\\\",\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}},\\\"methods\\\":[\\\"password\\\"]},\\\"scope\\\":{\\\"project\\\":{\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}}}}\",\"urlType\":\"public\",\"region\":\"RegionOne \"},{\"vid\":\"13811178912\",\"name\":\"vim2\",\"dataCenter\":\"datacenter2\",\"auth_URL\":\"http://127.0.0.1:22222/test/tokens\",\"params\":\"{\\\"auth\\\":{\\\"identity\\\":{\\\"password\\\":{\\\"user\\\":{\\\"password\\\":\\\"foobar\\\",\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}},\\\"methods\\\":[\\\"password\\\"]},\\\"scope\\\":{\\\"project\\\":{\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}}}}\",\"urlType\":\"public\",\"region\":\"RegionOne \"},{\"vid\":\"13811178919\",\"name\":\"vim3\",\"dataCenter\":\"DC_EOCA_1\",\"auth_URL\":\"http://127.0.0.1:22222/test/tokens\",\"params\":\"{\\\"auth\\\":{\\\"identity\\\":{\\\"password\\\":{\\\"user\\\":{\\\"password\\\":\\\"foobar\\\",\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}},\\\"methods\\\":[\\\"password\\\"]},\\\"scope\\\":{\\\"project\\\":{\\\"domain\\\":{\\\"name\\\":\\\"default\\\"},\\\"name\\\":\\\"ericsson\\\"}}}}\",\"urlType\":\"public\",\"region\":\"RegionOne \"}]}";
    }

    @RequestMapping("/eoca/v1.0/token")
    public String home2() {
        System.out.println(3);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "asfsfsdfasdfsadfsda");
        jsonObject.put("expires_at", "2028-10-22T03:48:44.117Z");
        return jsonObject.toString();
    }

}