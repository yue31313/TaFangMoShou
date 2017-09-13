package org.zhangda.androidym.er;

public enum ConfirmType
{
	ExitToMainMenu,
	RestartGame;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ConfirmType forValue(int value)
	{
		return values()[value];
	}
}