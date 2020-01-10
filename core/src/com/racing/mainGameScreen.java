package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.ArrayList;

public class mainGameScreen implements Screen
{
    private Game game;
    private Sprite car;
    private TextureAtlas carAtlas;
    private SpriteBatch batch;
    private float carX;
    private Texture road0;
    private Texture road1;
    private float roadY;
    private int speed;
    private Rectangle carRect;
    //private Barrier barrier;
    private ArrayList<Barrier> barriers = new ArrayList<>();
    private ArrayList<Integer> disposeBarriers = new ArrayList<>();
    private long lastSpawnTime = 0;
    private long spawnDelay = 4000;

    public mainGameScreen(Game game)
    {
        this.game = game;
        carAtlas = new TextureAtlas("carAtlas.atlas");
        int rand = MathUtils.random(0,3);
        System.out.println("rand: " + rand);
        switch (rand)
        {
            case 0:
                car = carAtlas.createSprite("redCar");
                speed = 115;
                break;
            case 1:
                car = carAtlas.createSprite("greenCar");
                speed = 100;
                break;
            case 2:
                car = carAtlas.createSprite("blueCar");
                speed = 100;
                break;
            case 3:
                car = carAtlas.createSprite("violetCar");
                speed = 100;
                break;
        }
        road0 = new Texture(new FileHandle("core\\assets\\road.png"));
        road1 = road0;
        carX = game.getScreenWidth()/2-car.getWidth()/2;
        car.setPosition(carX,0f);
        batch = new SpriteBatch();
        carRect = new Rectangle(carX,0f,car.getWidth(),car.getHeight());
        //barrier = new Barrier(MathUtils.random(0,game.getScreenWidth()-100),game.getScreenHeight());
        roadY = 0;
    }

    @Override
    public void show() {
        System.out.println("mainGameScreen shown");
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println("mainGameScreen render");

        if(Gdx.input.isKeyPressed(Input.Keys.D) && carX < game.getScreenWidth() - car.getWidth()) {
            carX += speed * Gdx.graphics.getDeltaTime();
            System.out.println("D pressed");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && carX > 0) {
            carX -= speed * Gdx.graphics.getDeltaTime();
            System.out.println("A pressed");
        }

        if(TimeUtils.millis() - lastSpawnTime > spawnDelay)
        {
            barriers.add(new Barrier(MathUtils.random(0,game.getScreenWidth()-100),game.getScreenHeight()));
            lastSpawnTime = TimeUtils.millis();
        }

        for(Barrier barrier : barriers)
            if(barrier.getBarrierSprite().getY() + barrier.getBarrierSprite().getHeight() < 0 && barriers.isEmpty() == false)
                disposeBarriers.add(barriers.indexOf(barrier));

        for(Integer i: disposeBarriers)
            barriers.remove(i);
        //barrier.move();
        carRect.setPosition(carX,0f);
        car.setPosition(carX,0f);
        batch.begin();
        batch.draw(road0,0,roadY);
        batch.draw(road1,0,roadY+road0.getHeight());
        for(Barrier barrier: barriers)
        {
            barrier.move();
            barrier.getBarrierSprite().draw(batch);
        }
        car.draw(batch);
        batch.end();
        if(roadY+road1.getHeight() < 0)
            roadY = 0;
        else
            roadY -= 150f*Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("mainGameScreeb resize");
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }

}
