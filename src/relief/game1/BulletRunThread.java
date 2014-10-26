package relief.game1;

import relief.game1.Constant.ItemType;

public class BulletRunThread extends Thread{
	MySurfaceView msv;
	private float bulletX,bulletY,angle,ballx,bally,direction,speed;
	private boolean keeprun,pause;
	int n,i,count;

	
	public BulletRunThread(MySurfaceView msv){
		super();
		this.msv = msv;
		pause = false;
		keeprun = true;
		
	
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
		n = 0;		
	}
	public void deleteBullet(int i){
		bulletX 	= msv.bulletArray[n][0];
		bulletY 	= msv.bulletArray[n][1];
		angle   	= msv.bulletArray[n][2];
		direction   = msv.bulletArray[n][3];
		if (i != n)  msv.setBulletArray(n, i, bulletX, bulletY, angle, direction);
		n = n - 1;
		msv.setBulletArray(n);
	}
	public void run(){
		super.run();
		try{
			Thread.sleep(1000);
		}catch(Exception e){e.printStackTrace();
		}
		for (n = 1;n<=2;n++)
		{
			newone();
			msv.setBulletArray(n,n,bulletX,bulletY,angle,direction);
		}
		n = n - 1;		
		while(true){
			if (!keeprun) return;
			newone();
			n = n + 1;
			msv.setBulletArray(n,n,bulletX,bulletY,angle,direction);
			count = 0;
			while (count<50 && keeprun)
			{
				count = count + 1;
				for (i=1;i<=n;i++)
				{
					bulletX 	= msv.bulletArray[i][0];
					bulletY 	= msv.bulletArray[i][1];
					angle   	= msv.bulletArray[i][2];
					direction   = msv.bulletArray[i][3];
					speed       = Constant.BULLETSPEED;
					if (msv.getBuff(ItemType.Cool)) speed = speed / 3;
					if (bulletX<0 || bulletX > Game1Activity.screenWidth || bulletY<0 || bulletY >Game1Activity.screenHeight)
					{
					   newone();	
					}
					if (angle > 0)
					{
						bulletX = (float)(bulletX + speed * Math.sin(angle)*direction);
						bulletY = (float)(bulletY + speed * Math.cos(angle)*direction);
					}
					else 
					{
						bulletX = (float)(bulletX - speed * Math.sin(angle)*direction);
						bulletY = (float)(bulletY - speed * Math.cos(angle)*direction);
					}
					msv.setBulletArray(n,i,bulletX, bulletY, angle ,direction);
				}
				while (pause) dopause();
				try{
						Thread.sleep(Constant.BULLETRUNINTERVAL);
						}catch(Exception e){e.printStackTrace();
				}
			}
		}
	}
	private void newone() {
		// TODO Auto-generated method stub
		
		double rand;
		int    randint;
	
		if(keeprun){
			
		
		rand = Math.random();
		randint = (int) Math.round(rand*100);
	    
		switch (randint%4){
			case 0: bulletX = 0; 								  bulletY = (float)rand * Game1Activity.screenHeight; break;
			case 1: bulletX = Game1Activity.screenWidth;    	 			  bulletY = (float)rand * Game1Activity.screenHeight; break;
			case 2: bulletX = (float)rand * Game1Activity.screenWidth; 	  bulletY = 0; 				    		   break;
			case 3: bulletX = (float)rand * Game1Activity.screenWidth;       bulletY = Game1Activity.screenHeight; 			   break;
		}		
		ballx = msv.ballX;
		bally = msv.ballY;
		angle = (float)Math.atan((bulletX - ballx)/(bulletY - bally));
		if (bulletX > ballx) direction = -1;
		else direction = 1;		
	}}
}

