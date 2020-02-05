package com.racing;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


import java.util.ArrayList;

public class Point
{
    private static int ID = 0;
    private int id;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Button button;
    private boolean isActive = false;

    public Point()
    {
        TextureAtlas UIAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        button = new Button();
        id = ID++;
    }

    private void addEdge(Edge edge)
    {
        edges.add(edge);
        System.out.println("Edge between " + edge.getParent() + " and " + edge.getNeighbour() + " created. Edge weight== " + edge.getWeight());
    }

    public void createEdge(Point neighbour, float weight)
    {
        Edge e = new Edge(neighbour,this,weight);
        if(!edgeExists(e))
            addEdge(e);
        if(!neighbour.oppositeEdgeExists(e))
            neighbour.addEdge(new Edge(this,neighbour,weight));
    }

    public boolean oppositeEdgeExists(Edge e)
    {
        if(!edges.isEmpty())
            for(int i = 0; i < edges.size();i++)
                if(e.getParent().getID() == edges.get(i).getNeighbour().getID())
                    return true;
        return false;
    }

    public boolean edgeExists(Edge e)
    {
        if(!edges.isEmpty())
            for(int i = 0; i < edges.size();i++)
                if(e.getNeighbour().getID() == edges.get(i).getNeighbour().getID())
                    return true;
        return false;
    }

    public Point[] getNeighbours()
    {
        Point[] arr = new Point[edges.size()];
        for(int i = 0; i < edges.size();i++)
            arr[i] = edges.get(i).getNeighbour();
        return arr;

    }

    public Edge[] getEdges()
    {
        Edge[] arr = new Edge[edges.size()];
        for(int i = 0; i < edges.size();i++)
            arr[i] = edges.get(i);
        return arr;
    }

    public int getID()
    {
        return id;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void turnOn()
    {
        isActive = true;
        button.setChecked(isActive);
    }

    public void turnOff()
    {
        isActive = false;
        button.setChecked(isActive);
    }

    public void setButtonPosition(float x, float y)
    {
        button.setPosition(x,y);
    }
}
