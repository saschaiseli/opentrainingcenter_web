package ch.opentrainingcenter.gui.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ch.opentrainingcenter.business.dbaccess.AthleteService;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;

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
		} else {
			applicationUser = athlete;
		}

	}

	public Athlete getApplicationUser() {
		return applicationUser;
	}

}
