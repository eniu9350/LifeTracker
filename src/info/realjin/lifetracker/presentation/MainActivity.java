package info.realjin.lifetracker.presentation;

import info.realjin.lifetracker.R;
import info.realjin.lifetracker.entity.Task;
import info.realjin.lifetracker.entity.TimeSlice;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

/**
 * container of the "current task tab" and "summary tab"
 * 
 * @author REALJIN
 * 
 */
public class MainActivity extends TabActivity {
	private List<Task> tasks;
	private Canvas canvas;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public MainActivity() {
		super();
		tasks = new ArrayList<Task>();
		Task a = new Task();
		Task b = new Task();
		tasks.add(a);
		tasks.add(b);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, CurrentTasksActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("artists")
				.setIndicator("Artists",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(intent);
		tabHost.addTab(spec);
	}

	
}
