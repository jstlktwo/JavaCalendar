package com.beginsecure.javafxapp.javaapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZonedDateTime;


public class MainController {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    void initialize() {

        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();

        drawCalendar();
        createCalendarPanels();
    }

    @FXML
    void backOneMonth(ActionEvent event) { //месяц назад
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
        createCalendarPanels();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) { //месяц вперед
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
        createCalendarPanels();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private FlowPane calendar;

    @FXML
    private Text month;

    @FXML
    private Button newMonth;

    @FXML
    private Button prevMonth;

    @FXML
    private Text year;

    public static String currentDate;
    public static String getId() {
        return currentDate;
    }

    public static String currentYearMonth;
    public static String getYM() {
        return currentYearMonth;
    }

    private void drawCalendar(){ //отрисовка полей данных календаря
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
    }

    @FXML
    private void createCalendarPanels() {

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = dateFocus.getMonth().maxLength();

        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }

        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();
                Rectangle rectangle = new Rectangle();

                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.web("404040"));
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        date.setFont(Font.font("gilroy", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
                        date.setStroke(Color.web("FFFFFF"));
                        double textTranslationY = -(rectangleHeight / 2) * 0.25;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.web("FF6060"));
                        rectangle.setStrokeWidth(2);
                    }
                }
                calendar.getChildren().add(stackPane);
                rectangle.setOnMouseClicked(event -> {
                    saveTheNote(stackPane);
                    notesOnClick();
                });
            }

        }
    }


    public void saveTheNote(StackPane stackPane){

        var start = String.valueOf(stackPane.getChildren()).lastIndexOf("text=");
        var end = String.valueOf(stackPane.getChildren()).indexOf("\", x=");
        char[] dst=new char[end - start];

        String str = String.valueOf(stackPane.getChildren());

        str.getChars(start, end, dst, 0);
        String dst2 = String.valueOf(dst);

        var start2 = dst2.lastIndexOf("\"")+1;
        var end2 = dst2.lastIndexOf("");

        char[] dst3 = new char[end2 - start2];
        dst2.getChars(start2, end2, dst3, 0);

        currentDate = String.valueOf(dst3) + " " + month.getText() + " " + year.getText();
        currentYearMonth = String.valueOf(dst3) + "." + month.getText();
    };

    public void notesOnClick(){
        calendar.getScene().getWindow().hide();

        FXMLLoader newWindow = new FXMLLoader(Main.class.getResource("notesList.fxml"));

        try {
            newWindow.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent parent = newWindow.getRoot();
        Stage stage = new Stage();
        stage.setTitle("List of notes");

        stage.setScene(new Scene(parent));
        stage.show();
    };


    } // last figure