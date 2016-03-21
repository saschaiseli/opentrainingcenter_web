package ch.opentrainingcenter.controller;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.data.menu.TrainingLoadingDelegator;
import ch.opentrainingcenter.model.Training;

@ManagedBean
@SessionScoped
public class CalenderMenuView implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalenderMenuView.class);

    private static final long serialVersionUID = 1L;
    private ScheduleModel lazyEventModel;

    @Inject
    private TrainingLoadingDelegator producer;

    private ScheduleEvent event;

    @PostConstruct
    public void init() {
        lazyEventModel = new LazyScheduleModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public void loadEvents(final Date start, final Date end) {
                LOGGER.info("Load Trainings: from {} to {}", start, end);
                for (final Training training : producer.getTrainings()) {
                    final Date date = training.getDateOfStart();
                    final DefaultScheduleEvent event = new DefaultScheduleEvent("A", date, date, true);
                    addEvent(event);
                }
            }
        };
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(final ScheduleEvent event) {
        this.event = event;
    }

    public void onEventSelect(final SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public ScheduleModel getLazyEventModel() {
        LOGGER.info("Anzahl Events: {}", lazyEventModel.getEventCount());
        return lazyEventModel;
    }
}
