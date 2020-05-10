package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class WinGame implements Screen
{
    private Label titel;
    private TextButton exitButton, toMainMenu;
    private Stage stage;
    private Texture background;
    private TextureAtlas textureAtlas;
    private Skin skin;
    private Game game;
    private SpriteBatch batch;

    public WinGame(Game game)
    {
        this.game = game;
        textureAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        skin = new Skin(Gdx.files.internal("UISkin.json"), textureAtlas);
        stage = new Stage();
        background = new Texture(Gdx.files.internal("Desert2.png"));
        titel = new Label("YOU WIN",skin,"default");
        titel.setPosition(game.getScreenWidth()/2f-titel.getWidth()/2,game.getScreenHeight()-game.getScreenHeight()/4f);
        exitButton = new TextButton("Exit", skin, "default");
        toMainMenu = new TextButton("To Main Menu", skin, "default");
        exitButton.setPosition(game.getScreenWidth()/2f-titel.getWidth()/2,game.getScreenHeight()- 3 * game.getScreenHeight()/4f);
        toMainMenu.setPosition(game.getScreenWidth()/2f-titel.getWidth()/2,game.getScreenHeight()- 2 * game.getScreenHeight()/4f);
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        stage.addActor(titel);
        stage.addActor(exitButton);
        stage.addActor(toMainMenu);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,game.getScreenWidth(),game.getScreenHeight());
        batch.end();

        stage.act(delta);
        stage.draw();

        //if(retryButton.getClickListener().isPressed())
        //game.setScreen(new mainGameScreen(game, mainMenu.getChoise(),mainMenu));//TODO уменьшить кол-во аргументов

        if(exitButton.getClickListener().isPressed())
            Gdx.app.exit();

        if(toMainMenu.getClickListener().isPressed())
            game.setScreen(new MainMenu(game));
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
