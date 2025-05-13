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
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;


@Route("downloads-examples")
@PageTitle("Downloads")
@Menu(order = 0, icon = "vaadin:download", title = "Download Examples")
@PermitAll
public class DownloadsExamplesView extends VerticalLayout {

    public DownloadsExamplesView(AttachmentRepository attachmentRepository, TaskService taskService) {
        Card card = createCodeSnippetFor("Downloads", "Show an image from class path resources",
                """
var logo = new Image(DownloadHandler.forClassResource(
                  DownloadsView.class, "vaadin.jpeg"), "Vaadin logo");""");

        var logo = new Image(DownloadHandler.forClassResource(
                DownloadsExamplesView.class, "vaadin.jpeg"), "Vaadin logo");
        card.addToFooter(logo);

        card = createCodeSnippetFor("Downloads", "Show an image from file system",
"""
// Download a file from disk
var download = new Anchor(DownloadHandler
        .forFile(new File("/Users/mikhail/Documents/terms-of-service.md"))
             .whenComplete(success -> Notification.show("Success: " + success)),
                 "Download terms of service");
""");

        var download = new Anchor(DownloadHandler.forFile(new File("/Users/mikhail/Documents/terms-of-service.md"))
                .whenComplete(success -> Notification.show("Success: " + success)),
                "Download terms of service");
        card.addToFooter(download);

        card = createCodeSnippetFor("Downloads", "Download from database",
"""
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
        card.addToFooter(downloadAttachment);

        card = createCodeSnippetFor("Downloads", "Download CSV file",
"""
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
        card.addToFooter(downloadCSV);
    }

    private long getAttachmentId() {
        return 1L;
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
