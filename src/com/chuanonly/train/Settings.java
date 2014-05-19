package com.chuanonly.train;

import java.util.ArrayList;


public class Settings
{
	public java.util.ArrayList<Integer> m_levels;
	public boolean m_sounds;
	
	private final String LEVEL ="level_";
	
	public Settings()
	{
		  this.m_levels = new ArrayList<Integer>();
          for (int i = 0; i < 150; i++)
          {
        	  int levelSuccessCnt = Util.getIntFromSharedPref(LEVEL+i, -1);
              this.m_levels.add(levelSuccessCnt);
          }
          m_sounds = Util.isSoundSettingOn();
	}

	public final void Save()
	{

	}
	
	public void saveSuccessLevel(int level)
	{
		int successCnt = Util.getIntFromSharedPref(LEVEL+level, 0);
		successCnt = Math.max(1, successCnt+1);
		Util.setIntToSharedPref(LEVEL+level, successCnt);
		m_levels.set(level, successCnt);
	}

	public void setDefaultSettings()
	{
		this.m_sounds = true;
		for (int i = 0; i < 150; i++)
		{
			int levelSuccessCnt = Util.getIntFromSharedPref(LEVEL+i, -1);
			this.m_levels.set(i, levelSuccessCnt);
		}
		this.Save();
	}

	public void saveSoundsetting(boolean isSoundOn) {
		Util.setSoundSettingON(isSoundOn);
		if (isSoundOn)
		{			
			MainActivity.handlerMessage(MainActivity.MUSIC_START);
		}else
		{
			MainActivity.handlerMessage(MainActivity.MUSIC_STOP);
		}
	}
}