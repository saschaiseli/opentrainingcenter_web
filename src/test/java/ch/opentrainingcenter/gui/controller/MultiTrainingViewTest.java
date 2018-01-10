package ch.opentrainingcenter.gui.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.opentrainingcenter.business.service.GTrainingService;
import ch.opentrainingcenter.gui.model.GTraining;

@Ignore
public class MultiTrainingViewTest {

    private MultiTrainingView view;
    private GTrainingService service;

    @Before
    public void setUp() {
        view = new MultiTrainingView();
        service = mock(GTrainingService.class);
        // view.setService(service);
    }

    @Test
    public void testWithEmptyList() {
        final List<GTraining> selected = new ArrayList<>();
        view.onSelection(selected);

        verify(service).loadMultiTrainings(Collections.emptyList());
    }

    @Test
    public void test_OnSelection_WithOneTraining() {
        final List<GTraining> selected = new ArrayList<>();
        final GTraining training = mock(GTraining.class);
        final List<Long> ids = new ArrayList<>();
        final long id = 42L;
        ids.add(id);
        when(training.getStartInMillis()).thenReturn(id);
        selected.add(training);

        view.onSelection(selected);

        verify(service).loadMultiTrainings(ids);
    }

    @Test
    public void test_OnSelection_WithTreeTraining() {
        final List<GTraining> selected = new ArrayList<>();
        final GTraining training1 = mock(GTraining.class);
        final GTraining training2 = mock(GTraining.class);
        final GTraining training3 = mock(GTraining.class);

        final List<Long> ids = new ArrayList<>();
        final long id1 = 42L;
        final long id2 = 42L;
        final long id3 = 42L;

        ids.add(id1);
        ids.add(id2);
        ids.add(id3);

        when(training1.getStartInMillis()).thenReturn(id1);
        when(training2.getStartInMillis()).thenReturn(id2);
        when(training3.getStartInMillis()).thenReturn(id3);

        selected.add(training1);
        selected.add(training2);
        selected.add(training3);

        view.onSelection(selected);

        verify(service).loadMultiTrainings(ids);
    }
}
