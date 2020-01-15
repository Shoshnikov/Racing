package com.racing;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Game extends com.badlogic.gdx.Game
{
	private int screenWidth;
	private int screenHeight;
	public Save save = new Save();

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
