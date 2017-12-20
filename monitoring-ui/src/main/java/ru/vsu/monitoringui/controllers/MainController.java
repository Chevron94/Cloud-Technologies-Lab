package ru.vsu.monitoringui.controllers;

/**
 * Created by Roman on 19.12.2017 21:13.
 */

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.result.view.RedirectView;
import ru.vsu.monitoringui.entities.VkEntity;

import java.net.URISyntaxException;


@Controller
public class MainController {

    @Value("${vk.auth.url}")
    private String authUrl;

    @Value("${vk.auth.access-url}")
    private String accessUrl;

    @Value("${vk.auth.client.id}")
    private String appID;

    @Value("${vk.auth.client.secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public RedirectView index(ServerHttpRequest req) throws URISyntaxException {
        if (isAuthenticated(req)) {
            return new RedirectView("index.html");
        } else {
            URIBuilder uriBuilder = new URIBuilder(authUrl);
            String uri = uriBuilder
                    .addParameter("client_id", appID)
                    .addParameter("display", "page")
                    .addParameter("scope", "friends")
                    .addParameter("response_type", "code")
                    .addParameter("v", "5.69").build().toString();
            return new RedirectView(uri);
        }
    }

    @RequestMapping("/auth")
    public RedirectView auth(ServerHttpRequest req) throws URISyntaxException {
        String code = req.getQueryParams().getFirst("CODE");
        URIBuilder uriBuilder = new URIBuilder(accessUrl);
        uriBuilder.addParameter("client_id", appID)
                .addParameter("client_secret", clientSecret)
                .addParameter("code", code)
                .addParameter("redirect_url", "http://monitoring-ui-cloud-monitoring.1d35.starter-us-east-1.openshiftapps.com/auth");
        VkEntity vkEntity = restTemplate.getForEntity(uriBuilder.build().toString(), VkEntity.class).getBody();
        req.getCookies().add("auth", new HttpCookie("auth", String.valueOf(vkEntity.getUserId())));
        return new RedirectView("/");
    }

    private Boolean isAuthenticated(ServerHttpRequest request) {
        return request.getCookies().containsKey("auth");
    }


}
