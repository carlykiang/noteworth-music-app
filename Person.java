package com.example.musicappcsia;

public class Person {

    private String name;
    private String score;

    public Person(String name, String score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public String getScore(){
        return score;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setScore(String score){
        this.score = score;
    }

}
