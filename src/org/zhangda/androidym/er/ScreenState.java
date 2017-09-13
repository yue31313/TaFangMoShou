package org.zhangda.androidym.er;

public enum ScreenState
{
	TransitionOn,
	Active,
	TransitionOff,
	Hidden;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ScreenState forValue(int value)
	{
		return values()[value];
	}
}