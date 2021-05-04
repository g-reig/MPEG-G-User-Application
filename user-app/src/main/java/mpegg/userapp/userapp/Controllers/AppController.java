package mpegg.userapp.userapp.Controllers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class AppController {
    private final HttpServletRequest request;
    private final String urlWorkflow = "http://localhost:8086";

    @Autowired
    public AppController(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping(path = "/")
    public RedirectView index() {
        return new RedirectView("/home");
    }

    @GetMapping(path = "/home")
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("home");
        KeycloakSecurityContext context = getKeycloakSecurityContext();
        model.addObject("username", context.getIdToken().getPreferredUsername());
        //model.addAttribute("roles", ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getToken().getRealmAccess().getRoles());
        return model;
    }

    @GetMapping(path = "/addDatasetGroup")
    public ModelAndView addDatasetGroup(@RequestParam("file_id") String file_id) {
        ModelAndView model = new ModelAndView("addDatasetGroup");
        KeycloakSecurityContext context = getKeycloakSecurityContext();
        model.addObject("file_id", file_id);
        return model;
    }

    @GetMapping(path = "/addDataset")
    public ModelAndView addDataset(@RequestParam("dg_id") String dg_id) {
        ModelAndView model = new ModelAndView("addDataset");
        model.addObject("dg_id", dg_id);
        return model;
    }

    @GetMapping(path = "/editDatasetGroup")
    public ModelAndView editDatasetGroup(@RequestParam("dg_id") String dg_id) {
        ModelAndView model = new ModelAndView("editDatasetGroup");
        model.addObject("dg_id", dg_id);
        return model;
    }

    @GetMapping(path = "/editDataset")
    public ModelAndView editDataset(@RequestParam("dt_id") String dt_id) {
        ModelAndView model = new ModelAndView("editDataset");
        model.addObject("dt_id", dt_id);
        return model;
    }

    @GetMapping(path = "/addFile")
    public ModelAndView addFile() {
        return new ModelAndView("addFile");
    }

    @GetMapping(path = "/ownFiles")
    public String ownFiles() {
        ModelAndView model = new ModelAndView("ownFiles");
        KeycloakSecurityContext context = getKeycloakSecurityContext();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization" , "Bearer "+context.getTokenString());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        response = restTemplate.exchange(urlWorkflow+"/api/v1/ownFiles", HttpMethod.GET, entity, String.class);
        return response.getBody().toString();
    }

    @GetMapping(path = "/test")
    public String test() {
        return "ok";
    }

    @GetMapping(path = "/logout")
    public RedirectView logout() throws ServletException {
        request.logout();
        return new RedirectView("/app/home");
    }

    private KeycloakSecurityContext getKeycloakSecurityContext() {
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }
}
