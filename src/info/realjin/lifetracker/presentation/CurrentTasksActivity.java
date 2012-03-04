package info.realjin.lifetracker.presentation;

import info.realjin.lifetracker.R;
import info.realjin.lifetracker.services.TimerService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CurrentTasksActivity extends Activity {
	TimerService timerService;

	boolean started = false;

	private BroadcastReceiver timerActionReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			int counter = intent.getIntExtra(TimerService.COUNTER_VALUE, 0);
			String text = String.valueOf(counter);
			TextView tv = (TextView) CurrentTasksActivity.this
					.findViewById(R.id.tvTest);
			tv.setText(text);
			// counterText.setText(text);
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.i("timerservice", "Counter Service Connected");  
			timerService = ((TimerService.TimerBinder) service).getService();
			timerService.start();
		}

		public void onServiceDisconnected(ComponentName className) {
			timerService = null;
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currenttasks);

		// bind service
		Log.i("timerservice", "to bind.....");
		Intent bindIntent = new Intent(CurrentTasksActivity.this,
				TimerService.class);
		getApplicationContext().bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);

		// temp
		TextView tv = (TextView) CurrentTasksActivity.this
				.findViewById(R.id.tvTest);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (timerService != null) {
					if (started) {
						timerService.stop();
						started = false;
					} else {
						timerService.start();
						started = true;
					}
				}
			}
		});

	}

	public void onResume() {
		super.onResume();
		IntentFilter counterActionFilter = new IntentFilter(
				TimerService.BROADCAST_COUNTER_ACTION);
		registerReceiver(timerActionReceiver, counterActionFilter);
	}

	
}
