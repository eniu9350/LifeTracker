package info.realjin.lifetracker;

import info.realjin.lifetracker.persistence.Dao;
import info.realjin.lifetracker.persistence.DbHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LifeTrackerActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		// test
		DbHelper helper = DbHelper.getHelper(this);
		Dao dao = helper.getDao();

		// init table if not exist
		boolean exist = dao.isInfoExist();
		Log.e("info exist====", String.valueOf(exist));
		if (!exist) {
			dao.createInfo();
			Log.e("===complete===", "info creation");
		}

		// test
		Button bt = (Button) findViewById(R.id.SerialMain_btStart);
		bt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
			}

		});

	}

}