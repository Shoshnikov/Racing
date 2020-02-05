package com.racing;

public class Edge
{
    private float weight;
    private Point neighbour;
    private Point parent;

    public Edge(Point neighbour, Point parent, float weight)
    {
        this.weight = weight;
        this.neighbour = neighbour;
        this.parent = parent;
        //System.out.println("Edge between" + parent + " and " + neighbour + " created. Edge weight== " + weight);
    }

    public Point getNeighbour()
    {
        return neighbour;
    }

    public Point getParent()
    {
        return parent;
    }

    public float getWeight()
    {
        return weight;
    }
}
