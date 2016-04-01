package com.shangx.hb_money_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

/**
 *	ʵʱ���������ʵʱ����������չ��
 */
public class RSplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
		SplashAdListener listener=new SplashAdListener() {
			@Override
			public void onAdDismissed() {
				Log.i("RSplashActivity", "onAdDismissed");
				jumpWhenCanClick();// ��ת������Ӧ��������
			}

			@Override
			public void onAdFailed(String arg0) {
				Log.i("RSplashActivity", "onAdFailed");
				jump();
			}

			@Override
			public void onAdPresent() {
				Log.i("RSplashActivity", "onAdPresent");
			}

			@Override
			public void onAdClick() {
				Log.i("RSplashActivity", "onAdClick");
				//���ÿ����ɽ��ܵ��ʱ���ûص�����
			}
		};
		
		/**
		 * ���캯����
		 * new SplashAd(Context context, ViewGroup adsParent,
		 * 				SplashAdListener listener,String adPlaceId, boolean canClick);
		 */
		String adPlaceId = "2373335"; // ��Ҫ������д����λid���ܳ����
		new SplashAd(this, adsParent, listener, adPlaceId, true);
		
	}
	
	
	/**
	 * �����ÿ����ɵ��ʱ����Ҫ�ȴ���תҳ��رպ����л������������ڡ��ʴ�ʱ��Ҫ����waitingOnRestart�жϡ�
	 * ���⣬�����������Ҫ��onRestart�е���jumpWhenCanClick�ӿڡ�
	 */
	public boolean waitingOnRestart=false;
	private void jumpWhenCanClick() {
		Log.d("test", "this.hasWindowFocus():"+this.hasWindowFocus());
		if(this.hasWindowFocus()||waitingOnRestart){
			this.startActivity(new Intent(RSplashActivity.this, MainActivity.class));
			this.finish();
		}else{
			waitingOnRestart=true;
		}
		
	}
	
	/**
	 * ���ɵ���Ŀ�����ʹ�ø�jump��������������jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(RSplashActivity.this, MainActivity.class));
		this.finish();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(waitingOnRestart){
			jumpWhenCanClick();
		}
	}
	
}
