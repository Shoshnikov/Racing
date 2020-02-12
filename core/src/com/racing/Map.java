package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

    public Map(Game game)
    {
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
        stage.addActor(rideButton);
        System.out.println("Map created");
    }

    @Override
    public void show() {
        graf = new Graf(8,2,400f,800f,game);
        for(Point p:graf.getPoints())
            stage.addActor(p.getButton());
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
        graf.drawEdges();
        stage.act(delta);
        stage.draw();
        for(Point p: graf.getPoints())
            if(p.getButton().isPressed() && destenationPointID == -1)
            {
                destenationPointID = p.getID();
                System.out.println("destenationPointID chenged " + destenationPointID);
            }
            else if(p.getButton().isPressed() && destenationPointID != -1)
            {
                graf.getPointAt(destenationPointID).getButton().setChecked(false);
                destenationPointID = p.getID();
                //System.out.println("destenationPointID chenged " + destenationPointID);
            }

            /*if(rideButton.isPressed())
            {
                System.out.println("Ride");
            }*/
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

}
