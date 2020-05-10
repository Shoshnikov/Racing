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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    private Image carForChoise;
    private TextureAtlas carAtlas;
    private Button leftArrow;
    private Button rightArrow;
    private int choise;
    private boolean leftAnimationOn = false, rightAnimationOn = false;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;


    public MainMenu(Game game)
    {
        System.out.println("Menu created");
        this.game = game;
        carAtlas = new TextureAtlas(Gdx.files.internal("carAtlas.atlas"));
        UIAtlas = new TextureAtlas(Gdx.files.internal("UIAtlas.atlas"));
        mainMenuSkin = new Skin(Gdx.files.internal("UISkin.json"),UIAtlas);
        playTB = new TextButton("play",mainMenuSkin,"default");
        exitTB = new TextButton("exit", mainMenuSkin,"default");
        stage = new Stage();
        background = new Texture(Gdx.files.internal("Desert2.png"));
        batch = new SpriteBatch();
        carForChoise = new Image(carAtlas.findRegion("redCar"));
        leftArrow = new Button(mainMenuSkin,"arrowLeft");
        rightArrow = new Button(mainMenuSkin,"arrowRigth");
        choise = 0;
        /*if(Gdx.files.internal("core\\assets\\save.sav").exists() && firstStart)
        {
            try
            {
                fileInputStream = new FileInputStream(Gdx.files.internal("core\\assets\\save.sav").toString());
                objectInputStream = new ObjectInputStream(fileInputStream);
                game.save = (Save)objectInputStream.readObject();
                System.out.println("Record readed: " + game.save.getRecord());
                objectInputStream.close();
            }
            catch(Exception e)
            {
                System.out.println("ОШИБКА ПРИ ЗАГРУЗКЕ ФАЙЛА");
                System.out.println(e.getMessage());
            }
            record = new Label("Record: " + game.save.getRecord(),mainMenuSkin,"default");
        }else
            record = new Label("Record: ",mainMenuSkin,"default");

        record.setPosition(Controller.getScreenWidth()/1.3f,Controller.getScreenHeight()/1.2f);
        firstStart = false;*/
    }

    private void switchTexture()
    {
        switch(choise)
        {
            case 0:
                carForChoise = new Image(carAtlas.findRegion("redCar"));
                break;
            case 1:
                carForChoise = new Image(carAtlas.findRegion("greenCar"));
                break;
            case 2:
                carForChoise = new Image(carAtlas.findRegion("blueCar"));
                break;
            case 3:
                carForChoise = new Image(carAtlas.findRegion("violetCar"));
                break;
        }
    }

    private void rightAnimation()
    {
        if(carForChoise.getX() < rightArrow.getX())
            carForChoise.setPosition(carForChoise.getX()+10f,carForChoise.getY());
        else
            {
                carForChoise.remove();
                switchTexture();
                carForChoise.setPosition(Controller.getScreenWidth()/6f,Controller.getScreenHeight()/1.8f);
                rightAnimationOn = false;
            }
    }

    private void leftAnimation()
    {
        if(carForChoise.getX() > leftArrow.getX())
            carForChoise.setPosition(carForChoise.getX()-10f,carForChoise.getY());
        else
            {
                carForChoise.remove();
                switchTexture();
                carForChoise.setPosition(Controller.getScreenWidth()/6f,Controller.getScreenHeight()/1.8f);
                leftAnimationOn = false;
            }
    }

    @Override
    public void show()
    {

        /*if(!firstStart)
            record.setText("Record: " + game.save.getRecord());*/

        playTB.setPosition(Controller.getScreenWidth() / 2f - playTB.getWidth()/2,Controller.getScreenHeight()/1.5f);
        playTB.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Play touched");
                return true;
            }
        });

        exitTB.setPosition(Controller.getScreenWidth()/2f-exitTB.getWidth()/2,Controller.getScreenHeight()/4f);
        exitTB.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        carForChoise.scaleBy(0.001f,0.001f);
        carForChoise.setPosition(Controller.getScreenWidth()/6f,Controller.getScreenHeight()/1.8f);

        rightArrow.setPosition(carForChoise.getX()+carForChoise.getPrefWidth(),carForChoise.getY() + 100f);
        rightArrow.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        leftArrow.setPosition(carForChoise.getX() - leftArrow.getPrefWidth(),carForChoise.getY() + 100f);
        leftArrow.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        System.out.println("Menu show");
        stage.addActor(playTB);
        stage.addActor(exitTB);
        stage.addActor(carForChoise);
        stage.addActor(rightArrow);
        stage.addActor(leftArrow);
        //stage.addActor(record);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        //System.out.println("Menu render delta: " + delta);

        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,Controller.getScreenWidth(),Controller.getScreenHeight());
        carForChoise.draw(batch,1f);
        batch.end();

        stage.act(delta);
        stage.draw();

        if(playTB.getClickListener().isPressed()) {
            game.setScreen(new Map(game));
            Controller c = new Controller();
            c.setChosenCarIndex(choise);
            playTB.getClickListener().cancel();
        }

        if(exitTB.getClickListener().isPressed())
            Gdx.app.exit();

        if(rightArrow.getClickListener().isPressed())
            if(choise < 3 && !rightAnimationOn)
            {
                choise++;
                System.out.println("leftAnimationOn started\nchoise " + choise);
                rightAnimationOn = true;
            }

        if(leftArrow.getClickListener().isPressed())
            if (choise > 0 && !leftAnimationOn)
            {
                choise--;
                System.out.println("rightAnimationOn started\nchoise " + choise);
                leftAnimationOn = true;
            }


        if(leftAnimationOn)
            leftAnimation();
        if(rightAnimationOn)
            rightAnimation();
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
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("Menu dispose");
    }
}
