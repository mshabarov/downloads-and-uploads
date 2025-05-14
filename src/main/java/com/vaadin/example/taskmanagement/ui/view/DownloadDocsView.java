package com.vaadin.example.taskmanagement.ui.view;

import java.io.IOException;
import java.io.InputStream;

import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("download-docs")
@PageTitle("Downloads Docs")
@Menu(order = 4, icon = "vaadin:book", title = "Download Docs")
@PermitAll
public class DownloadDocsView extends VerticalLayout {
    public DownloadDocsView() {
        try (InputStream code = this.getClass().getResourceAsStream("download-handler-api.adoc")) {
            if (code == null) {
                throw new IOException("Docs not found");
            }
            String codeAsString = new String(code.readAllBytes());
            SyntaxHighlighter docs = new SyntaxHighlighter(ShLanguage.ASCIIDOC, codeAsString);
            docs.setShStyle(ShStyle.DEFAULTSTYLE);
            docs.setWrapLongLines(true);

            Card card = new Card();
            card.setWidth("1100px");
            card.setTitle(new Div("Download Handler API"));
            card.setSubtitle(new Div("Documentation draft"));
            card.add(docs);
            card.addThemeVariants(
                    CardVariant.LUMO_OUTLINED,
                    CardVariant.LUMO_ELEVATED,
                    CardVariant.LUMO_HORIZONTAL,
                    CardVariant.LUMO_COVER_MEDIA
            );
            add(card);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load docs", e);
        }
    }
}
