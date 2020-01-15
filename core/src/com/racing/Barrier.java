package com.racing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Barrier
{
    private static TextureAtlas boxAtlas = new TextureAtlas("boxAtlas.atlas");
    private Sprite barrierSprite;
    private Rectangle barrierRectangle;
    private float x,y;
    public static float speed = 45f * Gdx.graphics.getDeltaTime();

    private void randomSprite()
    {
        int rand = MathUtils.random(0,2);
        switch(rand)
        {
            case 0:
                barrierSprite = boxAtlas.createSprite("boxWithWeel");
                break;
            case 1:
                barrierSprite = boxAtlas.createSprite("boxes");
                break;
            case 2:
                barrierSprite = boxAtlas.createSprite("barels");
                break;
        }
        barrierSprite.setPosition(x,y);
    }

    public Barrier(float x, float y)
    {
        this.x = x;
        this.y = y;
        randomSprite();
        barrierRectangle = new Rectangle(x,y,barrierSprite.getWidth(),barrierSprite.getHeight());
    }

    public Sprite getBarrierSprite()
    {
        return barrierSprite;
    }

    public void move()
    {
        y -= speed;
        barrierSprite.setPosition(x,y);
        barrierRectangle.setPosition(x,y);
    }

    public Rectangle getBarrierRectangle()
    {
        return barrierRectangle;
    }

}
