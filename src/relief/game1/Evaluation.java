package relief.game1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.ViewDebug.FlagToString;
import android.widget.Button;
import android.widget.TextView;

public class Evaluation extends Activity{
	private TextView goldTextView;
	private TextView coolTextView;
	private TextView wudiTextView;
	private TextView scoreTextView;
	private TextView achieTextView;
	private Button   brestart;
	private Button   bback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.evaluation);
		
		goldTextView = (TextView)findViewById(R.id.goldTextView);
		coolTextView = (TextView)findViewById(R.id.coolTextView);
		wudiTextView = (TextView)findViewById(R.id.wudiTextView);
		scoreTextView = (TextView)findViewById(R.id.scoreTextView);
		achieTextView = (TextView)findViewById(R.id.achieTextView);
		
	  
		Intent intent = getIntent();
		int gold = intent.getIntExtra("gold", 0);
		int cool = intent.getIntExtra("cool", 0);
		int wudi = intent.getIntExtra("wudi", 0);
		int min = intent.getIntExtra("min", 0);
		int sec = intent.getIntExtra("sec", 0);
		goldTextView.setText("   " + gold+"");
		coolTextView.setText("   " + cool+"");
		wudiTextView.setText("   " + wudi+"");
		scoreTextView.setText(min + ":" + sec);
		achieTextView.setText(evaluate(gold,cool,wudi,min,sec));
		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {
					@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
						Intent intent = new Intent(Evaluation.this,
								Game1Activity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
				}
				});
		findViewById(R.id.button2).setOnClickListener(
				new View.OnClickListener() {
					@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
						Intent intent = new Intent(Evaluation.this,
								Menu.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
				}
				});
	}
	private CharSequence evaluate(int gold, int cool, int wudi, int min, int sec) {
		// TODO Auto-generated method stub
		if (min  > 100)return "Eol֮��!";
		if (gold > 10) return "�����";
		if (cool > 5)  return "���Ǳ���ɣ�";
		if (wudi > 5)  return "�����޵�";
		if (sec == 0)  return "�����׼��";
		if (min < 5)   return "���Ͱ�";
		if (min < 15)  return "���ұ����ǿ=��=";
		if (min < 25)  return "������Ҳ�˵��";
		if (min < 35)  return "�д���ߡ�";
		if (min < 45)  return "�ٽ�����";
		if (min < 55)  return "��������";
		if (min < 65)  return "ͦ�ܶ����";
		if (min < 75)  return "������ˢ��";
		if (min < 85)  return "����С����";
		if (min < 95)  return "̫��ϧ��ѽ��";
		if (min <=100) return "��������";
		return null;
	}  
}
