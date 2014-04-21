package com.maycapps.crystal.ball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.maycapps.crystal.ball.R;
import com.maycapps.crystal.ball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	// Member variables
	private CrystalBall mCrystalBall;
	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;
	// private Button mGetAnswerButton;

	// Motion Detection member variables
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Assign the Views from the layout file
		mAnswerLabel = (TextView) findViewById(R.id.predicionText);
		mCrystalBall = new CrystalBall(getResources().getStringArray(
				R.array.answers));
		mCrystalBallImage = (ImageView) findViewById(R.id.crystalBall);

		// Initialize sensor variables
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {

			@Override
			public void onShake() {
				handleNewAnswer();
			}
		});

		// Show toast notification
		// Toast.makeText(this, "Yay! Our activity was created!",
		// Toast.LENGTH_LONG).show();

		/*
		 * mGetAnswerButton = (Button) findViewById(R.id.predictionButton);
		 * mGetAnswerButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * // Get an answer from the CrystalBall object String answer =
		 * mCrystalBall.getAnAnswer();
		 * 
		 * // Update the label with our dynamic answer
		 * mAnswerLabel.setText(answer);
		 * 
		 * // Set animations animateCrystalBall(); animateAnswer(); playSound();
		 * } });
		 */

		Log.d(TAG, "On Create method");
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);

	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

	private void animateCrystalBall() {
		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage
				.getDrawable();

		// Stop animation if it's already running (Android bug)
		if (ballAnimation.isRunning()) {
			ballAnimation.stop();
		}

		ballAnimation.start();
	}

	private void animateAnswer() {
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(1500);
		fadeInAnimation.setFillAfter(true);

		mAnswerLabel.setAnimation(fadeInAnimation);
	}

	private void playSound() {
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void handleNewAnswer() {
		// Get an answer from the CrystalBall object
		String answer = mCrystalBall.getAnAnswer();

		// Update the label with our dynamic answer
		mAnswerLabel.setText(answer);

		// Set animations
		animateCrystalBall();
		animateAnswer();
		playSound();
	}

}
