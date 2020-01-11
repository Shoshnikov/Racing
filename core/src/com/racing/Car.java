package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Car
{
    private static TextureAtlas carAtlas;
    private Sprite carSprite;
    private float speed;
    private float x;
    private Rectangle carRectangle;

    private void randomSprite()
    {
        int rand = MathUtils.random(0,3);
        System.out.println("rand: " + rand);
        switch (rand)
        {
            case 0:
                carSprite = carAtlas.createSprite("redCar");
                speed = 250;
                break;
            case 1:
                carSprite = carAtlas.createSprite("greenCar");
                speed = 200;
                break;
            case 2:
                carSprite = carAtlas.createSprite("blueCar");
                speed = 200;
                break;
            case 3:
                carSprite = carAtlas.createSprite("violetCar");
                speed = 200;
                break;
        }
    }

    public void choisenCar(int carNumber)
    {
        switch (carNumber)
        {
            case 0:
                carSprite = carAtlas.createSprite("redCar");
                speed = 250;
                break;
            case 1:
                carSprite = carAtlas.createSprite("greenCar");
                speed = 200;
                break;
            case 2:
                carSprite = carAtlas.createSprite("blueCar");
                speed = 200;
                break;
            case 3:
                carSprite = carAtlas.createSprite("violetCar");
                speed = 200;
                break;
        }
    }

    public Car(float x)
    {
        this.x = x;
        carAtlas = new TextureAtlas(Gdx.files.internal("carAtlas.atlas"));
        randomSprite();
        carRectangle = new Rectangle(x+ 30,30,carSprite.getWidth()-30,carSprite.getHeight()-30);
        this.x -= carSprite.getWidth()/2;
        setPosition();
    }

    public void moveLeft()
    {
        x -= speed *Gdx.graphics.getDeltaTime();
        setPosition();
    }

    public void moveRight()
    {
        x += speed *Gdx.graphics.getDeltaTime();
        setPosition();
    }

    private void setPosition()
    {
        carRectangle.setPosition(x,0f);
        carSprite.setPosition(x,0f);
    }

    public float getX()
    {
        return x;
    }

    public Rectangle getCarRectangle()
    {
        return carRectangle;
    }

    public Sprite getCarSprite()
    {
        return carSprite;
    }
}
