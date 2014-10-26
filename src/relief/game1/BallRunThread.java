package relief.game1;

public class BallRunThread extends Thread{
	MySurfaceView msv;
	Game1Activity ga;
	private float dis,xmove,ymove;
	public float aim_x,aim_y,current_x,current_y;
	private boolean  keeprun,pause;
	int picAlphaNum = 120;
	public BallRunThread(MySurfaceView msv){
		super();
		pause = false;
		keeprun = true;
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
		aim_x     = current_x;
		aim_y	  = current_y;
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
		msv.setBall(Game1Activity.screenWidth/2, Game1Activity.screenHeight/2);
	}
	public void run(){
		super.run();
		while(true){
				if (!keeprun) return;
				current_x = msv.ballX;
				current_y = msv.ballY;
				aim_x     = msv.aim_x;
				aim_y	  = msv.aim_y;
				while (pause) dopause();
				if (current_x>=0 && current_y>=0 && aim_x >=0 && aim_y >=0)
				{
					dis = (float)Math.sqrt(((current_x-aim_x)*(current_x-aim_x)+(current_y-aim_y)*(current_y-aim_y)));
					if (dis < Constant.BALLSPEED) 
					{
						current_x = aim_x;
						current_y = aim_y;
						msv.aim_x = -1;
						msv.aim_y = -1;
					}
					else 
					{
						xmove     = (aim_x-current_x)/dis * Constant.BALLSPEED;
						ymove     = (aim_y-current_y)/dis * Constant.BALLSPEED;
						current_x= current_x + xmove;
					    current_y= current_y + ymove;
					}
					
					msv.setBall(current_x,current_y);
					try{
						Thread.sleep(Constant.BALLINTERVAL);
					}catch(Exception e){e.printStackTrace();
					}
				}
		}
	}
}
