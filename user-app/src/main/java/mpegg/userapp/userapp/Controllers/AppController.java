package mpegg.userapp.userapp.Controllers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class AppController {
    private final HttpServletRequest request;

    @Autowired
    public AppController(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping(path = "/")
    public RedirectView index() {
        return new RedirectView("/app/home");
    }

    @GetMapping(path = "/app/home")
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("home");
        KeycloakSecurityContext context = getKeycloakSecurityContext();
        model.addObject("username", context.getIdToken().getPreferredUsername());
        //model.addAttribute("roles", ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getToken().getRealmAccess().getRoles());
        return model;
    }

    @GetMapping(path = "/app/create")
    public ModelAndView create() {
        ModelAndView model = new ModelAndView("create");
        KeycloakSecurityContext context = getKeycloakSecurityContext();
        model.addObject("username", context.getIdToken().getPreferredUsername());
        //model.addAttribute("roles", ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getToken().getRealmAccess().getRoles());
        return model;
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
