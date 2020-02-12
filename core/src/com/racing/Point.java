package com.racing;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;

public class Point
{
    private static int ID = 0;
    private int id;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Button button;
    private boolean isActive = false;
    private boolean isDrawed = false;
    private float x,y;
    private Rectangle pointRectangle;
    private Skin UISkin;
    private TextureAtlas UIAtlas;

    public Point()
    {
        UIAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        UISkin = new Skin(Gdx.files.internal("UISkin.json"),UIAtlas);
        button = new Button(UISkin,"radioButton");
        button.setSize(50f,50f);
        pointRectangle = new Rectangle(0,0,50f,50f);
        id = ID++;
    }

    private void addEdge(Edge edge)
    {
        edges.add(edge);
    }

    public void deleteEdges()
    {
        edges.clear();
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

    public void setButtonPosition(float x, float y)
    {
        this.y = y;
        this.x = x;
        button.setPosition(x,y);
        pointRectangle.setPosition(x,y);
    }

    public float getX()
    {
        return x;
    }

    public float getY() {
        return y;
    }

    public Edge getEdgeWith(Point point)
    {
        for(Edge e: edges)
            if(e.getNeighbour().getID() == point.getID())
                return e;
            return null;
    }


    public Button getButton()
    {
        return button;
    }

    public void setDrawed(boolean drawed) {
        isDrawed = drawed;
    }

    public boolean isDrawed() {
        return isDrawed;
    }

    public Rectangle getPointRectangle()
    {
        return pointRectangle;
    }
}
