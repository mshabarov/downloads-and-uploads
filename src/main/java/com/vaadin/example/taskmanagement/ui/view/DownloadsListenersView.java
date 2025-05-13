package com.vaadin.example.taskmanagement.ui.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;


@Route("download-listeners")
@PageTitle("Downloads Listeners")
@Menu(order = 1, icon = "vaadin:cloud-download-o", title = "Download Listeners")
@PermitAll
public class DownloadsListenersView extends VerticalLayout {

    private static final int SLOW_CONTENT_SIZE = 1024 * 1024 * 10; // 10 MB

    public DownloadsListenersView() {
        Card card = createCodeSnippetFor("Downloads", "Use shortcut methods for download progress listening",
                """
InputStreamDownloadHandler handler = DownloadHandler.fromInputStream(event -> new DownloadResponse(
                getSlowInputStream(), "slow-download.bin", "application/octet-stream", SLOW_CONTENT_SIZE))
        .whenStart(() -> {
            Notification.show("Download started", 3000, Notification.Position.BOTTOM_START);
            progressBar.setVisible(true);
        })
        .onProgress((transferred, total) -> progressBar.setValue((double) transferred / total))
        .whenComplete(success -> {
            progressBar.setVisible(false);
            if (success) {
                Notification.show("Download completed", 3000, Notification.Position.BOTTOM_START).addThemeVariants(
                        NotificationVariant.LUMO_SUCCESS);
            } else {
                Notification.show("Download failed", 3000, Notification.Position.BOTTOM_START)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });""");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setVisible(false);
        progressBar.setWidth("80%");

        // todo: int total size -> long
        // todo: -1 is passed into total size
        InputStreamDownloadHandler handler = DownloadHandler.fromInputStream(event -> new DownloadResponse(
                        getSlowInputStream(), "slow-download.bin", "application/octet-stream", SLOW_CONTENT_SIZE))
                .whenStart(() -> {
                    Notification.show("Download started", 3000, Notification.Position.BOTTOM_START);
                    progressBar.setVisible(true);
                })
                .onProgress((transferred, total) -> {
//                    Notification.show("Download progress: " + transferred + "/" + SLOW_CONTENT_SIZE, 3000, Notification.Position.BOTTOM_START);
                    progressBar.setValue((double) transferred / SLOW_CONTENT_SIZE);
                })
                .whenComplete(success -> {
                    progressBar.setVisible(false);
                    if (success) {
                        Notification.show("Download completed", 3000, Notification.Position.BOTTOM_START).addThemeVariants(
                                NotificationVariant.LUMO_SUCCESS);
                    } else {
                        Notification.show("Download failed", 3000, Notification.Position.BOTTOM_START)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                });
        var slow = new Anchor(handler, "Start slow download");
        card.addToFooter(slow);
        card.addToFooter(progressBar);
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

    private Card createCodeSnippetFor(String title, String subtitle, String code) {
        SyntaxHighlighter imageLogo = new SyntaxHighlighter(ShLanguage.JAVA, code);
        imageLogo.setShStyle(ShStyle.IDEA);

        Card card = new Card();
        card.setWidthFull();
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
