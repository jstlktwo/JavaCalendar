package com.beginsecure.javafxapp.javaapp;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import javafx.stage.Stage;


public class NotesScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeBtn;

    @FXML
    private TextArea enterArticle;
    @FXML
    private TextArea enterNote;

    @FXML
    private Button saveBtn;

    @FXML
    private Label enteredData;

    public static String pathname = "txtNotes/";

    public static int getLineCountByReader() throws IOException {//получение номера заметки
        try (var lnr = new LineNumberReader(new BufferedReader(new FileReader(pathname + MainController.getId() + ".txt")))) {
            while (lnr.readLine() != null);
            return lnr.getLineNumber();
        }
    }

    public void getArticleAndNote() throws Exception{//сохранение заметки
        String stringArticle = String.valueOf(enterArticle.getText());
        String stringNote = String.valueOf(enterNote.getText());

        try {
            File file = new File(pathname + MainController.getId() + ".txt");
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File has already been created");
            }
        } catch (IOException e) {
            System.out.println("Error creating the file: "+e);
            e.printStackTrace();
        }

        try {
            String id = MainController.getId();
            int lineNumber = getLineCountByReader();

            NoteStructure note = new NoteStructure(lineNumber+1, id, stringArticle, stringNote);
            System.out.println(pathname + MainController.getId() + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathname + MainController.getId() + ".txt", true));
            System.out.println(getLineCountByReader());
            writer.write(note.getInfo());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving in file");
            e.printStackTrace();
        }

    }


    @FXML
    void initialize() {
    }
    public void closeNotesOnClick() throws IOException {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Java App Calendar!");
        stage.setScene(scene);
        stage.show();
    }
    public void backNotesOnClick() throws IOException {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("notesList.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Java App Calendar!");
        stage.setScene(scene);
        stage.show();
    }



}
