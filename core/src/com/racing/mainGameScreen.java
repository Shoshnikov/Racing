package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.TimeUtils;
import jdk.tools.jaotc.Main;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
    private long record = 0l;
    private TextButton toMainMenu;
    private Label recordLabel;
    private Skin mainGameScreenSkin;
    private TextureAtlas UIAtlas;
    private Stage stage;
    private long prevTime = 0l;
    private MainMenu mainMenu;

    public mainGameScreen(Game game, int chosenCarIndex, MainMenu mainMenu)
    {
        this.game = game;
        road0 = new Texture(Gdx.files.internal("road.png"));
        road1 = road0;
        car = new Car(game.getScreenWidth()/2f, chosenCarIndex);
        batch = new SpriteBatch();
        roadY = 0;
        UIAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        mainGameScreenSkin = new Skin(Gdx.files.internal("UISkin.json"),UIAtlas);
        toMainMenu = new TextButton("Main Menu", mainGameScreenSkin,"default");
        toMainMenu.setPosition(game.getScreenWidth()/1.3f,game.getScreenHeight()/1.2f);
        recordLabel = new Label("",mainGameScreenSkin,"default");
        recordLabel.setPosition(game.getScreenWidth()/2-recordLabel.getWidth() - 12,game.getScreenHeight()-50);
        stage = new Stage();
        this.mainMenu = mainMenu;
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
    public void show()
    {
        stage.addActor(recordLabel);
        stage.addActor(toMainMenu);
        Gdx.input.setInputProcessor(stage);
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

        if(TimeUtils.timeSinceMillis(prevTime) > 33)
        {
            record += 1;
            recordLabel.setText(""+record);
            //recordLabel.setPosition(game.getScreenWidth()/2-recordLabel.getWidth()/2,game.getScreenHeight()-50);
            prevTime = TimeUtils.millis();
        }

        if(roadY+road1.getHeight() < 0)
            roadY = 0;
        else
            roadY -= Barrier.speed;

        removeBarriers();

        if(toMainMenu.getClickListener().isPressed())
        {
            game.setScreen(mainMenu);
        }

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

        stage.act(delta);
        stage.draw();
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
        dispose();
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        System.out.println("Прошлый рекорд " + game.save.getRecord());
        if(record > game.save.getRecord())
        try
        {
            game.save.setRecord(record);
            FileOutputStream fileOutputStream = new FileOutputStream(Gdx.files.internal("core\\assets\\save.sav").toString());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game.save);
            objectOutputStream.close();
        }
        catch(Exception e)
        {
            System.out.println("ОШИБКА ПРИ СОХРАНЕНИИ ФАЙЛА");
            System.out.println(e.getMessage());
        }
        System.out.println("dispose");
    }
}
