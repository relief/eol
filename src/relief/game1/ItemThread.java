package relief.game1;

import java.net.Proxy.Type;

import relief.game1.Constant.ItemType;

public class ItemThread extends Thread{
	MySurfaceView msv;
	private Item item;
	private boolean keeprun,pause;
	int i,count;
	private static float fp =150f;
	private int n; 
	public float x,y,vx,vy,ay,r;
	
	public ItemThread(MySurfaceView msv){
		super();
		pause = false;
		keeprun = true;
		this.msv = msv;
		n = 0;
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
	public void run(){
		super.run();
		try{
			Thread.sleep(1000);
		}catch(Exception e){e.printStackTrace();
		}
		
		while(true){
			if (!keeprun) return;
		
			item = newItem();
			n = n + 1;
			msv.setItemArray(n, n, item);
			count = 0;
			while (count<fp*2/3 && keeprun)
			{
				for (i=1;i<=n;i++){
					item = msv.getItemArray(i);
					if (item.eaten) {  //如果被吃了
						item.vx = 0;
						item.vy = 0;
						item.ay = (float)Math.sqrt(2*Game1Activity.screenWidth/10);			
					}
					if (x>=0 && x <= Game1Activity.screenWidth && y>=0 && y <= Game1Activity.screenHeight)
					{	//如果在地图范围内
						item.vy = item.vy + item.ay;
						if (msv.getBuff(ItemType.Cool)){ 
							item.x  = item.x  + item.vx/3;
							item.y  = item.y  + item.vy*2/3;
						}
						else {
							item.x  = item.x  + item.vx;
							item.y  = item.y  + item.vy;
						}
						msv.setItemArray(n,i,item);
					}	
					else {	//如果不在地图范围内
							if (i != n)  msv.setItemArray(n,i,msv.getItemArray(n));
							n = n - 1;
							msv.setItemArray(n);
					}
				}
				while (pause) dopause();
				try{
					Thread.sleep(Constant.BULLETRUNINTERVAL);
					}catch(Exception e)	{
						e.printStackTrace();
				}		
				count++;
			}
			/*try{
				long rand;
				rand = Math.round(Math.random()*3000);
				Thread.sleep(rand);
				}catch(Exception e)	{
					e.printStackTrace();
			}		*/	
		}
	}
	private Item newItem() {
		// TODO Auto-generated method stub
		Item tmp = new Item();
		if(keeprun){
			tmp.x  = 0;
			tmp.y  = Game1Activity.screenHeight - 50;
			tmp.vx = Game1Activity.screenWidth / fp;
			tmp.ay = 0.08f;
			double db = (-1.10*Game1Activity.screenHeight-tmp.ay*fp*fp/8.0f)/0.5/fp;
			double ub = (-0.00*Game1Activity.screenHeight-tmp.ay*fp*fp/8.0f)/0.5/fp;
			
			double rand = Math.random();
			
			tmp.vy = (float)(rand*(ub-db)+db);
			
			//Type
			switch ((int)Math.round(rand*10)){
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:			
				tmp.type = ItemType.Gold;
				break;			
			case 6:
			case 7:	
				tmp.type = ItemType.Cool;
				break;			
			case 8:
			case 9:
				tmp.type = ItemType.Wudi;
				break;
			default:
				tmp.type = ItemType.Unknown;
				tmp.unknownRandom = 0;
			}
			return tmp;
		}
		return null;
	}
}

