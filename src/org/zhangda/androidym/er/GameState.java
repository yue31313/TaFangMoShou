package org.zhangda.androidym.er;

public enum GameState
{
	PlacingInitialTowers,
	Started,
	Paused,
	Ended;

	public int getValue()
	{
		return this.ordinal();
	}

	public static GameState forValue(int value)
	{
		return values()[value];
	}
}