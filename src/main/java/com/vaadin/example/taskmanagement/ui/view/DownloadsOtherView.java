package com.vaadin.example.taskmanagement.ui.view;

import com.flowingcode.vaadin.addons.syntaxhighlighter.ShLanguage;
import com.flowingcode.vaadin.addons.syntaxhighlighter.ShStyle;
import com.flowingcode.vaadin.addons.syntaxhighlighter.SyntaxHighlighter;
import jakarta.annotation.security.PermitAll;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.DisabledUpdateMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadEvent;
import com.vaadin.flow.server.streams.DownloadHandler;


@Route("download-other")
@PageTitle("Downloads Other")
@Menu(order = 2, icon = "vaadin:cloud-download", title = "Download Other")
@PermitAll
public class DownloadsOtherView extends VerticalLayout {

    private Dialog serverSideModalDialog;
    private ToggleButton inertToggle;
    private ToggleButton disabledToggle;
    private ToggleButton allowDisabledToggle;

    public DownloadsOtherView() {
        createCodeSnippetFor("Downloads", "Control inert for download component",
                """
Anchor downloadLink = new Anchor(new DownloadHandler() {
    @Override
    public void handleDownloadRequest(DownloadEvent event) {
        // download handling...
    }
    
    @Override
    public boolean allowInert() {
        return inertToggle.getValue();
    
    @Override
    public DisabledUpdateMode getDisabledUpdateMode() {
        return allowDisabledToggle.getValue() ? DisabledUpdateMode.ALWAYS :
                DisabledUpdateMode.ONLY_WHEN_ENABLED;
    }
    
    @Override
    public String getUrlPostfix() {
        return "empty-download.txt";
    }
}, "Download me!");
                """);

        add(new Html("</br>"));
        addServersideModalDialogSection();
        addShowNotificationButton();

        Anchor downloadLink = new Anchor(new DownloadHandler() {
            @Override
            public void handleDownloadRequest(DownloadEvent event) {
                // download handling...
            }

            @Override
            public boolean allowInert() {
                return inertToggle.getValue();
            }

            @Override
            public String getUrlPostfix() {
                return "empty-download.txt";
            }

            @Override
            public DisabledUpdateMode getDisabledUpdateMode() {
                return allowDisabledToggle.getValue() ? DisabledUpdateMode.ALWAYS :
                        DisabledUpdateMode.ONLY_WHEN_ENABLED;
            }
        }, "Download me!");
        add(downloadLink);

        inertToggle = new ToggleButton("Allow inert download");
        add(inertToggle);

        add(new Html("</br>"));
        add(new H3("Downloads when the owner component is disabled:"));
        add(new Html("<span>Download handling on the server-side is rejected when the owner component is disabled. " +
                     "This is by default but can be changed to by pass it and allow handling download request even in that case.</span>"));

        allowDisabledToggle = new ToggleButton("Allow disabled download");
        add(allowDisabledToggle);

        disabledToggle = new ToggleButton("Disable download link",
                event -> downloadLink.setEnabled(!event.getValue()));
        add(disabledToggle);
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

    private void addShowNotificationButton() {
        Button showNotification = new Button("Always inert button", e -> Notification.show("Notification !!!"));
        showNotification.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL, KeyModifier.ALT);
        add(showNotification);
    }

    private void addServersideModalDialogSection() {
        add(new H3("Downloads with server-side modal component (Does not show Modality Curtain):"));
        add(new Html("<span>By pressing the 'Open Server-side Modal Dialog' a dialog with no client-side modality " +
                     "opens that blocks user interactions not using a modality curtain on the view, but via a so called " +
                     "server-side modality curtain. This means that while this dialog is open, user can interact with the " +
                     "download link and button on the underlying page, but the server-side would reject the request. " +
                     "Once toggle button is pressed, the server-side allows download link handling.</span>"));
        initServerSideModalDialog();
        add(createOpenServersideModalDialogButton());
    }

    private void initServerSideModalDialog() {
        serverSideModalDialog = createDialog();
        serverSideModalDialog.setModal(false); // This makes it client-side modeless -> no client-side modality curtain
//        HorizontalLayout btnLayout = new HorizontalLayout();
//        btnLayout.setJustifyContentMode(JustifyContentMode.END);
//        btnLayout.add(new Button("Close", e -> {
//            serverSideModalDialog.close();
            // Remove the server-side modality curtain:
//            getUI().ifPresent(ui -> ui.remove(serverSideModalDialog));
//        }));
//        serverSideModalDialog.add(btnLayout);
    }

    private Button createOpenServersideModalDialogButton() {
        Button openServerSideModalDialog = new Button("Open Server-side Modal Dialog", e -> {
            serverSideModalDialog.open();
            getUI().ifPresent(ui -> ui.addModal(serverSideModalDialog));
        });
        openServerSideModalDialog.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return openServerSideModalDialog;
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New task");
        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> {
            getUI().ifPresent(ui -> ui.remove(dialog));
            dialog.close();
        });
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        return dialog;
    }

    private VerticalLayout createDialogLayout() {
        TextField description = new TextField("Description");
        TextField notes = new TextField("Notes");
        VerticalLayout dialogLayout = new VerticalLayout(description, notes);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> {
            getUI().ifPresent(ui -> ui.remove(dialog));
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }
}
