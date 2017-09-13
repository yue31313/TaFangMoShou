﻿package org.zhangda.androidym.er;

import loon.action.sprite.SpriteBatch;
import loon.core.geom.Vector2f;
import loon.core.graphics.LColor;
import loon.core.graphics.LFont;
import loon.core.timer.GameTime;

public class GamePausedSpriteWithText extends Sprite
{
	private LFont font;

	public GamePausedSpriteWithText(MainGame game)
	{
		super(game, "assets/ingame_menu.png", 0, new Vector2f(0f, 0f));
	}

	@Override
	public void draw(SpriteBatch batch,GameTime gameTime)
	{
		super.draw(batch,gameTime);
		Utils.DrawStringAlignCenter(batch, this.font, LanguageResources.getResume().toUpperCase(), 160f, 260f, LColor.white);
		Utils.DrawStringAlignCenter(batch, this.font, LanguageResources.getRestart().toUpperCase(), 160f, 320f, LColor.white);
		Utils.DrawStringAlignCenter(batch, this.font, LanguageResources.getMainMenu().toUpperCase(), 160f, 380f, LColor.white);
	}

	@Override
	protected void loadContent()
	{
		super.loadContent();
		this.font = LFont.getFont(12);
	}
}