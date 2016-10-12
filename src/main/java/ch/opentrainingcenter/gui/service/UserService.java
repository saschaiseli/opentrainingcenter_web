package ch.opentrainingcenter.gui.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.opentrainingcenter.business.dbaccess.AthleteService;
import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.domain.CommonTransferFactory;

@Stateless
public class UserService {
    private static final String DUMMY_EMAIL = "dummy@opentrainingcenter.ch";

    @Inject
    private AthleteService service;

    private Athlete applicationUser;

    public void initAthlete() {
        final Athlete athlete = service.findByEmail(DUMMY_EMAIL);
        if (athlete == null) {
            applicationUser = CommonTransferFactory.createAthlete("Dummy", "Dummy", DUMMY_EMAIL, "password");
            service.doSave(applicationUser);
        } else {
            applicationUser = athlete;
        }

    }

    public long getApplicationUserId() {
        return applicationUser.getId();
    }

}
