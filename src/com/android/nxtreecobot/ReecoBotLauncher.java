package com.android.nxtreecobot;

import com.android.nxtreecobot.standalone.DrivingLabyrinthStandalone;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ReecoBotLauncher extends Activity implements OnClickListener {
	
	private Button vButton;
	private RadioButton rdb1;
	private RadioButton rdb2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reeco_bot_launcher);
        
        vButton = (Button) findViewById(R.id.valide_mode);
        rdb1 = (RadioButton) findViewById(R.id.rb1);
        rdb2 = (RadioButton) findViewById(R.id.rb2);
        
        vButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_reeco_bot_launcher, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.valide_mode:
			if (rdb1.isChecked()) {
				Toast.makeText(this, rdb1.getText().toString(), Toast.LENGTH_SHORT).show();
		        Intent driveIntent = new Intent(this, DrivingLabyrinthStandalone.class);
				startActivity(driveIntent);
			} else {
				Toast.makeText(this, rdb2.getText().toString(), Toast.LENGTH_SHORT).show();
				Intent remoteIntent = new Intent(this, RemoteControlTab.class);
				startActivity(remoteIntent);
			}
		break;
		}
	}

    
}
