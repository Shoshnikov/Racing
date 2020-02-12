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
    private int currentPointID = 0;
    private int destenationPointID = -1;
    private TextButton rideButton;
    private TextureAtlas UIAtlas;
    private Skin UISkin;
    private ArrayList<Point> knownPoints = new ArrayList<Point>();
    private ArrayList<Edge> knownEdges = new ArrayList<Edge>();
    private Label destenation;
    private MainMenu mainMenu;
    private int chosenCarIndex;
    private int dest;

    public Map(Game game,MainMenu mainMenu, int chosenCarIndex)
    {
        this.mainMenu = mainMenu;
        this.chosenCarIndex = chosenCarIndex;
        this.game = game;
        Texture map = new Texture(Gdx.files.internal("Wasteland0.jpg"));
        mapSprite = new Sprite(map);
        mapSprite.setSize(game.getScreenWidth(),game.getScreenHeight());
        batch = new SpriteBatch();
        stage = new Stage();
        UIAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        UISkin = new Skin(Gdx.files.internal("UISkin.json"),UIAtlas);
        rideButton = new TextButton("Ride",UISkin);
        rideButton.setSize(200f,75f);
        rideButton.setPosition(game.getScreenWidth()-250f,75f);
        destenation = new Label("Destenation :",UISkin,"blackLabel");
        destenation.setSize(200,75);
        destenation.setPosition(250,70);
        stage.addActor(rideButton);
        stage.addActor(destenation);
        System.out.println("Map created");
        graf = new Graf(8,2,400f,800f,game);
    }

    @Override
    public void show() {
        //graf = new Graf(8,2,400f,800f,game);
        //for(Point p:graf.getPoints())
            //stage.addActor(p.getButton());
        addNewPoints(graf.getPointAt(0));
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
                destenation.setText("Destenation : " +  Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f) + " km");
                dest = Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f);
                System.out.println("destenationPointID chenged " + destenationPointID);
            }
            else if(p.getButton().isPressed() && destenationPointID != -1)
            {
                try
                {
                    destenation.setText("Destenation : " + Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f) + " km");
                    dest = Math.round(graf.getPointAt(currentPointID).getEdgeWith(p).getWeight()/200f);
                }catch(Exception e)
                {
                    destenation.setText("Destenation : Unkown");
                }
                graf.getPointAt(destenationPointID).getButton().setChecked(false);
                destenationPointID = p.getID();
            }

            if(rideButton.isPressed())
            {
                graf.getPointAt(currentPointID).getButton().setChecked(false);
                System.out.println("Ride");
                currentPointID = destenationPointID;
                addNewPoints(graf.getPointAt(currentPointID));
                game.setScreen(new mainGameScreen(game,chosenCarIndex,mainMenu,dest,this));
                for(Point p: graf.getPoints())
                    p.getButton().getClickListener().cancel();
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
