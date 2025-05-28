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
import com.vaadin.flow.server.streams.TransferContext;
import com.vaadin.flow.server.streams.TransferProgressListener;
import com.vaadin.flow.server.streams.UploadEvent;
import com.vaadin.flow.server.streams.UploadHandler;

@Route("uploads-listeners-examples")
@PageTitle("Uploads")
@Menu(order = 7, icon = "vaadin:cloud-upload-o", title = "Upload Listener Examples")
@PermitAll
public class UploadsListenersView extends VerticalLayout {

    public UploadsListenersView() {
        Card card = createCodeSnippetFor("Uploads", "Listen for upload events",
                """
 Upload upload = new Upload(UploadHandler.toTempFile(
         (metadata, file) ->
                 System.out.printf("File saved to: %s%n",
                         file.getAbsolutePath()))
         .whenStart(() -> Notification.show("Upload started"))
         .whenComplete((success) -> {
             if (success) {
                 Notification.show("Upload completed");
             } else {
                 Notification.show("Upload failed");
             }
         }));
                """);

        Upload upload = new Upload(UploadHandler.toTempFile(
                (metadata, file) ->
                        System.out.printf("File saved to: %s%n",
                                file.getAbsolutePath()))
                .whenStart(() -> Notification.show("Upload started"))
                .whenComplete((success) -> {
                    if (success) {
                        Notification.show("Upload completed");
                    } else {
                        Notification.show("Upload failed");
                    }
                }));
        card.addToFooter(upload);

        card = createCodeSnippetFor("Uploads", "Listen for upload events",
                """
TransferProgressListener progressListener = new TransferProgressListener() {
            @Override
            public void onStart(TransferContext context) {
                Notification.show("Upload started for file: " + context.fileName());
            }
            @Override
            public void onProgress(TransferContext context,
                                   long transferredBytes, long totalBytes) {
                // handle progress updates
            }
            @Override
            public void onComplete(TransferContext context,
                                   long transferredBytes) {
                Notification.show("Upload completed: " + transferredBytes + " bytes transferred");
            }
            @Override
            public void onError(TransferContext context,
                                IOException reason) {
                Notification.show("Upload failed: " + reason.getMessage());
            }
        };
Upload upload = new Upload(UploadHandler.toTempFile(
        (metadata, file) -> {}, progressListener));
                """);

        TransferProgressListener progressListener = new TransferProgressListener() {
            @Override
            public void onStart(TransferContext context) {
                Notification.show("Upload started for file: " + context.fileName());
            }
            @Override
            public void onProgress(TransferContext context,
                                   long transferredBytes, long totalBytes) {
                // handle progress updates
            }
            @Override
            public void onComplete(TransferContext context,
                                   long transferredBytes) {
                Notification.show("Upload completed: " + transferredBytes + " bytes transferred");
            }
            @Override
            public void onError(TransferContext context,
                                IOException reason) {
                Notification.show("Upload failed: " + reason.getMessage());
            }
        };
        Upload upload2 = new Upload(UploadHandler.toTempFile(
                (metadata, file) -> {},
                    progressListener));
        card.addToFooter(upload2);
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
