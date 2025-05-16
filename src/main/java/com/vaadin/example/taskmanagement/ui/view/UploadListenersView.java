package com.vaadin.example.taskmanagement.ui.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.annotation.security.PermitAll;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("upload-listeners")
@PageTitle("Upload Listeners")
@Menu(order = 1, icon = "vaadin:cloud-upload-o", title = "Upload Listeners")
@PermitAll
public class UploadListenersView extends VerticalLayout {

    private static final int SLOW_CONTENT_SIZE = 1024 * 1024 * 10; // 10 MB

    public UploadListenersView() {
        add(new H4("Show the progress of the upload"));
        // Step 1: Use the progress bar to show the upload of the data.
        ProgressBar progressBar = new ProgressBar();
        progressBar.setVisible(false);
        progressBar.setWidth("80%");
        add(progressBar);

        // put your code here...
    }

}
