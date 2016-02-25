package ch.opentrainingcenter.controller;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import ch.opentrainingcenter.data.menu.TrainingLoadingDelegator;
import ch.opentrainingcenter.model.Training;

@ManagedBean
@ViewScoped
public class CalenderMenuView implements Serializable {

    private static final long serialVersionUID = 1L;
    private ScheduleModel lazyEventModel;

    @Inject
    private TrainingLoadingDelegator producer;

    @PostConstruct
    public void init() {
        lazyEventModel = new LazyScheduleModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public void loadEvents(final Date start, final Date end) {
                for (final Training training : producer.getTrainings()) {
                    final Date date = new Date(training.getDatum());
                    final DefaultScheduleEvent event = new DefaultScheduleEvent("A", date, date, true);
                    addEvent(event);
                }
            }
        };
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
}
