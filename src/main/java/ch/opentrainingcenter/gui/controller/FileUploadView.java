package ch.opentrainingcenter.gui.controller;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ch.opentrainingcenter.business.service.GTrainingService;
import ch.opentrainingcenter.gui.controller.Events.Added;
import ch.opentrainingcenter.gui.controller.Events.Deleted;
import ch.opentrainingcenter.gui.model.GTraining;

@ManagedBean
public class FileUploadView {

    private static final String SUCCESS = "Upload des Files '%s' war erfolgreich: Dauer %s [ms]";
    private static final String CONVERT_ERROR = "Fehler beim konvertieren des Files '%s' mit der Meldung '%s'";
    private static final String DB_ERROR = "Datenbankfehler: File '%s' konnte nicht gespeichert werden. Meldung: '%s'";

    private UploadedFile file;

    @Inject
    GTrainingService gService;

    @Inject
    @Added
    private Event<GTraining> eventAddManager;

    @Inject
    @Deleted
    private Event<GTraining> eventDeleteManager;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(final UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(final FileUploadEvent event) {
        System.out.println("sdfsdfsdf");
        FacesMessage message = null;
        GTraining gTraining = null;
        final String fileName = event.getFile().getFileName();
        try {
            final long startTime = System.currentTimeMillis();
            gTraining = gService.storeGpsFile(event.getFile().getInputstream());
            eventAddManager.fire(gTraining);
            final long estimatedTime = System.currentTimeMillis() - startTime;
            message = new FacesMessage(String.format(SUCCESS, fileName, estimatedTime));
        } catch (final IOException e) {
            message = new FacesMessage(String.format(CONVERT_ERROR, fileName, e.getMessage()));
        } catch (final Exception e) {
            message = new FacesMessage(String.format(DB_ERROR, fileName, e.getMessage()));
            eventDeleteManager.fire(gTraining);
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}