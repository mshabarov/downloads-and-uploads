package com.vaadin.example.taskmanagement.ui.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
import com.vaadin.flow.server.streams.TransferContext;
import com.vaadin.flow.server.streams.TransferProgressListener;


@Route("download-custom")
@PageTitle("Downloads Custom")
@Menu(order = 2, icon = "vaadin:cloud-download-o", title = "Download Custom")
@PermitAll
public class DownloadsCustomView extends VerticalLayout {

    private static final AtomicLong numberOfDownloads = new AtomicLong(0);

    public DownloadsCustomView() {
        Card card = createCodeSnippetFor("Downloads", "Custom downloads",
                """
LinkWithM5Validation link = new LinkWithM5Validation(event -> {
    try {
        var data = loadFileFromS3(event.getFileName(), event.getContentType());
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(data);
        String base64Md5 = Base64.getEncoder().encodeToString(digest);
        event.getResponse().setHeader("Content-MD5", base64Md5);
        event.getResponse().getOutputStream().write(data);
        event.getUI().access(() -> Notification.show(
                "Download from S3 completed, number of downloads: " +\s
                    numberOfDownloads.incrementAndGet()));
        event.getSession().setAttribute("downloads-number-" + event.getFileName(),\s
                    numberOfDownloads.get());
    } catch (NoSuchAlgorithmException | IOException e) {
        event.getResponse().setStatus(500);
    }
}, "Download from S3");

 private static class LinkWithM5Validation extends Anchor {
     // JS customizations in <a> for checksum checking on the client-side
 }
                        """);


        LinkWithM5Validation link = new LinkWithM5Validation(event -> {
            try {
                var data = loadFileFromS3(event.getFileName(), event.getContentType());
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] digest = md5.digest(data);
                String base64Md5 = Base64.getEncoder().encodeToString(digest);
                event.getResponse().setHeader("Content-MD5", base64Md5);
                event.getResponse().getOutputStream().write(data);
                event.getUI().access(() -> Notification.show(
                        "Download from S3 completed, number of downloads: " +
                            numberOfDownloads.incrementAndGet()));
                event.getSession().lock();
                try {
                    event.getSession().setAttribute("downloads-number-" + event.getFileName(),
                            numberOfDownloads.get());
                } finally {
                    event.getSession().unlock();
                }
            } catch (NoSuchAlgorithmException | IOException e) {
                event.getResponse().setStatus(500);
            }
        }, "Download from S3");
        card.addToFooter(link);
    }

    private byte[] loadFileFromS3(String fileName, String contentType) {
        byte[] bytes = new byte[1024];
        new java.security.SecureRandom().nextBytes(bytes);
        return bytes;
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

    private static class LinkWithM5Validation extends Anchor {
        public LinkWithM5Validation(DownloadHandler downloadHandler, String text) {
            super(downloadHandler, text);
        }

        // JS customizations in <a> for checksum checking on the client-side
    }
}
