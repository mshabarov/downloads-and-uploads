package com.vaadin.example.taskmanagement.ui.view;

import jakarta.annotation.security.PermitAll;

import com.vaadin.example.taskmanagement.domain.AttachmentRepository;
import com.vaadin.example.taskmanagement.service.TaskService;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("upload")
@PageTitle("Uploads")
@Menu(order = 2, icon = "vaadin:upload", title = "Upload Examples")
@PermitAll
public class UploadExamplesView extends VerticalLayout {

    public UploadExamplesView(AttachmentRepository attachmentRepository, TaskService taskService) {
        add(new H4("Have Upload receive data into memory"));
        // Step 1: Store upload data into memory and print content as text.

        // put your code here...

        add(new H4("Have Upload store received data into a File"));
        // Step 2: Store uploaded data into a File on the system

        // put your code here...

        add(new H4("Have Upload store received data into a temporary file"));
        // Step 3: Store uploaded data into a temporary file

        // put your code here...

        add(new H4("Create a custom handler for receiving upload"));
        // Step 4: Generate a custom handler to receive data as a String and show it in a TextArea

        // put your code here...
    }

}
