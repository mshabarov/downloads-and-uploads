package com.vaadin.example.taskmanagement.ui.view;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.PageRequest;

import com.vaadin.example.taskmanagement.domain.Attachment;
import com.vaadin.example.taskmanagement.domain.AttachmentRepository;
import com.vaadin.example.taskmanagement.domain.Task;
import com.vaadin.example.taskmanagement.service.TaskService;
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

    public DownloadsView(AttachmentRepository attachmentRepository, TaskService taskService) {
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

        createCodeSnippetFor("""
var downloadCSV = new Anchor(event -> {
    List<Task> tasks = taskService.list(PageRequest.of(0, 10));
    try (OutputStream out = event.getOutputStream();
         OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
         CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT
                 .withHeader("ID", "Description", "Creation Date"))) {
        for (Task task : tasks) {
            csv.printRecord(task.getId(), task.getDescription(),
                    task.getCreationDate().toString());
        }
        csv.flush();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}, "Download tasks as CSV");
                """);

        var downloadCSV = new Anchor(event -> {
            List<Task> tasks = taskService.list(PageRequest.of(0, 10));
            try (OutputStream out = event.getOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                 CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT
                         .withHeader("ID", "Description", "Creation Date"))) {
                for (Task task : tasks) {
                    csv.printRecord(task.getId(), task.getDescription(),
                            task.getCreationDate().toString());
                }
                csv.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "Download tasks as CSV");
        add(downloadCSV);
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
