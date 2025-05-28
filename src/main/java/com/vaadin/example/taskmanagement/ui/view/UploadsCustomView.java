package com.vaadin.example.taskmanagement.ui.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.streams.UploadEvent;
import com.vaadin.flow.server.streams.UploadHandler;

@Route("custom-uploads-examples")
@PageTitle("Uploads")
@Menu(order = 6, icon = "vaadin:cloud-upload", title = "Custom Upload Examples")
@PermitAll
public class UploadsCustomView extends VerticalLayout {

    public UploadsCustomView() {
        Card card = createCodeSnippetFor("Uploads", "Upload to server into memory",
                """
UploadHandler customUploadHandler = new UploadHandler() {
    @Override
    public void handleUploadRequest(UploadEvent event) {
        try (InputStream inputStream = event.getInputStream()) {
            String fileName = event.getFileName();
            long fileSize = event.getFileSize();
            String contentType = event.getContentType();
            if (validFile(fileName, fileSize, contentType)) {
                // Process the uploaded file
                // ...
            } else {
                event.getUI().access(() ->
                        Notification.show("Invalid file: " + fileName, 3000,
                                Notification.Position.MIDDLE));
            }
        } catch (IOException e) {
            // The thrown exception will be caught and will fire
            // a responseHandled(false, response) method call.
            throw new UncheckedIOException(e);
        }
    }
    @Override
    public void responseHandled(boolean success, VaadinResponse response) {
        // Set your own custom response value for success/fail by overriding this method.
        // Default responses are 200 ok for success and 500 internal server error for failure
        if (success) {
            response.setStatus(200); // OK
        } else {
            response.setStatus(503); // Service Unavailable
        }
    }
    @Override
    public long getRequestSizeMax() {
        // Return the maximum size, in bytes, of a complete request for multipart upload.
        return -1; // -1 means no limit and is the default
    }
    @Override
    public long getFileSizeMax() {
        // Return the maximum size, in bytes, of a single file for multipart upload.
        return -1; // -1 means no limit and is the default
    }
    @Override
    public long getFileCountMax() {
        // Return the maximum amount of files allowed for multipart upload.
        return 10000; // -1 means no limit default is 10000
    }
};
Upload upload = new Upload(customUploadHandler);
                """);

        UploadHandler customUploadHandler = new UploadHandler() {
            @Override
            public void handleUploadRequest(UploadEvent event) {
                try (InputStream inputStream = event.getInputStream()) {
                    String fileName = event.getFileName();
                    long fileSize = event.getFileSize();
                    String contentType = event.getContentType();
                    if (validFile(fileName, fileSize, contentType)) {
                        // Process the uploaded file
                        // ...
                    } else {
                        event.getUI().access(() ->
                                Notification.show("Invalid file: " + fileName, 3000,
                                        Notification.Position.MIDDLE));
                    }
                } catch (IOException e) {
                    // The thrown exception will be caught and will fire
                    // a responseHandled(false, response) method call.
                    throw new UncheckedIOException(e);
                }
            }
            @Override
            public void responseHandled(boolean success, VaadinResponse response) {
                // Set your own custom response value for success/fail by overriding this method.
                // Default responses are 200 ok for success and 500 internal server error for failure
                if (success) {
                    response.setStatus(200); // OK
                } else {
                    response.setStatus(503); // Service Unavailable
                }
            }
            @Override
            public long getRequestSizeMax() {
                // Return the maximum size, in bytes, of a complete request for multipart upload.
                return -1; // -1 means no limit and is the default
            }
            @Override
            public long getFileSizeMax() {
                // Return the maximum size, in bytes, of a single file for multipart upload.
                return -1; // -1 means no limit and is the default
            }
            @Override
            public long getFileCountMax() {
                // Return the maximum amount of files allowed for multipart upload.
                return 10000; // -1 means no limit default is 10000
            }
        };
        Upload upload = new Upload(customUploadHandler);
        card.addToFooter(upload);
    }

    private boolean validFile(String fileName, long fileSize, String contentType) {
        // Implement your file validation logic here
        return true;
    }

    private Card createCodeSnippetFor(String title, String subtitle, String code) {
        SyntaxHighlighter imageLogo = new SyntaxHighlighter(ShLanguage.JAVA, code);
        imageLogo.setShStyle(ShStyle.IDEA);

        Card card = new Card();
        card.setWidth("900px");
        card.setTitle(new Div(title));
        card.setSubtitle(new Div(subtitle));
        card.add(imageLogo);
        card.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL,
                CardVariant.LUMO_COVER_MEDIA
        );
        add(card);
        return card;
    }
}
