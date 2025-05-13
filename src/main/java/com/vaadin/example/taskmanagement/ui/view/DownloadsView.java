package com.vaadin.example.taskmanagement.ui.view;

import java.io.File;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;

import com.vaadin.example.taskmanagement.domain.Attachment;
import com.vaadin.example.taskmanagement.domain.AttachmentRepository;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;


@Route("syntax-highlighter")
@PageTitle("Downloads")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Downloads")
@PermitAll
public class DownloadsView extends VerticalLayout {

    public DownloadsView(AttachmentRepository attachmentRepository) {
        createCodeSnippetFor("""
// Show an image from class path resources
var logo = new Image(DownloadHandler.forClassResource(
DownloadsView.class, "vaadin.jpeg"), "Vaadin logo");""");

        var logo = new Image(DownloadHandler.forClassResource(
                DownloadsView.class, "vaadin.jpeg"), "Vaadin logo");
        add(logo);

        createCodeSnippetFor("""
// Download a file from disk
var download = new Anchor(DownloadHandler.forFile(new File("/Users/mikhail/Documents/terms-of-service.md"))
        .whenComplete(success -> Notification.show("Success: " + success)),
                "Download terms of service");
""");

        // Download a File and show a notification when completed (requires @Push)
        var download = new Anchor(DownloadHandler.forFile(new File("/Users/mikhail/Documents/terms-of-service.md"))
                .whenComplete(success -> Notification.show("Success: " + success)),
                "Download terms of service");
        add(download);

        createCodeSnippetFor("""
long attachmentId = getAttachmentId();
var downloadAttachment = new Anchor(DownloadHandler.fromInputStream((event) -> {
    try {
        Attachment attachment = attachmentRepository.findById(attachmentId);
        return new DownloadResponse(attachment.getData().getBinaryStream(),
                attachment.getName(), attachment.getMime(), attachment.getSize());
    } catch (Exception e) {
        return DownloadResponse.error(500);
    }
}, "task-attachment-" + attachmentId + ".txt"), "Download task attachment");
                """);

        long attachmentId = getAttachmentId();
        var downloadAttachment = new Anchor(DownloadHandler.fromInputStream((event) -> {
            try {
                Attachment attachment = attachmentRepository.findById(attachmentId);
                return new DownloadResponse(attachment.getData().getBinaryStream(),
                        attachment.getName(), attachment.getMime(), attachment.getSize());
            } catch (Exception e) {
                return DownloadResponse.error(500);
            }
        }, "task-attachment-" + attachmentId + ".txt"), "Download task attachment");
        add(downloadAttachment);
    }

    private long getAttachmentId() {
        return 1L;
    }

    private void createCodeSnippetFor(String code) {
        SyntaxHighlighter imageLogo = new SyntaxHighlighter(ShLanguage.JAVA, code);
        imageLogo.setShStyle(ShStyle.IDEA);
        add(imageLogo);
    }
}
