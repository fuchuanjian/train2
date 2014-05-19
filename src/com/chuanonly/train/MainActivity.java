
package com.chuanonly.train;

import java.lang.ref.WeakReference;

import loon.LGame;
import loon.LSetting;
import loon.core.graphics.opengl.LTexture;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;


public class MainActivity extends LGame {
	private AdView mAdView;
	private InterstitialAd mInterstitialAdView;
	private static WeakReference<Handler> mHandlerRef;
	private SoundPlayHelper mSoundPlay;
	
	public static final int MSG_SHOW_AD = 0;
	public static final int MSG_HIDE_AD = 1;
	public static final int MSG_SHOW_FULLSCREEN_AD = 100;
	public static final int MSG_SOUND = 2;
	
	public static final int MUSIC_START = 3;
	public static final int MUSIC_STOP = 4;
	
	//soundid
	public static final int SOUND_BUTTON = 0;
	public static final int SOUND_WU = 1;
	public static final int SOUND_SUCEESS = 2;
	public static final int SOUND_CRASH = 3;
	public static final int SOUND_DANGDANG = 4;
	public static final int SOUND_CLICK = 5;
	
	@Override
	public void onGamePaused() {
		mHandler.sendEmptyMessage(MUSIC_STOP);
	}

	@Override
	public void onGameResumed() {
		mHandler.sendEmptyMessageDelayed(MUSIC_START, 1500);
	}

	@Override
	public void onMain() {
		LTexture.ALL_LINEAR=true;
		LSetting setting = new LSetting();
		setting.width = 800;
		setting.height = 480;
		setting.showFPS = false;
		setting.landscape = true;
		setting.fps = 30;
		register(setting, GameMain.class);
		if (Util.isNetworkAvailable(APP.getContext()))
		{
			loadAd();			
		}
		_bottomLayout.bringToFront();
		_bottomLayout.setVisibility(View.GONE);
		mHandlerRef = new WeakReference<Handler>(mHandler);
		 mSoundPlay = new SoundPlayHelper();
	     mSoundPlay.initSounds(this);
	     mSoundPlay.loadSfx(this, R.raw.button, SOUND_BUTTON);
	     mSoundPlay.loadSfx(this, R.raw.wu, SOUND_WU);
	     mSoundPlay.loadSfx(this, R.raw.dangdang, SOUND_DANGDANG);
	     mSoundPlay.loadSfx(this, R.raw.burst, SOUND_CRASH);
	     mSoundPlay.loadSfx(this, R.raw.win, SOUND_SUCEESS);
	     mSoundPlay.loadSfx(this, R.raw.click, SOUND_CLICK);
	}
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if (msg.what == MSG_SHOW_AD)
			{
				int cnt = Util.getLoginCnt();
				if (cnt >=0)
				{					
					if (mAdView == null)
					{
						loadAd();
					}
					_bottomLayout.setVisibility(View.VISIBLE);				
				}
			}else if (msg.what == MSG_HIDE_AD)
			{
				_bottomLayout.setVisibility(View.GONE);
			}else if (msg.what == MSG_SHOW_FULLSCREEN_AD)
			{
				showFullAd();
			}else if (msg.what == MSG_SOUND)
			{
				int soundId = msg.arg1;
				mSoundPlay.play(soundId, 0);
			}else if (msg.what == MUSIC_START)
			{
				if (Util.isSoundSettingOn())
				{					
					mSoundPlay.playBGMusic();
				}
			}else if (msg.what == MUSIC_STOP)
			{
				mHandler.removeMessages(MUSIC_START);
				mSoundPlay.stopBGMusic();
			}
		};
	};
	public static void showAd()
	{
		if (Util.isNetworkAvailable(APP.getContext()))
		{			
			Handler h =  mHandlerRef.get();
			if (h != null)
			{
				h.sendEmptyMessage(MSG_SHOW_AD);
			}
		}
	}
	public static void showFullScreenAd()
	{
		if (Util.isNetworkAvailable(APP.getContext()))
		{			
			Handler h =  mHandlerRef.get();
			if (h != null)
			{
				h.sendEmptyMessage(MSG_SHOW_FULLSCREEN_AD);
			}
		}
	}
	
	public static void hideAd()
	{
		if (Util.isNetworkAvailable(APP.getContext()))
		{			
			Handler h =  mHandlerRef.get();
			if (h != null)
			{
				h.sendEmptyMessage(1);
			}
		}
	}
	private boolean hasLoad = false;
	private void showFullAd()
	{
		try {
			if (mInterstitialAdView == null)
			{
				mInterstitialAdView = new InterstitialAd(this, "a153786583df627");
			}
			if (hasLoad == false)
			{
				mInterstitialAdView.loadAd(new AdRequest());
				hasLoad = true;
				mInterstitialAdView.setAdListener(new AdListener() {
					@Override
					public void onReceiveAd(Ad arg0) {
						if (arg0 == mInterstitialAdView) {
							mInterstitialAdView.show();
						}
					}
					
					@Override
					public void onPresentScreen(Ad arg0) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onLeaveApplication(Ad arg0) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onDismissScreen(Ad arg0) {
					}
				});
			}else {
				mInterstitialAdView.show();
			}
		} catch (Exception e) {
		}
		
	}
	private void loadAd()
	{
		mAdView = new AdView(this, AdSize.BANNER, "a153786c3c2e9ed");
		_bottomLayout.addView(mAdView);
		mAdView.loadAd(new AdRequest());	
		mAdView.setAdListener(new AdListener() {
			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPresentScreen(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLeaveApplication(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDismissScreen(Ad arg0) {
				Util.setLoginCnt(-3);
				_bottomLayout.setVisibility(View.GONE);
			}
		});
	}
	
	public static void playSound(int soundID)
	{
		if (Util.isSoundSettingOn())
		{			
			Handler h =  mHandlerRef.get();
			if (h != null)
			{
				Message msg = h.obtainMessage();
				msg.what = MSG_SOUND;
				msg.arg1 = soundID;
				msg.sendToTarget();
			}
		}
	}
	@Override
	protected void onDestroy()
	{
		mSoundPlay.release();
		super.onDestroy();
	}
	
	public static void handlerMessage(int mgsWhat)
	{
		Handler h =  mHandlerRef.get();
		if (h != null)
		{
			h.sendEmptyMessage(mgsWhat);
		}
	}
}
