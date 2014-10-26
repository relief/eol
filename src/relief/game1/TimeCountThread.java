package relief.game1;

import relief.game1.Constant.ItemType;

public class TimeCountThread extends Thread{
    MySurfaceView msv;
    private int n4 = 0,n3 = 0 ,n2 = 0 ,n1 = 0, n0 = 0;
    private boolean keeprun,pause,goldeaten; 
	public TimeCountThread(MySurfaceView msv){
		super();
		pause   = false;
		keeprun = true;
		goldeaten = false;
		this.msv = msv;
	}
	public void stopit(){
		keeprun = false;
	}
	public void pauseit(){
		pause = true;
	}
	public void resumeit(){
		pause = false;		
	}
	private void dopause(){
		try{
			Thread.sleep(50);
		}catch(Exception e){e.printStackTrace();
		}
	}
	public void restart(){
		pause = false;
		keeprun = true;
		n4 = 0;
		n3 = 0;
		n2 = 0;
		n1 = 0;
		n0 = 0;
	}
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true){
			if (!keeprun) return;
			while (pause) dopause();
			try {
				Thread.sleep(Constant.TIMEINTERVAL);
			} catch (Exception e) {
			// TODO: handle exception
			}
			int count;
			if (msv.getBuff(ItemType.Cool)){
				count = msv.getCount(ItemType.Cool);
				if (count == 700) msv.setBuff(ItemType.Cool, false);
				else msv.setCount(ItemType.Cool, count + 1);
			}
			if (msv.getBuff(ItemType.Wudi)){
				count = msv.getCount(ItemType.Wudi);
				if (count == 700) msv.setBuff(ItemType.Wudi, false);
				else msv.setCount(ItemType.Wudi, count + 1);
			}
			
			else 
			if (goldeaten){
				for (int i=0;i<3;i++) addSecond();
				goldeaten = false;
			}else addMiniSecond();
			msv.setTime(n4, n3, n2, n1, n0);
		}
	}
	private void addSecond() {
		// TODO Auto-generated method stub
		if (n2<9) n2 = n2 + 1;
		else 
		{
			n2 = 0;
			if (n3 < 9) n3 = n3 + 1;
			else 
			{
				n3 = 0;
				n4 = n4 + 1;
			}
		}
	}
	public void goldeaten()
	{
		goldeaten = true;
	}
	private void addMiniSecond()
	{
		if (n0 < 9) n0 = n0 + 1;
		else 
		{
			n0 = 0;
			if (n1 <5) n1 = n1 + 1;
			else 
			{
				n1 = 0;
				if (n2<9) n2 = n2 + 1;
				else 
				{
					n2 = 0;
					if (n3 < 9) n3 = n3 + 1;
					else 
					{
						n3 = 0;
						n4 = n4 + 1;
					}}}}
	}		
		
}


