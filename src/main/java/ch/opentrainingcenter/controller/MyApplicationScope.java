package ch.opentrainingcenter.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ch.opentrainingcenter.model.Athlete;
import ch.opentrainingcenter.model.CommonTransferFactory;
import ch.opentrainingcenter.service.AthleteService;

@ApplicationScoped
public class MyApplicationScope {

    private static final String DUMMY_EMAIL = "dummy@opentrainingcenter.ch";

    @Inject
    private AthleteService service;

    private Athlete applicationUser;

    @PostConstruct
    public void initAthlete() {
        final Athlete athlete = service.findByEmail(DUMMY_EMAIL);
        if (athlete == null) {
            applicationUser = CommonTransferFactory.createAthlete("Dummy", "Dummy", DUMMY_EMAIL, "password");
            service.doSave(applicationUser);
        }
    }

    public Athlete getApplicationUser() {
        return applicationUser;
    }

}
