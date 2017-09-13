﻿package org.zhangda.androidym.er;

import loon.action.sprite.SpriteBatch;
import loon.core.LSystem;
import loon.core.geom.RectBox;
import loon.core.geom.Vector2f;
import loon.core.graphics.LColor;
import loon.core.input.LInput;
import loon.core.input.LInputFactory;
import loon.core.input.LTouchCollection;
import loon.core.input.LInputFactory.Key;
import loon.core.input.LInputFactory.Touch;
import loon.core.input.LTouchLocation;
import loon.core.input.LTouchLocationState;
import loon.core.timer.GameTime;

public abstract class MenuScreen extends GameScreen {

	private MainGame game;

	private java.util.ArrayList<MenuEntry> menuEntries = new java.util.ArrayList<MenuEntry>();

	private String menuTitle;

	public ScreenType prevScreen;

	private int selectedEntry;

	public MenuScreen(String menuTitle, MainGame game, ScreenType prevScreen) {
		this.prevScreen = prevScreen;
		this.game = game;
		this.menuTitle = menuTitle;
		super.setTransitionOnTime(0.5f);
		super.setTransitionOffTime(0.5f);
	}

	@Override
	public void draw(SpriteBatch batch, GameTime gameTime) {
		for (int i = 0; i < this.menuEntries.size(); i++) {
			MenuEntry entry = this.menuEntries.get(i);
			boolean isSelected = super.getIsActive()
					&& (i == this.selectedEntry);
			entry.Draw(batch, this, isSelected, gameTime);
		}
		float num2 = (float) Math.pow((double) super.getTransitionPosition(),
				2.0);
		Vector2f position = new Vector2f();
		Vector2f origin = new Vector2f(batch.getFont().stringWidth(
				this.menuTitle) / 2f);
		LColor color = PoolColor.getColor(0xc0 * super.getTransitionAlpha(),
				0xc0 * super.getTransitionAlpha(),
				0xc0 * super.getTransitionAlpha(),
				0xc0 * super.getTransitionAlpha());
		float scale = 1.25f;
		position.y -= num2 * 100f;
		batch.drawString(batch.getFont(), this.menuTitle, position, color, 0f,
				origin, scale);
	}

	protected RectBox GetMenuEntryHitBounds(MenuEntry entry) {
		return entry.getBounds();
	}

	@Override
	public void HandleInput(GameTime gameTime, LInput input) {
		if (Key.isKeyPressed(Key.BACK)) {
			this.OnCancel();
		}
		LTouchCollection collection = LInputFactory.getTouchState();
		if (collection.size() > 0) {
			for (LTouchLocation touch : collection) {
				if (touch.getPrevState() == LTouchLocationState.Pressed) {
					for (int i = 0; i < this.menuEntries.size(); i++) {
						MenuEntry entry = this.menuEntries.get(i);
						if (this.GetMenuEntryHitBounds(entry).contains(
								Touch.x(), Touch.y())) {
							this.OnSelectEntry(i);
						}
					}
				}
			}
		}
	}

	protected void OnCancel() {
		super.ExitScreen();
		if (this.getScreenType() == ScreenType.MainMenuScreen) {
			LSystem.exit();
		} else if ((this.getScreenType() == ScreenType.MonsterInfoScreen)
				|| (this.getScreenType() == ScreenType.TowerInfoScreen)) {
			super.getScreenManager().AddScreen(
					new InstructionScreen(this.game, this.getScreenType()));
		} else if ((this.getScreenType() == ScreenType.InstructionsScreen)
				|| (this.getScreenType() == ScreenType.SelectLevelScreen)) {
			super.getScreenManager().AddScreen(
					new MainMenuScreen(this.game, this.getScreenType()));
		} else if (this.prevScreen == ScreenType.GamePausedScreen) {
			super.getScreenManager().AddScreen(
					new GamePausedScreen(this.game, this.getScreenType()));
		} else {
			super.getScreenManager().AddScreen(
					new MainMenuScreen(this.game, this.getScreenType()));
		}
	}

	protected void OnSelectEntry(int entryIndex) {
		this.menuEntries.get(entryIndex).OnSelectEntry();
	}

	@Override
	public void Update(GameTime gameTime, boolean otherScreenHasFocus,
			boolean coveredByOtherScreen) {
		super.Update(gameTime, otherScreenHasFocus, coveredByOtherScreen);
		for (int i = 0; i < this.menuEntries.size(); i++) {
			boolean isSelected = super.getIsActive()
					&& (i == this.selectedEntry);
			this.menuEntries.get(i).Update(this, isSelected, gameTime);
		}
	}

	protected void UpdateMenuEntryLocations() {
		float num = (float) Math.pow((double) super.getTransitionPosition(),
				2.0);
		Vector2f vector = new Vector2f(0f, (float) ((super.getScreenManager()
				.getGame().getHeight() / 2) - (this.menuEntries.get(0)
				.GetHeight(this) + (70 * this.menuEntries.size()))));
		for (int i = 0; i < this.menuEntries.size(); i++) {
			MenuEntry entry = this.menuEntries.get(i);
			vector.x = (LSystem.screenRect.width / 2)
					- (entry.GetWidth(this) / 2);
			if (super.getScreenState() == ScreenState.TransitionOn) {
				vector.x -= num * 256f;
			} else {
				vector.x += num * 512f;
			}
			entry.setPosition(vector);
			vector.y += entry.GetHeight(this) + 70;
		}
	}

	protected final java.util.List<MenuEntry> getMenuEntries() {
		return this.menuEntries;
	}

	private ScreenType privateScreenType;

	public final ScreenType getScreenType() {
		return privateScreenType;
	}

	public final void setScreenType(ScreenType value) {
		privateScreenType = value;
	}
}