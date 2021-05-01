package mpegg.userapp.userapp.Controllers;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class APIController {
    private final HttpServletRequest request;
    private final String urlWorkflow = "http://localhost:8086";

    @Autowired
    public APIController(HttpServletRequest request) {
        this.request = request;
    }

    @PostMapping("/addDatasetGroup")
    public String addDatasetGroup(@RequestPart("dg_md") MultipartFile dg_md, @RequestPart("dg_pr") MultipartFile dg_pr, @RequestPart("dt_md") MultipartFile[] dt_md, @RequestPart("file_id") String file_id) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization","Bearer "+context.getTokenString());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file_id", file_id);
        body.add("dg_md", dg_md.getResource());
        body.add("dg_pr", dg_pr.getResource());
        for (MultipartFile file : dt_md) {
            body.add("dt_mt",file.getResource());
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(urlWorkflow+"/api/v1/addDatasetGroup", HttpMethod.POST, requestEntity, String.class);
        return "ok";
    }
}
