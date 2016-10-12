package ch.opentrainingcenter.gui.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ch.opentrainingcenter.gui.service.UserService;

@ApplicationScoped
public class MyApplicationScope {

    @Inject
    private UserService service;

    @PostConstruct
    public void initAthlete() {
        service.initAthlete();
    }

    public long getApplicationUserId() {
        return service.getApplicationUserId();
    }

}
