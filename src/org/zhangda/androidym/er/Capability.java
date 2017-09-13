package org.zhangda.androidym.er;

public enum Capability
{
	AirGround,
	Air,
	Bash;

	public int getValue()
	{
		return this.ordinal();
	}

	public static Capability forValue(int value)
	{
		return values()[value];
	}
}