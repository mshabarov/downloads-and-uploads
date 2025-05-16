package com.vaadin.example.taskmanagement.ui.view;

import java.io.OutputStream;
import java.util.List;

import jakarta.annotation.security.PermitAll;
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


@Route("upload")
@PageTitle("Upload")
@Menu(order = 0, icon = "vaadin:upload", title = "Upload Examples")
@PermitAll
public class UploadExamplesView extends VerticalLayout {

    public UploadExamplesView(AttachmentRepository attachmentRepository, TaskService taskService) {
        add(new H4("Have Upload receive data into memory"));
        // Step 1: Store upload data into memory and print conten as text.

        // put your code here...

        add(new H4("Have Upload store received data into a File"));
        // Step 2: Store uploaded data into a File on the system

        // put your code here...

        add(new H4("Have Upload store received data into a temporary file"));
        // Step 3: Store uploaded data into a temporary file

        // put your code here...

        add(new H4("Create a custom handler for receiving upload"));
        // Step 4: Generate a UploadHandler for receiving data from the client

        // put your code here...
    }

    private long getAttachmentId() {
        return 1L;
    }
}
