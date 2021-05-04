package mpegg.userapp.userapp.Controllers;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/addFile")
    public String addFile(@RequestParam("file_name") String file_name) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization","Bearer "+context.getTokenString());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file_name", file_name);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(urlWorkflow + "/api/v1/addFile", HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "Error creating file";
        }
        return "File created successfully";
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
            body.add("dt_md",file.getResource());
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(urlWorkflow + "/api/v1/addDatasetGroup", HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "Not ok";
        }
        return "ok";
    }

    @PostMapping("/addDataset")
    public String addDataset(@RequestPart(value = "dt_md",required = false) MultipartFile dt_md, @RequestPart(value = "dt_pr", required = false) MultipartFile dt_pr, @RequestPart("dg_id") String dg_id) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization","Bearer "+context.getTokenString());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("dg_id", dg_id);
        body.add("dt_md", dt_md.getResource());
        body.add("dt_pr", dt_pr.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(urlWorkflow + "/api/v1/addDataset", HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "Not ok";
        }
        return "ok";
    }

    @PostMapping("/editDatasetGroup")
    public String editDatasetGroup(@RequestPart(value = "dg_md",required = false) MultipartFile dg_md, @RequestPart(value = "dg_pr", required = false) MultipartFile dg_pr, @RequestPart("dg_id") String dg_id) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization","Bearer "+context.getTokenString());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("dg_id", dg_id);
        body.add("dg_md", dg_md.getResource());
        body.add("dg_pr", dg_pr.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(urlWorkflow + "/api/v1/editDatasetGroup", HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "Not ok";
        }
        return "ok";
    }

    @PostMapping("/editDataset")
    public String editDataset(@RequestPart(value = "dt_md",required = false) MultipartFile dt_md, @RequestPart(value = "dt_pr", required = false) MultipartFile dt_pr, @RequestPart("dt_id") String dt_id) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization","Bearer "+context.getTokenString());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("dt_id", dt_id);
        body.add("dt_md", dt_md.getResource());
        body.add("dt_pr", dt_pr.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(urlWorkflow + "/api/v1/editDataset", HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "Not ok";
        }
        return "ok";
    }

}
