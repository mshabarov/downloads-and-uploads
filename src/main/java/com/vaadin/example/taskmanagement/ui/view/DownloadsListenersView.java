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


@Route("download-listeners")
@PageTitle("Downloads Listeners")
@Menu(order = 1, icon = "vaadin:cloud-download-o", title = "Download Listeners")
@PermitAll
public class DownloadsListenersView extends VerticalLayout {

    private static final int SLOW_CONTENT_SIZE = 1024 * 1024 * 10; // 10 MB

    public DownloadsListenersView() {
        add(new H4("Show the progress of the download"));
        // Step 1: Use the progress bar to show the download of the given input stream data.
        ProgressBar progressBar = new ProgressBar();
        progressBar.setVisible(false);
        progressBar.setWidth("80%");
        add(progressBar);

        InputStream inputStream = getSlowInputStream();
        // put your code here...
    }

    private InputStream getSlowInputStream() {
        byte[] data = new byte[SLOW_CONTENT_SIZE];
        new java.security.SecureRandom().nextBytes(data);
        return new ByteArrayInputStream(data) {
            @Override
            public synchronized int read(byte[] b, int off, int len) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return super.read(b, off, len);
            }
        };
    }
}
