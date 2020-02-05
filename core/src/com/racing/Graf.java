package com.racing;

import java.util.ArrayList;

public class Graf
{
    private int pointsCount;
    private int maxEdges;
    private int arr[][];
    private float minWeight, maxWeight;
    private ArrayList<Point> points = new ArrayList<Point>();

    public Graf(int pointsCount, int maxEdges, float minWeight, float maxWeight)
    {
        this.pointsCount = pointsCount;
        this.maxEdges = maxEdges;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        arr = new int[pointsCount][pointsCount];
        createPoints();
        createEdges();
        createMatrix();
        printMatrix();
    }

    private void createPoints()
    {
        for(int i = 0;i<pointsCount;i++)
            points.add(new Point());
    }

    private void createEdges()
    {
        for(int i = 0; i < points.size();i++)
        {
            int edgesCount = 1 + (int)(Math.random() * maxEdges);
            for(int j = 0; j < edgesCount;j++)
            {
                int neighbour = i;
                while(neighbour == i)
                    neighbour =  1 + (int)(Math.random() * points.size()-1);
                points.get(i).createEdge(points.get(neighbour),minWeight + (float)(Math.random() * maxWeight));
            }
        }
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
            System.out.println("Point: " + points.get(i) + " at ID: " + points.get(i).getID() + " has neighbours at ID: ");
            for(int z = 0;z< neighbours.length;z++)
                System.out.println("\t"+ neighbours[z].getID());
            for(int j = 0; j < edges.length;j++)
            {
                arr[i][edges[j].getNeighbour().getID()] = (int)(edges[j].getWeight());
            }
        }
    }

    private void printMatrix()
    {
        for(int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

}
