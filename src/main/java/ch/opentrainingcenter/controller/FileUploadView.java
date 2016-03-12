package ch.opentrainingcenter.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ch.opentrainingcenter.model.Training;
import ch.opentrainingcenter.service.TrainingService;
import ch.opentrainingcenter.service.fileconverter.fit.ConvertFitEJB;

@ManagedBean
public class FileUploadView {

    private UploadedFile file;

    @Inject
    private ConvertFitEJB convert;

    @Inject
    private TrainingService trainingService;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(final UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(final FileUploadEvent event) {
        FacesMessage message = null;
        try {
            final Training training = convert.convert(event.getFile().getInputstream());
            final long startTime = System.currentTimeMillis();
            trainingService.doSave(training);
            final long estimatedTime = System.currentTimeMillis() - startTime;
            message = new FacesMessage("SUCCESS", event.getFile().getFileName() + " uploaded in " + estimatedTime + "[ms]");
        } catch (final Exception e) {
            message = new FacesMessage("ERROR", event.getFile().getFileName() + " " + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}