﻿package com.chuanonly.train;

import android.R.integer;
import android.util.Log;


public class StateLevelSelect extends GameState
{

	private final String LASTPAGER = "lastPage_";
	private int fadeOutTicks;

	private int mLevelOffset;
	private int levelOffset;
	private Sprite levelSelectButton;
	private Button levelSelectButtonBack;
	private Button levelSelectButtongo;
	private Sprite levelSelectButtonDefault;
	private java.util.ArrayList<Button> levelSelectLevels = new java.util.ArrayList<Button>();
	private Sprite levelSelectLock;
	private Sprite levelselectpanel;
	private int mainLevelSelect;
	private Sprite menubg;
	private Sprite numberSprite;
	private boolean trial;
	private Sprite trialFullBig;
	private int offsetPage = 0;
	public StateLevelSelect(GameCore parent)
	{
		super.initState(parent);
		this.levelSelectButton = new Sprite("levelnormalselection", 2, 1, 9, true);
		this.levelSelectButtonDefault = new Sprite("leveldefault1002", 2, 1, 9, true);
		this.levelSelectLock = new Sprite("lock10", 1, 1, 9, true);
		this.levelSelectButtonBack = new Button(EButtonTypes.ENormal, "btnback", 9, 0);
		this.levelSelectButtongo = new Button(EButtonTypes.ENormal, "btngo", 9, 0);
		this.numberSprite = new Sprite("numbers3", 10, 1, 9, true);
		this.menubg = new Sprite("menubg", 1, 1, 9, true);
		this.trialFullBig = new Sprite("trial-fullbig", 1, 1, 0x12, true);
	}

	@Override
	public void activateState()
	{
		this.trial = super.game.isTrial();
		this.fadeOutTicks = -1;
		this.mainLevelSelect = super.game.getValue(EValues.EValueSelectedMainLevel);
		if (mainLevelSelect == 0)
		{
			this.levelOffset = 0;
		}else if (mainLevelSelect == 1)
		{
			this.levelOffset = 30;
		}else if (mainLevelSelect == 2)
		{
			this.levelOffset = 60;
		}else if (mainLevelSelect == 3)
		{
			this.levelOffset = 90;
		}
		mLevelOffset = levelOffset;
		offsetPage = Util.getIntFromSharedPref(LASTPAGER+mainLevelSelect, 0);
		mLevelOffset = levelOffset+  offsetPage * 15 ;
		this.refreshButtons();
		if (this.mainLevelSelect == 0 || this.mainLevelSelect == 3)
		{
			this.levelselectpanel = new Sprite("levelselectpanel1", 1, 1, 9, false);
		}
		else if (this.mainLevelSelect == 1)
		{
			this.levelselectpanel = new Sprite("levelselectpanel2", 1, 1, 9, false);
		}
		else
		{
			this.levelselectpanel = new Sprite("levelselectpanel3", 1, 1, 9, false);
		}
		this.levelSelectButtonBack.disable(false);
		super.game.startMenuMusic(false);
	}
	@Override
	public void backButtonPressed()
	{
		super.game.changeState(EStates.EGameStateMainLevelSelect);
	}

	@Override
	public void deactivateState()
	{
		this.levelSelectLevels.clear();
		this.levelselectpanel = null;
	}

