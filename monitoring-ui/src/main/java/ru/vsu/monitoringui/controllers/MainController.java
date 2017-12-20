package ru.vsu.monitoringui.controllers;

/**
 * Created by Roman on 19.12.2017 21:13.
 */

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.server.ServerWebExchange;
import ru.vsu.monitoringui.entities.VkEntity;

import java.net.URISyntaxException;


@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

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
    public RedirectView index(ServerWebExchange exchange) throws URISyntaxException {
        if (isAuthenticated(exchange.getRequest())) {
            return new RedirectView("index.html");
        } else {
            URIBuilder uriBuilder = new URIBuilder(authUrl);
            String uri = uriBuilder
                    .addParameter("client_id", appID)
                    .addParameter("display", "page")
                    .addParameter("scope", "friends")
                    .addParameter("response_type", "code")
                    .addParameter("redirect_uri", "http://monitoring-ui-cloud-monitoring.1d35.starter-us-east-1.openshiftapps.com/auth")
                    .addParameter("v", "5.69").build().toString();
            LOGGER.info(uri);
            return new RedirectView(uri);
        }
    }

    @RequestMapping("/auth")
    public RedirectView auth(@RequestParam("code") String code, ServerWebExchange exchange) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(accessUrl);
        uriBuilder.addParameter("client_id", appID)
                .addParameter("client_secret", clientSecret)
                .addParameter("code", code)
                .addParameter("redirect_url", "http://monitoring-ui-cloud-monitoring.1d35.starter-us-east-1.openshiftapps.com/auth");
        LOGGER.info(uriBuilder.build().toString());
        try {
            VkEntity vkEntity = restTemplate.getForEntity(uriBuilder.build().toString(), VkEntity.class).getBody();
            exchange.getResponse().addCookie(ResponseCookie.from("auth", vkEntity.getUserId().toString()).build());
        } catch (RestClientException ex) {
            LOGGER.error("Auth failed, error: "+ex.getMessage(), ex);
            exchange.getResponse().addCookie(ResponseCookie.from("auth", code).build());
        }
        return new RedirectView("/");
    }

    private Boolean isAuthenticated(ServerHttpRequest request) {
        return request.getCookies().getFirst("auth") != null;
    }
}
