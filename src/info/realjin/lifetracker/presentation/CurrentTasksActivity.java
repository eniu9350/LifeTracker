package info.realjin.lifetracker.presentation;

import info.realjin.lifetracker.R;
import info.realjin.lifetracker.entity.Task;
import info.realjin.lifetracker.entity.TimeSlice;
import info.realjin.lifetracker.services.TimerService;

import java.util.List;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
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

			// update tasks progress
			MainActivity ma = (MainActivity) CurrentTasksActivity.this
					.getParent();
			updateProgresses();

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
		getApplicationContext().bindService(bindIntent, serviceConnection,
				Context.BIND_AUTO_CREATE);

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

	// ========update tasks ui
	public void updateProgresses() {
		Log.i("child count=", String.valueOf(((TabActivity) getParent())
				.getTabHost().getChildCount()));

		List<Task> tasks = ((MainActivity) getParent()).getTasks();

		long now;
		int i;
		for (i = 0; i < tasks.size(); i++) {
			Task t = tasks.get(i);
			List<TimeSlice> slices = t.getTimeSlices();
			if (slices.size() == 0) {
				now = System.currentTimeMillis();
				updateProgress(i, t, now);
				// mmm:error
				continue;
			} else {
				now = System.currentTimeMillis();
				updateProgress(i, t, now);

			}

		}
	}

	/**
	 * @param position
	 * @param t
	 * @param now
	 *            for synchronizing use
	 */
	private void updateProgress(int position, Task t, long now) {
		// draw
		long interval;
		List<TimeSlice> slices = t.getTimeSlices();
//		interval = now - slices.get(0).getStart();
		interval = 20000;

		int n;// count of rect
		double portion; // portion of the residule
		n = (int) (interval / (60 * 15));
		portion = (interval - n * (60 * 15)) / (60 * 15);

		RelativeLayout rlo = (RelativeLayout) findViewById(R.id.rloTasks);
		TaskView tskv = new TaskView(this, n, portion);
		rlo.addView(tskv);
	}

	public class TaskView extends View {
		private int n;
		private double portion;
		Paint paint = new Paint();
		
		
		public TaskView(Context context, int n, double portion) {
			super(context);
			this.n = n;
			this.portion = portion;
			
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
		}

		@Override
		public void onDraw(Canvas canvas) {
			Log.i("==draw===","===");
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(3);

			canvas.drawRect(330, n * 100 + 30, 380, n * 100 + 80, paint);
			paint.setStrokeWidth(0);
		}

	}
}