	@Override
	public void paint(Painter painter)
	{
		int w = super.game.getW();
		int h = super.game.getH();
		for (int i = 0; i < w; i += this.menubg.getWidth())
		{
			this.menubg.Paint(painter, (float) i, 0f, 0, 0f);
		}
		int num4 = -1;
		int num5 = 0;
		int num6 = 50;
		int num7 = 0xe0;
		int num8 = 110;
		int num9 = 0x90;
		for (int j = 0; j < this.levelSelectLevels.size(); j++)
		{
			num5++;
			if (this.trial && (j == 5))
			{
				painter.setOpacity(0.5f);
			}
			if ((j % 5) == 0)
			{
				num4++;
				num5 = 0;
			}
			if (this.levelSelectLevels.get(j).paint(painter, super.game, num7 + (num8 * num5), num6 + (num4 * num9)))
			{
				super.game.clearMouseStatus();
				super.game.setValue(EValues.EValueSelectedLevel, (j + 1) + this.mLevelOffset);
				this.fadeOutTicks = 0;
				super.game.doButtonPressSound();
				for (int k = 0; k < this.levelSelectLevels.size(); k++)
				{
					this.levelSelectLevels.get(k).disable(true);
				}
				this.levelSelectButtonBack.disable(true);
				super.game.stopMenuMusic(false);
			}
			if (this.levelSelectLevels.get(j).m_flags == 1)
			{
				this.levelSelectLock.Paint(painter, (float)((((this.levelSelectLevels.get(j).getW() / 2) + num7) + (num8 * num5)) + 8), (float)((((this.levelSelectLevels.get(j).getH() / 2) + num6) + (num4 * num9)) + 8), 0);
			}
			if (((j + 1) + this.mLevelOffset) < 10)
			{
				int num12 = (num7 + (num8 * num5)) + ((this.levelSelectLevels.get(j).getW() / 2) - (this.numberSprite.getWidth() / 2));
				int num13 = (num6 + (num9 * num4)) + ((this.levelSelectLevels.get(j).getH() / 2) - (this.numberSprite.getHeight() / 2));
				this.numberSprite.Paint(painter, (float) num12, (float) num13, (1 + j) + this.mLevelOffset);
			}
			else if (((j + 1) + this.mLevelOffset) < 100)
			{
				int num14 = (num7 + (num8 * num5)) + ((this.levelSelectLevels.get(j).getW() / 2) - this.numberSprite.getWidth());
				int num15 = (num6 + (num9 * num4)) + ((this.levelSelectLevels.get(j).getH() / 2) - (this.numberSprite.getHeight() / 2));
				this.numberSprite.Paint(painter, (float) num14, (float) num15, ((1 + j) + this.mLevelOffset) / 10);
				this.numberSprite.Paint(painter, (float)(num14 + this.numberSprite.getWidth()), (float) num15, ((1 + j) + this.mLevelOffset) % 10);
			}else if (((j + 1) + this.mLevelOffset) < 1000)
			{
				int num14 = (num7 + (num8 * num5)) + ((this.levelSelectLevels.get(j).getW() / 2) - this.numberSprite.getWidth());
				int num15 = (num6 + (num9 * num4)) + ((this.levelSelectLevels.get(j).getH() / 2) - (this.numberSprite.getHeight() / 2));
				this.numberSprite.Paint(painter, (float)(num14 - this.numberSprite.getWidth()), (float) num15, ((1 + j) + this.mLevelOffset) / 100);
				this.numberSprite.Paint(painter, (float) num14, (float) num15, ((1 + j) + this.mLevelOffset) / 10);
				this.numberSprite.Paint(painter, (float)(num14 + this.numberSprite.getWidth()), (float) num15, ((1 + j) + this.mLevelOffset) % 10);
			}
		}
		if (this.trial)
		{
			painter.setOpacity(1.0f);
			this.trialFullBig.Paint(painter, (float)(num7 + ((w - num7) / 2)), (float)(num6 + ((num9 * 0x10) / 10)), 0);
			if (super.game.isMouseUp())
			{
				int num16 = super.game.getMouseX();
				int num17 = super.game.getMouseY();
				if ((num16 > (num7 + 10)) && (num17 > (num6 + ((num9 * 11) / 10))))
				{
					super.game.clearMouseStatus();
					super.game.setValue(EValues.EValueTrialClickedFrom, 50);
					super.game.changeState(EStates.EGameStateTrial);
				}
			}
		}
		this.levelselectpanel.Paint(painter, 0f, 0f, 0);
		
		//返回键
		if (this.levelSelectButtonBack.paint(painter, super.game, this.levelSelectButtonBack.getW() / 12, h - ((this.levelSelectButtonBack.getH() * 13) / 12)))
		{
			if (offsetPage <= 0)
			{				
				super.game.changeState(EStates.EGameStateMainLevelSelect);
				super.game.clearMouseStatus();
			}else
			{
				offsetPage -- ;
				Util.setIntToSharedPref(LASTPAGER+mainLevelSelect, offsetPage);
				this.mLevelOffset = levelOffset+  offsetPage * 15 ;
				refreshButtons();
			}
			super.game.doButtonPressSound();
		}
		
		//下一页键
		if ( this.levelSelectButtongo.paint(painter, super.game, this.levelSelectButtonBack.getW() / 12, h - ((this.levelSelectButtonBack.getH() * 13) / 12) - ((this.levelSelectButtonBack.getH() * 14) / 12) ))
		{
			if (offsetPage < 2)
			{				
				offsetPage ++ ;
				if (offsetPage >= 1 ) offsetPage = 1;
				Util.setIntToSharedPref(LASTPAGER+mainLevelSelect, offsetPage);
				this.mLevelOffset = levelOffset + offsetPage * 15 ;
				refreshButtons();
			}
			super.game.doButtonPressSound();
		}
		
		if (this.fadeOutTicks != -1)
		{
			this.fadeOutTicks++;
			if (this.fadeOutTicks >= 0x1c)
			{
				this.fadeOutTicks = 0x1c;
				super.game.changeState(EStates.EGameStateGame);
			}
			
		}
	}

	private void refreshButtons()
	{
		this.levelSelectLevels.clear();
		for (int i = 0; i < 15; i++)
		{
			int level = i + this.mLevelOffset;
			Button button;
			if (level == 0 || level== 30 || level== 60  ||
				super.game.getSettings().m_levels.get(level-1) > 0 )
			{
				button = new Button(EButtonTypes.ENormal, this.levelSelectButton, 9, 5, false);
			}else
			{
				button = new Button(EButtonTypes.ENormal, this.levelSelectButton, 9, 5, true);
				button.m_flags = 1;
			}
			this.levelSelectLevels.add(button);
		}
	}

	@Override
	public void tick()
	{
	}
}