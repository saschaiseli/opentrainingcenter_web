package ch.opentrainingcenter.gui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.business.dbaccess.AthleteService;
import ch.opentrainingcenter.business.domain.Athlete;

/**
 * EJB um Security
 *
 */
@Stateful
@Named
public class SecuritySession implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySession.class);
    @Resource
    private SessionContext context;

    @Inject
    private AthleteService service;

    private static final long serialVersionUID = 1L;

    private Athlete athlete = null;

    public SecuritySession() {
        LOGGER.info("SecuritySession created");
    }

    public Athlete getUser() {
        final Principal principal = context.getCallerPrincipal();
        final String email = principal.getName();
        athlete = service.findByEmail(email);
        return athlete;
    }

    public String getName() {
        Athlete a = getUser();
        return a.getFirstName();
    }

    public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect("../login.jsf");
    }
}
