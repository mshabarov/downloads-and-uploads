package com.vaadin.example.taskmanagement.ui.view;

import java.io.File;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;


@Route("syntax-highlighter")
@PageTitle("Downloads")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Downloads")
@PermitAll
public class DownloadsView extends VerticalLayout {

    public DownloadsView() {
        createCodeSnippetFor("""
// Show an image from class path resources
Image logo = new Image(DownloadHandler.forClassResource(
DownloadsView.class, "vaadin.jpeg"), "Vaadin logo");""");

        Image logo = new Image(DownloadHandler.forClassResource(
                DownloadsView.class, "vaadin.jpeg"), "Vaadin logo");
        add(logo);

        createCodeSnippetFor("""
// Download a file from disk
Anchor download = new Anchor(DownloadHandler.forFile(new File("/Users/mikhail/Documents/terms-of-service.md"))
        .whenComplete(success -> Notification.show("Success: " + success)),
                "Download terms of service");
""");

        // Download a File and show a notification when completed (requires @Push)
        Anchor download = new Anchor(DownloadHandler.forFile(new File("/Users/mikhail/Documents/terms-of-service.md"))
                .whenComplete(success -> Notification.show("Success: " + success)),
                "Download terms of service");
        add(download);
    }

    private void createCodeSnippetFor(String code) {
        SyntaxHighlighter imageLogo = new SyntaxHighlighter(ShLanguage.JAVA, code);
        imageLogo.setShStyle(ShStyle.IDEA);
        add(imageLogo);
    }
}
