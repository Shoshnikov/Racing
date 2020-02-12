package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Graf
{
    private int pointsCount;
    private int maxEdges;
    private int arr[][];
    private float minWeight, maxWeight;
    private ArrayList<Point> points = new ArrayList<Point>();
    private Game game;

    public Graf(int pointsCount, int maxEdges, float minWeight, float maxWeight,Game game)
    {
        this.pointsCount = pointsCount;
        this.maxEdges = maxEdges;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.game = game;
        arr = new int[pointsCount][pointsCount];
        createPoints();
        createEdges();
        createMatrix();
        printMatrix();
        //points.get(0).setButtonPosition(game.getScreenWidth()/2f,game.getScreenHeight()/2f);
        System.out.println("Graf created");
        setPointsPositions();
        //drawEdges();
    }

    private void createPoints()
    {
        for(int i = 0;i<pointsCount;i++)
        {
            Point p = new Point();
            points.add(p);
        }
        System.out.println("Points created");
    }

    private void createEdges()
    {
        boolean flag = false;
        boolean correction = false;
        int edgesCount;
        int randomNeighbourID;
        while(!correction)
        {
            for(Point p: points)
                p.deleteEdges();
            for(Point p: points)
            {
                if(p.getEdges().length < maxEdges)
                {
                    edgesCount = MathUtils.random(1,maxEdges-p.getEdges().length);
                    for(int i = 0; i < edgesCount; i++)
                    {
                        while(!flag)
                        {
                            randomNeighbourID = MathUtils.random(0,points.size()-1);
                            if(randomNeighbourID != p.getID()) {
                                p.createEdge(points.get(randomNeighbourID), MathUtils.random(minWeight, maxWeight));
                                flag = true;
                            }
                        }
                        flag = false;
                    }
                }
            }
            correction = grafCorrect();
        }
        System.out.println("Edges created");
    }

    private boolean grafCorrect()
    {
        System.out.println("Correction started");
        int numberOfPoints = 0;
        boolean[] idArray = new boolean[pointsCount];
        Point currentPoint = points.get(0);
        boolean flag = true;
        while(flag)
        {
            numberOfPoints++;
            idArray[currentPoint.getID()] = true;
            for(Point p:currentPoint.getNeighbours())
            {
                flag = false;
                if(!idArray[p.getID()]) {
                    currentPoint = p;
                    flag = true;
                    break;
                }
            }
        }

        if(numberOfPoints < pointsCount)
            return false;
        return true;
    }

    private void createMatrix()
    {
        for(int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr.length; j++)
                arr[i][j] = 0;

        for(int i = 0; i < pointsCount;i++)
        {
            Edge[] edges = points.get(i).getEdges();
            Point[] neighbours = points.get(i).getNeighbours();
            //System.out.println("Point: " + points.get(i) + " at ID: " + points.get(i).getID() + " has neighbours at ID: ");
            for(int z = 0;z< neighbours.length;z++)
                System.out.println("\t"+ neighbours[z].getID());
            for(int j = 0; j < edges.length;j++)
            {
                arr[i][edges[j].getNeighbour().getID()] = (int)(edges[j].getWeight());
            }
        }
        System.out.println("Matrix created");
    }

    private void printMatrix()
    {
        for(int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }
        System.out.println("Matrix printed");
    }

    public ArrayList<Point> getPoints()
    {
        return points;
    }

    public Point getPointAt(int index)
    {
        return points.get(index);
    }

    private void setPointsPositions()
    {
        System.out.println("Drawing first point");
        points.get(0).getButton().setChecked(true);
        points.get(0).setButtonPosition(game.getScreenWidth()/2f,game.getScreenHeight()/2f);
        points.get(0).setDrawed(true);
        System.out.println("Start drawing points");
        float x = 0,y = 0;
        boolean flag = false;
        int num = 0;
        while(!flag) {
            System.out.println("Flag statement");
            for (int i = 0; i < points.size(); i++) {
                System.out.println("For points");
                for (Point neighbour : points.get(i).getNeighbours()) {
                    System.out.println("For point's neighbours");
                    while (!neighbour.isDrawed()) {
                        System.out.println("Draw neighbout isn't drawed ");

                        int rand = MathUtils.random(0, 15);
                        switch (rand) {
                            // первая четверть
                            case 0:
                                y = points.get(i).getEdgeWith(neighbour).getWeight();
                                break;
                            case 1:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(60f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(60f);
                                break;
                            case 2:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(45f);
                                x = y;
                                break;
                            case 3:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(30f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(30f);
                                break;
                            case 4:
                                x = points.get(i).getEdgeWith(neighbour).getWeight();
                                break;
                            // четвертая четверть
                            case 5:
                                y = -points.get(i).getEdgeWith(neighbour).getWeight();
                                break;
                            case 6:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(300f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(300f);
                                break;
                            case 7:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(315f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(315f);
                                break;
                            case 8:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(330f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(330f);
                                break;
                            // вторая четверть
                            case 9:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(120f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(120f);
                                break;
                            case 10:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(135f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(135f);
                                break;
                            case 11:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(150f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(150f);
                                break;
                            case 12:
                                x = -points.get(i).getEdgeWith(neighbour).getWeight();
                                break;
                            // третья четверть
                            case 13:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(210f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(210f);
                                break;
                            case 14:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(225f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(225f);
                                break;
                            case 15:
                                y = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.sinDeg(240f);
                                x = points.get(i).getEdgeWith(neighbour).getWeight() * MathUtils.cosDeg(240f);
                                break;
                        }
                        System.out.println("Before if statement ");
                        if (points.get(i).getX() + x > 75f && points.get(i).getX() + x < game.getScreenWidth() - 75f && points.get(i).getY() + y > 150f && points.get(i).getY() + y < game.getScreenHeight() - 75f)
                        {
                            System.out.println("if statement");
                            neighbour.setButtonPosition(points.get(i).getX() + x, points.get(i).getY() + y);
                            neighbour.setDrawed(true);
                            //System.out.println("Point " + neighbour + " at ID " + neighbour.getID() + " drawed at x:" + neighbour.getX() + " y: " + neighbour.getY());
                        }
                    }
                }
            }
            flag = true;
            System.out.println("Overlaps correction");
            for(Point p: points) {
                for (Point p1 : points)
                    if (p.getPointRectangle().overlaps(p1.getPointRectangle()) && p.getID() != p1.getID())
                        flag = false;
                if(!flag)
                {
                    clearPoints();
                    //System.out.println("drawing graf again");
                    break;
                }
            }
            System.out.println("Seted position of points");
        }
    }

    private void clearPoints()
    {
        for(Point p: points)
            p.setDrawed(false);
    }


    public void drawEdges()
    {
        Gdx.gl.glLineWidth(4f);
        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(Color.BLACK);
        for(Point p: points) {
            for (Edge e : p.getEdges()) {
                sr.begin(ShapeRenderer.ShapeType.Line);

                sr.line(p.getX() + 25f, p.getY() + 25f, e.getNeighbour().getX() + 25f, e.getNeighbour().getY() + 25f);
                sr.end();
            }
        }
        //System.out.println("Edges drawed");
    }

}
