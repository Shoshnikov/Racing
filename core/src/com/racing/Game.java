package com.racing;

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
