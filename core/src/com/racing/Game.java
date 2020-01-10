package com.racing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends com.badlogic.gdx.Game
{
	private int screenWidth;
	private int screenHeight;

	public Game(int screenWidth, int screenHeight)
	{
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth()
	{
		return screenWidth;
	}

	public int getScreenHeight()
	{
		return screenHeight;
	}

	@Override
	public void create ()
	{
        System.out.println("Game created");
        setScreen(new MainMenu(this));
	}

}
