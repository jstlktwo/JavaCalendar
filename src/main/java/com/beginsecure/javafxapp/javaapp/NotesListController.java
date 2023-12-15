package com.beginsecure.javafxapp.javaapp;

import java.io.*;
import java.net.URL;

import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class NotesListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label curDate;

    @FXML
    private Button createNewNotes;

    @FXML
    private Button deleteAllNotes;

    @FXML
    private Button deleteNoteNumber;

    @FXML
    private Button saveDeleting;

    @FXML
    private TextField enterDeleteNoteNumber;

    @FXML
    private TextArea listOfNotes;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label isDayCalendar;

    @FXML
    private Label calendarDay;

    public static String pathname = "txtNotes/";

    public void interfaceNotes(){
        int k = 1;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathname + MainController.getId() + ".txt"));
            String line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()){
                    listOfNotes.appendText(k + ". " + line + "\n\n");
                    k++;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDeleteNoteNumber(){

        ArrayList<String> textBuffer = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathname + MainController.getId() + ".txt"));
            String line = reader.readLine();
            while (line != null) {
                textBuffer.add(line);
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = Integer.parseInt(enterDeleteNoteNumber.getText());
        System.out.println(textBuffer.size());
        textBuffer.remove(index-1);
        System.out.println(textBuffer.size());
        listOfNotes.clear();
        enterDeleteNoteNumber.clear();
        for(int i = 0; i < textBuffer.size();i++){
            if (!textBuffer.get(i).isEmpty()){
                listOfNotes.appendText(i+1 + ". " + textBuffer.get(i) + "\n\n");
            }
        }

        saveDeleting.setOnAction(event -> {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(pathname + MainController.getId() + ".txt", false));
                writer.write("");
                writer.close();
                BufferedWriter newWriter = new BufferedWriter(new FileWriter(pathname + MainController.getId() + ".txt", true));
                int j = 0;
                for(int i = 0; i < textBuffer.size();i++){
                    if (!textBuffer.get(i).isEmpty()){
                        newWriter.write(textBuffer.get(i) + "\n");
                    }
                }
                newWriter.close();
            } catch (IOException e) {
                System.out.println("Error trying to save in file");
                e.printStackTrace();
            }
        });

    }

    public void removeAllNotes(){
        listOfNotes.clear();
        saveDeleting.setOnAction(event -> {
            try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathname + MainController.getId() + ".txt", false));
            writer.write("");
            writer.close();
            } catch (IOException e) {
                System.out.println("Error trying to save in file");
                e.printStackTrace();
            }
        });
    }



    public void createNotesOnClick() throws IOException {
        Stage stage = (Stage) enterDeleteNoteNumber.getScene().getWindow();
        stage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("notesScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Create a new note");
        stage.setScene(scene);
        stage.show();
    }

    public void closeListOnClick() throws IOException {
        Stage stage = (Stage) enterDeleteNoteNumber.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Java App Calendar!");
        stage.setScene(scene);
        stage.show();
    }


    public void isDayOff(){

        if (Objects.equals(String.valueOf(MainController.getYM()), "31.DECEMBER")){
            calendarDay.setText("New Year");
            isDayCalendar.setText("Holiday");
        } else if (Objects.equals(String.valueOf(MainController.getYM()), "1.JANUARY")) {
            calendarDay.setText("New Year");
            isDayCalendar.setText("Holiday");
        } else if (Objects.equals(String.valueOf(MainController.getYM()), "7.JANUARY")) {
            calendarDay.setText("Christmas");
            isDayCalendar.setText("Holiday");
        } else if (Objects.equals(String.valueOf(MainController.getYM()), "23.FEBRUARY")) {
            calendarDay.setText("Defender of the Fatherland Day");
            isDayCalendar.setText("Holiday");
        } else if (Objects.equals(String.valueOf(MainController.getYM()), "8.MARCH")) {
            calendarDay.setText("International Women's Day");
            isDayCalendar.setText("Holiday");
        }  else if (Objects.equals(String.valueOf(MainController.getYM()), "1.MAY")) {
            calendarDay.setText("Labour Day");
            isDayCalendar.setText("Holiday");
        }  else if (Objects.equals(String.valueOf(MainController.getYM()), "9.MAY")) {
            calendarDay.setText("Victory Day");
            isDayCalendar.setText("Holiday");
        }  else if (Objects.equals(String.valueOf(MainController.getYM()), "12.JUNE")) {
            calendarDay.setText("Russia Day");
            isDayCalendar.setText("Holiday");
        }  else if (Objects.equals(String.valueOf(MainController.getYM()), "4.NOVEMBER")) {
            calendarDay.setText("National Unity Day");
            isDayCalendar.setText("Holiday");
            isDayCalendar.setTextFill(Color.web("EEEEEE"));
        } else {
            calendarDay.setText("");
            calendarDay.setTextFill(Color.web("404040"));
            isDayCalendar.setText("Not a holiday");
        }
    }

    @FXML
    void initialize() {
        interfaceNotes();
        curDate.setText(MainController.getId());
        curDate.setStyle("-fx-font-size: 11px; -fx-text-fill: white; -fx-text-align: center");
        isDayOff();

    }


}
