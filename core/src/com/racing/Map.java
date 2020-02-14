package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import java.util.ArrayList;



public class Map implements Screen
{
    private Sprite mapSprite;
    private SpriteBatch batch;
    private Game game;
    private Graf graf;
    private Stage stage;
    private int currentPointID;
    private int destenationPointID = -1;
    private TextButton rideButton;
    private TextureAtlas UIAtlas;
    private Skin UISkin;
    private ArrayList<Point> knownPoints;
    private ArrayList<Edge> knownEdges;
    private Label destenation;
    private int dest;

    public Map(Game game)
    {
        this.game = game;
        knownPoints = new ArrayList<Point>();
        knownEdges = new ArrayList<Edge>();
        graf = new Graf(8,2,400f,800f);
        currentPointID = 0;
    }

    public Map(Game game, ArrayList<Point> knownPoints, ArrayList<Edge> knownEdges, Graf graf,int currentPointID)
    {
        this.game = game;
        this.knownEdges = knownEdges;
        this.knownPoints = knownPoints;
        this.graf = graf;
        this.currentPointID = currentPointID;
        this.stage = Controller.getMapStage();
    }

    @Override
    public void show()
    {
        Texture map = new Texture(Gdx.files.internal("Wasteland0.jpg"));
        mapSprite = new Sprite(map);
        mapSprite.setSize(game.getScreenWidth(),game.getScreenHeight());
        batch = new SpriteBatch();
        stage = new Stage();
        UIAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        UISkin = new Skin(Gdx.files.internal("UISkin.json"),UIAtlas);
        rideButton = new TextButton("Drive",UISkin);
        rideButton.setSize(200f,75f);
        rideButton.setPosition(game.getScreenWidth()-250f,75f);
        destenation = new Label("Destination :",UISkin,"blackLabel");
        destenation.setSize(200,75);
        destenation.setPosition(250,70);
        stage.addActor(rideButton);
        stage.addActor(destenation);
        System.out.println("Map created");
        addNewPoints(graf.getPointAt(currentPointID));
        Gdx.input.setInputProcessor(stage);
        System.out.println("Map show");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mapSprite.draw(batch);
        batch.end();
        graf.getPointAt(currentPointID).getButton().setChecked(true);
        drawKnownEdges();
        stage.act(delta);
        stage.draw();
        for(Point p: graf.getPoints())
            if(p.getButton().isPressed() && destenationPointID == -1)
            {
                destenationPointID = p.getID();
                destenation.setText("Destination : " +  Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f) + " km");
                dest = Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f);
                System.out.println("destinationPointID chenged " + destenationPointID);
            }
            else if(p.getButton().isPressed() && destenationPointID != -1)
            {
                try
                {
                    destenation.setText("Destination : " + Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f) + " km");
                    dest = Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f);
                    System.out.println("destinationPointID chenged " + destenationPointID);
                }catch(Exception e)
                {
                    destenation.setText("Destination : Unkown");
                }
                graf.getPointAt(destenationPointID).getButton().setChecked(false);
                destenationPointID = p.getID();
            }

            if(rideButton.isPressed())
            {
                graf.getPointAt(currentPointID).getButton().setChecked(false);
                System.out.println("Drive");
                currentPointID = destenationPointID;
                addNewPoints(graf.getPointAt(currentPointID));
                Controller c = new Controller();
                c.setGraf(graf);
                c.setKnownEdges(knownEdges);
                c.setKnownPoints(knownPoints);
                c.setDestination(dest);
                c.setCurrentPointID(currentPointID);
                c.setMapStage(stage);
                game.setScreen(new mainGameScreen(game,this));
            }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void addNewPoints(Point newCurrentPoint)
    {
        stage.addActor(newCurrentPoint.getButton());
        knownPoints.add(newCurrentPoint);
        for(Point p: newCurrentPoint.getNeighbours())
        {
            stage.addActor(p.getButton());
            knownPoints.add(p);
            knownEdges.add(newCurrentPoint.getEdgeWith(p));
        }
        rebuildStage();
    }

    private void rebuildStage()
    {
        stage.clear();
        stage.addActor(rideButton);
        stage.addActor(destenation);
        for(Point p: knownPoints)
            stage.addActor(p.getButton());
    }

    private void drawKnownEdges()
    {
        Gdx.gl.glLineWidth(4f);
        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(Color.BLACK);
        for(Edge e: knownEdges) {
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.line(e.getParent().getX() + 25f, e.getParent().getY() + 25f, e.getNeighbour().getX() + 25f, e.getNeighbour().getY() + 25f);
            sr.end();
        }
    }
}
