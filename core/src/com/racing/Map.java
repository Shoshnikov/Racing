package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map implements Screen
{
    private Sprite mapSprite;
    private SpriteBatch batch;
    private Game game;
    private Graf graf;

    public Map(Game game)
    {
        this.game = game;
        Texture map = new Texture(Gdx.files.internal("Wasteland0.jpg"));
        mapSprite = new Sprite(map);
        mapSprite.setSize(game.getScreenWidth(),game.getScreenHeight());
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        graf = new Graf(10,3,10f,30f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mapSprite.draw(batch);
        batch.end();
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
