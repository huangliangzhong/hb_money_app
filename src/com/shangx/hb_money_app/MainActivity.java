package com.shangx.hb_money_app;

import java.util.List;

import org.json.JSONObject;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;


public class MainActivity extends Activity {
    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    private TextView mAccessibleLabel;
    private TextView mNotificationLabel;
    private TextView mLabelText;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        final float density = metrics.density;
        final int screenWidth = metrics.widthPixels;
        ad();
        initBanner();
        int width = (int) (screenWidth - (density * 12 + .5f) * 2);
        int height = (int) (366.f * width / 1080);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
//        ImageView imageView1 = (ImageView) findViewById(R.id.image_accessibility);
//        ImageView imageView2 = (ImageView) findViewById(R.id.image_notification);

        mAccessibleLabel = (TextView) findViewById(R.id.label_accessible);
        mNotificationLabel = (TextView) findViewById(R.id.label_notification);
        mLabelText = (TextView) findViewById(R.id.label_text);

//        imageView1.setLayoutParams(lp);
//        imageView2.setLayoutParams(lp);

        if (Build.VERSION.SDK_INT >= 18) {
//            imageView2.setVisibility(View.VISIBLE);
            mNotificationLabel.setVisibility(View.VISIBLE);
            findViewById(R.id.button_notification).setVisibility(View.VISIBLE);
        } else {
//            imageView2.setVisibility(View.GONE);
            mNotificationLabel.setVisibility(View.GONE);
            findViewById(R.id.button_notification).setVisibility(View.GONE);
        }

//        imageView1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("test", "# fired");
//                unlockScreen();
//
//            }
//        }, 5000L);
    }

    private void initBanner(){
    	 LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
//       AdView adView = new AdView(this, AdSize.FIT_SCREEN);
//       adLayout.addView(adView);
//       EcManager.startFloatWindowService(this);
		
		// ��������AppSid��Appsec���˺���������AdViewʵ����ǰ����
			// AdView.setAppSid("debug");
			// AdView.setAppSec("debug");
			
			// ��Ⱥ����
			AdSettings.setKey(new String[] { "baidu", "�� �� " });
			// AdSettings.setSex(AdSettings.Sex.FEMALE);
			// AdSettings.setBirthday(Calendar.getInstance());
			// AdSettings.setCity("�Ϻ�");
			// AdSettings.setZip("123456");
			// AdSettings.setJob("����ʦ");
			// AdSettings.setEducation(AdSettings.Education.BACHELOR);
			// AdSettings.setSalary(AdSettings.Salary.F10kT15k);
			// AdSettings.setHob(new String[]{"��ë��", "����", "baseball"});
			// AdSettings.setUserAttr("k1","v1");
			// AdSettings.setUserAttr("k2","v2");
			
			// �������View
			String adPlaceId = "2373334"; // ��Ҫ������д����λid���ܳ����
			adView = new AdView(this, adPlaceId);
			// ���ü�����
			adView.setListener(new AdViewListener() {
				public void onAdSwitch() {
					Log.w("", "onAdSwitch");
				}
				public void onAdShow(JSONObject info) {
					// ����Ѿ���Ⱦ����
					Log.w("", "onAdShow " + info.toString());
				}
				public void onAdReady(AdView adView) {
					// ��Դ�Ѿ�������ϣ���û����Ⱦ����
					Log.w("", "onAdReady " + adView);
				}
				public void onAdFailed(String reason) {
					Log.w("", "onAdFailed " + reason);
				}
				public void onAdClick(JSONObject info) {
					Log.w("", "onAdClick " + info.toString());
				}
				public void onVideoStart() {
					Log.w("","onVideoStart");
				}
				public void onVideoFinish() {
					Log.w("","onVideoFinish");
				}
			});
		
		//��adView��ӵ����ؼ���(ע���ø��ؼ���һ��Ϊ���ĸ��ؼ���ֻҪ�ÿؼ���ͨ��addView����ӹ����ͼ����)
//		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adLayout.addView(adView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeLabelStatus();
    }

    private void changeLabelStatus() {
        boolean isAccessibilityEnabled = isAccessibleEnabled();
        mAccessibleLabel.setTextColor(isAccessibilityEnabled ? 0xFF009588 : Color.RED);
        mAccessibleLabel.setText(isAccessibleEnabled() ? "���������Ѵ�" : "��������δ��");
        mLabelText.setText(isAccessibilityEnabled ? "����~�����ȥ�����������ˣ��һ��Զ������������" : "��򿪿��ؿ�ʼ�����");

        if (Build.VERSION.SDK_INT >= 18) {
            boolean isNotificationEnabled = isNotificationEnabled();
            mNotificationLabel.setTextColor(isNotificationEnabled ? 0xFF009588 : Color.RED);
            mNotificationLabel.setText(isNotificationEnabled ? "����֪ͨ�Ѵ�" : "����֪ͨδ��");

            if (isAccessibilityEnabled && isNotificationEnabled) {
                mLabelText.setText("����~�����ȥ�����������ˣ��һ��Զ������������");
            } else {
                mLabelText.setText("����������ض��򿪿�ʼ�����");
            }
        }
    }

    public void onStartDetail(View view) {
        startActivity(new Intent(MainActivity.this,DetailActivity.class));
    }
    
    public void onNotificationEnableButtonClicked(View view) {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    public void onSettingsClicked(View view) {
        startActivity(sSettingsIntent);
    }

    private boolean isAccessibleEnabled() {
        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            if (info.getId().equals(getPackageName() + "/.MonitorService")) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotificationEnabled() {
        ContentResolver contentResolver = getContentResolver();
        String enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");

        if (!TextUtils.isEmpty(enabledListeners)) {
            return enabledListeners.contains(getPackageName() + "/" + getPackageName() + ".NotificationService");
        } else {
            return false;
        }
    }

    private void showEnableAccessibilityDialog() {
        final ConfirmDialog dialog = new ConfirmDialog(this);
        dialog.setTitle("��Ҫ!").setMessage("����Ҫ��\"�к��\"�ĸ�������ѡ�������΢�ź��")
                .setPositiveButton("��", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(sSettingsIntent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("ȡ��", null);
        dialog.show();
    }
    
    private long mExitTime;
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	                if ((System.currentTimeMillis() - mExitTime) > 2000) {
	                        Object mHelperUtils;
	                        chabo();
	                        Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
	                        mExitTime = System.currentTimeMillis();

	                } else {
	                        finish();
	                }
	                return true;
	        }
	        return super.onKeyDown(keyCode, event);
	}
	 
	 InterstitialAd interAd;
	 public void chabo(){
			if(interAd.isAdReady()){
				interAd.showAd(MainActivity.this);
			}else{
				interAd.loadAd();
			}
		}
		
		private void ad(){
			String adPlaceId = "2362918"; // ��Ҫ������д����λid���ܳ����
			interAd=new InterstitialAd(this, adPlaceId);
			interAd.setListener(new InterstitialAdListener(){

				@Override
				public void onAdClick(InterstitialAd arg0) {
					Log.i("InterstitialAd","onAdClick");
				}

				@Override
				public void onAdDismissed() {
					Log.i("InterstitialAd","onAdDismissed");
					interAd.loadAd();
				}

				@Override
				public void onAdFailed(String arg0) {
					Log.i("InterstitialAd","onAdFailed");
				}

				@Override
				public void onAdPresent() {
					Log.i("InterstitialAd","onAdPresent");
				}

				@Override
				public void onAdReady() {
					Log.i("InterstitialAd","onAdReady");
				}
				
			});
			interAd.loadAd();
		}
}
