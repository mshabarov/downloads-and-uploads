package com.vaadin.example.taskmanagement.ui.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.annotation.security.PermitAll;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.PageRequest;

import com.vaadin.example.taskmanagement.domain.Attachment;
import com.vaadin.example.taskmanagement.domain.AttachmentRepository;
import com.vaadin.example.taskmanagement.domain.Task;
import com.vaadin.example.taskmanagement.service.TaskService;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("")
@PageTitle("Downloads")
@Menu(order = 0, icon = "vaadin:download", title = "Download Examples")
@PermitAll
public class DownloadsExamplesView extends VerticalLayout {

    public DownloadsExamplesView(AttachmentRepository attachmentRepository, TaskService taskService) {
        add(new H4("Render an image from classpath resource"));
        // Step 1: Render the image "vaadin.jpeg" from the classpath on this view.

        // put your code here...

        add(new H4("Download a file from file system"));
        // Step 2: Download the file "terms-of-service.md" that is at the root of this project.

        // put your code here...

        add(new H4("Download a given attachment as text file"));
        // Step 3: Download the content of this attachment in a file.
        //         Set the file name format as "attachment-{id}.txt"
        long attachmentId = getAttachmentId();
        Attachment attachment = attachmentRepository.findById(attachmentId);

        // put your code here...

        add(new H4("Download the task list as CSV"));
        // Step 4: Pre-requisite: Add a task on the Task List view.
        //         Test: Download the tasks as CSV using the pre-made code snippet below.
        //         You need to choose OutputStream to write data to.
        //         (Then run this code snippet when user triggers the download.)

        List<Task> tasks = taskService.list(PageRequest.of(0, 10));

        /* Set up the OutputStream you may write data to */
        OutputStream out = null;

        // use the commented code below to write the data to the OutputStream
        /*
        try (OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
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
        */
    }

    private long getAttachmentId() {
        return 1L;
    }
}
