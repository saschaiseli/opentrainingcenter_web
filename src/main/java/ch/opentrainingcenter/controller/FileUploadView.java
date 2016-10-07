package ch.opentrainingcenter.controller;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;
import ch.opentrainingcenter.service.fileconverter.fit.ConvertFitEJB;
import ch.opentrainingcenter.util.Events.Added;
import ch.opentrainingcenter.util.Events.Deleted;

@ManagedBean
public class FileUploadView {

    private static final String SUCCESS = "Upload des Files '%s' war erfolgreich: Dauer %s [ms]";
    private static final String CONVERT_ERROR = "Fehler beim konvertieren des Files '%s' mit der Meldung '%s'";
    private static final String DB_ERROR = "Datenbankfehler: File '%s' konnte nicht gespeichert werden. Meldung: '%s'";

    private UploadedFile file;

    @Inject
    private ConvertFitEJB convert;

    @Inject
    private MyApplicationScope scope;

    @Inject
    private TrainingService trainingService;

    @Inject
    @Added
    private Event<Training> eventAddManager;

    @Inject
    @Deleted
    private Event<Training> eventDeleteManager;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(final UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(final FileUploadEvent event) {
        FacesMessage message = null;
        Training training = null;
        final String fileName = event.getFile().getFileName();
        try {
            training = convert.convert(event.getFile().getInputstream());
            training.setDateOfImport(DateTime.now().toDate());
            training.setAthlete(scope.getApplicationUser());
            final long startTime = System.currentTimeMillis();
            eventAddManager.fire(training);
            trainingService.doSave(training);
            final long estimatedTime = System.currentTimeMillis() - startTime;
            message = new FacesMessage(String.format(SUCCESS, fileName, estimatedTime));
        } catch (final IOException e) {
            message = new FacesMessage(String.format(CONVERT_ERROR, fileName, e.getMessage()));
        } catch (final Exception e) {
            message = new FacesMessage(String.format(DB_ERROR, fileName, e.getMessage()));
            eventDeleteManager.fire(training);
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}