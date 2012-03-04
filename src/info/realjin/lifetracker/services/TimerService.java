package info.realjin.lifetracker.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TimerService extends Service implements ITimerService {
	public final static String BROADCAST_COUNTER_ACTION = "shy.luo.broadcast.COUNTER_ACTION";
	public final static String COUNTER_VALUE = "shy.luo.broadcast.counter.value";
	private boolean stop = false;
	private IBinder binder = new TimerBinder();

	public class TimerBinder extends Binder {

		public TimerService getService() {
			return TimerService.this;
		}
	}

	public void start() {
		Log.i("timerservice", "starting");  
		AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {
			@Override
			protected Integer doInBackground(Integer... vals) {
				Integer initCounter = vals[0];

				stop = false;
				while (!stop) {
					publishProgress(initCounter);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					initCounter++;
				}

				return initCounter;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				super.onProgressUpdate(values);

				int counter = values[0];

				Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
				intent.putExtra(COUNTER_VALUE, counter);

				sendBroadcast(intent);
			}

			@Override
			protected void onPostExecute(Integer val) {
				int counter = val;

				Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
				intent.putExtra(COUNTER_VALUE, counter);

				sendBroadcast(intent);
			}

		};

		task.execute(0);
	}

	public void stop() {
		stop = true;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("timerservice", "binded");
		return binder;
	}
}
