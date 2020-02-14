package com.racing;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Controller
{
    private static int screenHeight;
    private static int screenWidth;
    private static int chosenCarIndex;
    private static Graf graf;
    private static ArrayList<Point> knownPoints;
    private static ArrayList<Edge> knownEdges;
    private static int destination;
    private static int currentPointID;
    private static Stage mapStage;

    public static int getScreenHeight()
    {
        return screenHeight;
    }

    public static int getScreenWidth()
    {
        return screenWidth;
    }

    public static int getChosenCarIndex()
    {
        return chosenCarIndex;
    }

    public static ArrayList<Edge> getKnownEdges() {
        return knownEdges;
    }

    public static ArrayList<Point> getKnownPoints() { return knownPoints; }

    public static Graf getGraf() {
        return graf;
    }

    public static int getDestination() {
        return destination;
    }

    public static int getCurrentPointID() {
        return currentPointID;
    }

    public static Stage getMapStage() { return mapStage; }

    public void setScreenHeight(int screenHeight)
    {
        Controller.screenHeight = screenHeight;
    }

    public void setScreenWidth(int screenWidth) {
        Controller.screenWidth = screenWidth;
    }

    public void setChosenCarIndex(int chosenCarIndex) {
        Controller.chosenCarIndex = chosenCarIndex;
    }

    public void setGraf(Graf graf) {
        Controller.graf = graf;
    }

    public void setKnownEdges(ArrayList<Edge> knownEdges) {
        Controller.knownEdges = knownEdges;
    }

    public void setKnownPoints(ArrayList<Point> knownPoints) {
        Controller.knownPoints = knownPoints;
    }

    public void setDestination(int destination) {
        Controller.destination = destination;
    }

    public void setCurrentPointID(int currentPointID) {
        Controller.currentPointID = currentPointID;
    }

    public void setMapStage(Stage mapStage) { Controller.mapStage = mapStage; }
}
