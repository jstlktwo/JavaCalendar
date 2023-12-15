package com.beginsecure.javafxapp.javaapp;

public class NoteStructure {

    String id;
    int number;
    String textOfArticle;
    String textOfNote;
    NoteStructure(int number, String id, String textOfArticle, String textOfNote)
    {
        this.number = number;
        this.id = id;
        this.textOfArticle = textOfArticle;
        this.textOfNote = textOfNote;
    }

    public String getInfo(){
        return (id + "\tTitle: " + textOfArticle + "\tNote: " + textOfNote + "\n");

    }
}
