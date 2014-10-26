package relief.game1;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class Game1Activity extends Activity {
    /** Called when the activity is first created. */
    private MySurfaceView msv;
    private SharedPreferences sp ;
	private String record;
	public static int screenWidth,screenHeight;
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
   
         //全屏显示
        Game1Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics  dm = new DisplayMetrics();  
    	//取得窗口属性  
        System.out.println("Game1Activity");
    	getWindowManager().getDefaultDisplay().getMetrics(dm);    
    	//窗口的宽度  
    	screenWidth = dm.widthPixels;  
    	//窗口高度  
    	screenHeight = dm.heightPixels;
    	
    	msv = new MySurfaceView(Game1Activity.this);
        setContentView(msv);
    }
     
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		msv.stopAllThread();
		super.onDestroy();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	menu.add(0, 1, 1, R.string.menu0);
    	menu.add(0, 2, 2, R.string.menu1);  
    	menu.add(0, 3, 3, R.string.menu2);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1)
		{
			msv.restart();
		}
		if(item.getItemId()==2) 
		{
			
			msv.stopAllThread();
			finish();
		}
		if(item.getItemId()==3) 
		{
			Intent intent = new Intent(Game1Activity.this,
					About.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	public void vibrate() {
		// TODO Auto-generated method stub
	    Vibrator vibrator;
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		long[] patten = {0,500,500};
		vibrator.vibrate(patten,-1);
	}
    	
	public String getrecord(){
		sp = this.getSharedPreferences("sharePre",Context.MODE_PRIVATE);
		record = sp.getString("record", "00:00");
		return record;		
	}
	public void freshrecord(char ch4,char ch3,char ch2,char ch1,char ch0){
		String str;
		char ch[] = new char [7];
		if (ch4 == '0')
		{
			ch[0] = ch3; ch[1] = ch2; ch[2] = ':'; ch[3] = ch1; ch[4] = ch0;
			str = String.valueOf(ch, 0, 5);
		}
		else {
			ch[0] = ch4; ch[1] = ch3; ch[2] = ch2; ch[3] = ':'; ch[4] = ch1; ch[5] = ch0; 
			str = String.valueOf(ch, 0, 6);
		}
		if (str.length() > record.length() || (str.length() == record.length() && str.compareTo(record) > 0))  //如果比记录大，更新记录
		{
			SharedPreferences.Editor editor= sp.edit(); 
			editor.putString("record", str);
			editor.commit();
			record = str;
		}
	}
	public void Evaluation(int a,int b,int c,char a1,char a2,char a3,char a4,char a5){
		Intent intent = new Intent(Game1Activity.this,
				Evaluation.class);
		int min,sec;
		min = (a2 - 48)*10 + a3 - 48;
		if (a1 != '0') min += (a1-48)*100;
		sec = (a4 - 48)*10 + a5 - 48;
		intent.putExtra("gold", a);
		intent.putExtra("cool", b);
		intent.putExtra("wudi", c);
		intent.putExtra("min", min);
		intent.putExtra("sec", sec);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}