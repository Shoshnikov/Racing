package com.racing;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen
{
    private TextButton playTB;
    private TextButton exitTB;
    private Skin mainMenuSkin;
    private TextureAtlas UIAtlas;
    private Stage stage;
    private Game game;
    private Texture background;
    private SpriteBatch batch;

    public MainMenu(Game game)
    {
        System.out.println("Menu created");
        this.game = game;
        UIAtlas = new TextureAtlas(new FileHandle("core\\assets\\UIAtlas.atlas"));
        mainMenuSkin = new Skin(new FileHandle("core\\assets\\MainMenu.json"),UIAtlas);
        playTB = new TextButton("play",mainMenuSkin,"default");
        exitTB = new TextButton("exit", mainMenuSkin,"default");
        stage = new Stage();
        background = new Texture(new FileHandle("core\\assets\\Desert2.png"));
        batch = new SpriteBatch();
    }
    @Override
    public void show()
    {
        playTB.setPosition(game.getScreenWidth() / 2 - playTB.getWidth()/2,game.getScreenHeight()/1.5f);
        playTB.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Play touched");
                return true;
            }
        });
        exitTB.setPosition(game.getScreenWidth()/2-exitTB.getWidth()/2,game.getScreenHeight()/4);
        exitTB.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        System.out.println("Menu show");
        stage.addActor(playTB);
        stage.addActor(exitTB);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        System.out.println("Menu render delta: " + delta);
        batch.begin();
        batch.draw(background,0,0,game.getScreenWidth(),game.getScreenHeight());
        batch.end();
        stage.draw();
        if(playTB.getClickListener().isPressed())
            game.setScreen(new mainGameScreen(game));
        if(exitTB.getClickListener().isPressed())
            Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Menu resized {width:" + width +" height:" + height+"}");
    }

    @Override
    public void pause() {
        System.out.println("Menu pause");
    }

    @Override
    public void resume() {
        System.out.println("Menu resume");
    }

    @Override
    public void hide() {
        System.out.println("Menu hide");
    }

    @Override
    public void dispose() {
        System.out.println("Menu dispose");
    }
}
