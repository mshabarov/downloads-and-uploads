package com.vaadin.example.taskmanagement.ui.view;

import java.io.File;

import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.FileUploadHandler;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.TemporaryFileUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import com.vaadin.flow.server.streams.UploadMetadata;

@Route("upload-examples")
@PageTitle("Uploads")
@Menu(order = 5, icon = "vaadin:upload", title = "Upload Examples")
@PermitAll
public class UploadsExamplesView extends VerticalLayout {

    public UploadsExamplesView() {
        Card card = createCodeSnippetFor("Uploads", "Upload to server into memory",
                """
InMemoryUploadHandler inMemoryHandler = UploadHandler.inMemory(
        (UploadMetadata metadata, byte[] data) -> {
            // Get other information about the file.
            String fileName = metadata.fileName();
            String mimeType = metadata.contentType();
            long contentLength = metadata.contentLength();
            // Do something with the file data...
        });
Upload upload = new Upload(inMemoryHandler);
                """);

        InMemoryUploadHandler inMemoryHandler = UploadHandler.inMemory(
                (UploadMetadata metadata, byte[] data) -> {
                    // Get other information about the file.
                    String fileName = metadata.fileName();
                    String mimeType = metadata.contentType();
                    long contentLength = metadata.contentLength();
                    // Do something with the file data...
                });
        Upload upload = new Upload(inMemoryHandler);
        card.addToFooter(upload);

        card = createCodeSnippetFor("Uploads", "Upload to server into a file",
                """
FileUploadHandler fileHandler = UploadHandler.toFile(
        (UploadMetadata metadata, File file) ->
                System.out.printf("File saved to: %s%n", file.getAbsolutePath()),
        (String fileName) -> new File("/Users/mikhail/Downloads/", fileName));
Upload upload = new Upload(fileHandler);
                """);

        FileUploadHandler fileHandler = UploadHandler.toFile(
                (UploadMetadata metadata, File file) ->
                        System.out.printf("File saved to: %s%n", file.getAbsolutePath()),
                (String fileName) -> new File("/Users/mikhail/Downloads/", fileName));
        Upload upload2 = new Upload(fileHandler);
        card.addToFooter(upload2);

        card = createCodeSnippetFor("Uploads", "Upload to server into a temp file",
                """
TemporaryFileUploadHandler temporaryFileHandler = UploadHandler.toTempFile(
        (UploadMetadata metadata, File file) ->
                System.out.printf("File saved to: %s%n", file.getAbsolutePath())
);
Upload upload = new Upload(temporaryFileHandler);
                """);

        TemporaryFileUploadHandler temporaryFileHandler = UploadHandler.toTempFile(
                (UploadMetadata metadata, File file) ->
                        System.out.printf("File saved to: %s%n", file.getAbsolutePath())
        );
        Upload upload3 = new Upload(temporaryFileHandler);
        card.addToFooter(upload3);
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
