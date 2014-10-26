package relief.game1;


import net.youmi.android.AdManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Menu extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		 super.onCreate(savedInstanceState);
	     //AdManager.init(this,"30ef9ce9aae464a1", "136b4dc23af48fb6", 30, false);
	     
	     setContentView(R.layout.main);

	     // Set the button of newGame
	     findViewById(R.id.newGame).setOnClickListener(
		 new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Menu.this,
							Game1Activity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
	    // Set the button of exitGame
		findViewById(R.id.exitGame).setOnClickListener(
			new View.OnClickListener() {
				@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
					finish();
			}
			});
	    	
	    }
}


