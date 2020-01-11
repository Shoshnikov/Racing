package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class mainGameScreen implements Screen
{
    private Game game;
    private Car car;
    private SpriteBatch batch;
    private Texture road0;
    private Texture road1;
    private float roadY;
    private ArrayList<Barrier> barriers = new ArrayList<>();
    private long lastSpawnTime = 0;
    private long spawnDelay = 2000;

    public mainGameScreen(Game game)
    {
        this.game = game;
        road0 = new Texture(Gdx.files.internal("road.png"));
        road1 = road0;
        car = new Car(game.getScreenWidth()/2f);
        batch = new SpriteBatch();
        roadY = 0;
    }

    private void removeBarriers()
    {
        Iterator<Barrier> iterator = barriers.iterator();
        while(iterator.hasNext())
        {
            Barrier barrier = iterator.next();
            if(barrier.getBarrierRectangle().overlaps(car.getCarRectangle()) || barrier.getBarrierSprite().getY() + barrier.getBarrierSprite().getHeight() < 0)
                iterator.remove();
        }
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
        System.out.println("mainGameScreen render Delta: " + delta);

        if(Gdx.input.isKeyPressed(Input.Keys.D) && car.getX() < game.getScreenWidth() - car.getCarSprite().getWidth()) {
           car.moveRight();
            System.out.println("D pressed");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && car.getX() > 0) {
            car.moveLeft();
            System.out.println("A pressed");
        }

        if(TimeUtils.millis() - lastSpawnTime > spawnDelay)
        {
            barriers.add(new Barrier(MathUtils.random(0,game.getScreenWidth()-100),game.getScreenHeight()));
            lastSpawnTime = TimeUtils.millis();
        }

        if(roadY+road1.getHeight() < 0)
            roadY = 0;
        else
            roadY -= Barrier.speed;

        removeBarriers();

        batch.begin();
        batch.draw(road0,0,roadY, game.getScreenWidth(),game.getScreenHeight());
        batch.draw(road1,0,roadY+road0.getHeight(), game.getScreenWidth(),game.getScreenHeight());
        for(Barrier barrier: barriers)
        {
            barrier.move();
            barrier.getBarrierSprite().draw(batch);
        }
        car.getCarSprite().draw(batch);
        batch.end();
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
