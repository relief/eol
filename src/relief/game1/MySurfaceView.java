package relief.game1;


import relief.game1.Constant.ItemType;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MySurfaceView extends SurfaceView
implements SurfaceHolder.Callback{
    
	private static final float MIN_DIS = 700;
	
	Game1Activity ga;
	Paint paint,RecordP,CurrentP,halfCoolP,CoolP,halfWudiP;
	ItemThread   ilt;
	OnDrawThread odt;
	BallRunThread ballrt;
	BulletRunThread bulletrt;
	TimeCountThread timect;
	
	public float ballX = Game1Activity.screenWidth/2, ballY = Game1Activity.screenHeight/2;
	public float bulletX=100	,bulletY = 100;
	public float aim_x = -1f,aim_y = -1f;
	public float angle;
	public float[][] bulletArray = new float[500][4]; 
	public Item[] itemArray = new Item[100]; 
	public float[] gold = new float[5];
	public boolean isCoolBuff,isWudi,isPause;
	private int CoolCount,WudiCount;
	private int bulletnum = 0,itemnum = 0;
	private int unknownRandom;
	private int goldnum,coolnum,wudinum;
	
	private float[] pos ={Game1Activity.screenWidth-140,40,Game1Activity.screenWidth-120,40,Game1Activity.screenWidth-100,40,Game1Activity.screenWidth-80,40,Game1Activity.screenWidth-60,40,Game1Activity.screenWidth-40,40};
							//	   1      1      1      :      1      1
	private float[] posrecordstring = {Game1Activity.screenWidth - 165,70,Game1Activity.screenWidth-145,70,Game1Activity.screenWidth - 125,70,Game1Activity.screenWidth-105,70};
					// Record       
	private float[] posrecord       = {Game1Activity.screenWidth - 80,70,Game1Activity.screenWidth - 68,70,Game1Activity.screenWidth - 56,70,Game1Activity.screenWidth - 44,70,Game1Activity.screenWidth - 32,70,Game1Activity.screenWidth - 20,70};
//						1      0        0      :       1       1
	String record;
	
	Bitmap ball,buls,bulo,bult,bulth,BG,boom,boomo,boomt,boomth,boomf,goldPic,goldEatenPic,paotai,wudiPic
	,wudiEatenPic,otherPic,otherEatenPic,coolPic,coolEatenPic,coolBg,wudiBall,pausePic,resumePic;
	int ballwidth,ballheight,bulletwidth,bulletheight;
	int picAlphaNum = 255;
	int explodeCount = 0;
	char time[] = {'0','0','0',':','0','0'};

	public boolean endgame = false;
	
	public MySurfaceView(Context context){
		super(context);
		this.ga= (Game1Activity) context;
		this.getHolder().addCallback(this);
		unknownRandom = 0;
		isCoolBuff = false;
		isWudi     = false;
		isPause    = false;
		record = ga.getrecord();
		goldnum = 0; coolnum = 0; wudinum = 0;
		
		initialPaint();		
		initialBitmap();
				
		
		System.out.println(11);
		
		ballwidth    = ball.getWidth();
		ballheight   = ball.getHeight();
		bulletwidth  = buls.getWidth();
		bulletheight = buls.getHeight();
		System.out.println(12);
		
		ballrt = new BallRunThread(this);
		ballrt.restart();
		ballrt.start();
		
		odt      = new OnDrawThread(this);
		bulletrt = new BulletRunThread(this);
		timect   = new TimeCountThread(this);
		ilt		 = new ItemThread(this);
		System.out.println(13);
		
		ilt.start();
		bulletrt.start();
		System.out.println(14);
	}
	private void initialBitmap() {
		// TODO Auto-generated method stub
		resumePic = BitmapFactory.decodeResource(ga.getResources(), R.drawable.resume);
		pausePic  = BitmapFactory.decodeResource(ga.getResources(), R.drawable.pause);
		wudiBall  = BitmapFactory.decodeResource(ga.getResources(), R.drawable.wudiball);
		coolBg    = BitmapFactory.decodeResource(ga.getResources(), R.drawable.coolbg);
		wudiPic   = BitmapFactory.decodeResource(ga.getResources(), R.drawable.wudi);
		wudiEatenPic   = BitmapFactory.decodeResource(ga.getResources(), R.drawable.wudieaten);
		coolPic   = BitmapFactory.decodeResource(ga.getResources(), R.drawable.cool);
		coolEatenPic   = BitmapFactory.decodeResource(ga.getResources(), R.drawable.cooleaten);
		paotai = BitmapFactory.decodeResource(ga.getResources(), R.drawable.paotai);
		otherPic = BitmapFactory.decodeResource(ga.getResources(), R.drawable.other);
		otherEatenPic = BitmapFactory.decodeResource(ga.getResources(), R.drawable.othereaten);
		goldPic = BitmapFactory.decodeResource(ga.getResources(), R.drawable.gold);
		goldEatenPic = BitmapFactory.decodeResource(ga.getResources(), R.drawable.goldeaten);
		ball = BitmapFactory.decodeResource(ga.getResources(), R.drawable.balls);
		buls = BitmapFactory.decodeResource(ga.getResources(), R.drawable.buls);
		bulo = BitmapFactory.decodeResource(ga.getResources(), R.drawable.bulo);
		bult = BitmapFactory.decodeResource(ga.getResources(), R.drawable.bult);
		bulth= BitmapFactory.decodeResource(ga.getResources(), R.drawable.bulth);
		BG   = BitmapFactory.decodeResource(ga.getResources(), R.drawable.background);
		boom = BitmapFactory.decodeResource(ga.getResources(), R.drawable.boom);
		boomo = BitmapFactory.decodeResource(ga.getResources(), R.drawable.boomo);
		boomt = BitmapFactory.decodeResource(ga.getResources(), R.drawable.boomt);
		boomth = BitmapFactory.decodeResource(ga.getResources(), R.drawable.boomth);
		boomf = BitmapFactory.decodeResource(ga.getResources(), R.drawable.boomf);
	}
	private void initialPaint() {
		// TODO Auto-generated method stub
		paint  = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(30);

		RecordP = new Paint();
		RecordP.setColor(Color.rgb(0xFF,0x00,0x00));
		RecordP.setTextSize(20);

		CurrentP = new Paint();
		CurrentP.setColor(Color.rgb(0x8A, 0x2B, 0xE2));
		CurrentP.setTextSize(40);
		
		CoolP = new Paint();
		CoolP.setAlpha(180);
		
		halfCoolP = new Paint();
		halfCoolP.setAlpha(120);
		
		halfWudiP = new Paint();
		halfWudiP.setAlpha(120);
	}
	public void setBall(float ballX,float ballY){
		this.ballX = ballX;
		this.ballY = ballY;
	}
	public void setPicAlphaNum(int picAlphaNum){
		this.picAlphaNum = picAlphaNum;
	}
	public void setTime(int n4,int n3,int n2,int n1,int n0){
	   time[0] = (char)(n4 + 48);
	   time[1] = (char)(n3 + 48);
	   time[2] = (char)(n2 + 48);
	   time[4] = (char)(n1 + 48);
	   time[5] = (char)(n0 + 48);
	}
	public void setBulletArray(int n,int i,float x, float y, float angle , float direction) {
		// TODO Auto-generated method stub
		this.bulletnum = n;
		this.bulletArray[i][0] = x;
		this.bulletArray[i][1] = y;
		this.bulletArray[i][2] = angle;
		this.bulletArray[i][3] = direction;
	}
	public void setBulletArray(int n) {
		// TODO Auto-generated method stub
		this.bulletnum = n;
	}
	public void setItemArray(int n,int i,float x, float y, float vx, float vy,float ay,ItemType type) {
		this.itemnum = n ;
		this.itemArray[i].x = x;
		this.itemArray[i].y = y;
		this.itemArray[i].vx = vx;
		this.itemArray[i].vy = vy;
		this.itemArray[i].ay = ay;
		this.itemArray[i].type = type;		
	}
	public void setItemArray(int n,int i,Item item) {
		this.itemnum = n ;
		this.itemArray[i] = item;
	}
	public void setItemArray(int n){
		this.itemnum = n;
	}
	public void setItemArray(int i,int unknownRandom){
		this.itemArray[i].unknownRandom = unknownRandom;
	}
	public Item getItemArray(int i){
		return this.itemArray[i];
	}
	public boolean getBuff(ItemType type){
    	switch(type){
			case Cool:return isCoolBuff;
			case Wudi:return isWudi;
    	}
    	return false;
    }
    public void setBuff(ItemType type,boolean b){
    	switch(type){
			case Cool:isCoolBuff = b; return ;
			case Wudi:isWudi = b; 	  return ;
    	}
    	return;
    }
    public int getCount(ItemType type){
		switch(type){
			case Cool:return CoolCount;
			case Wudi:return WudiCount;
		}
    	return 0;    	
    }
    public void setCount(ItemType type,int count){

		switch(type){
			case Cool: CoolCount = count; return;
			case Wudi: WudiCount = count; return;
		}
    	return ;    	
    }
	protected void onDraw(Canvas canvas){	
		int i;		
		super.onDraw(canvas);		
		
		//画背景
		canvas.drawBitmap(BG, 0, 0, paint);
		//画冰霜效果
		if (isCoolBuff) drawCoolBuff(canvas);
		//画小球
		drawBall(canvas);
		
		//画道具		
		for (i=1;i<=itemnum;i++){
			drawitem(canvas,i);
		}		
		
		//画子弹
		for (i=1;i<=bulletnum;i++)
		{
			drawbullet(canvas,i);
		}
		//画炮台
		//canvas.drawBitmap(paotai, Game1Activity.screenHeight-40, 0, paint);
		//画当前时间和最好纪录
		if (time[0] == '0') canvas.drawPosText(time, 1, 5, pos, CurrentP);
		else canvas.drawPosText(time, 0, 6, pos, CurrentP);
		canvas.drawPosText("最好成绩", posrecordstring, RecordP);		
		canvas.drawPosText(record, posrecord, RecordP);		
		//画暂停继续按钮
		if (isPause)
			canvas.drawBitmap(resumePic, Game1Activity.screenWidth-65, Game1Activity.screenHeight - 65, paint);	
		else 
			canvas.drawBitmap(pausePic, Game1Activity.screenWidth-65, Game1Activity.screenHeight - 65, paint);
}
    private void drawBall(Canvas canvas) {
		// TODO Auto-generated method stub
    	int count;
    	if (isWudi)
    	{
    		count = getCount(ItemType.Wudi);
    		if (count > 400 && (count % 100)<50) 
    			canvas.drawBitmap(wudiBall, ballX-70/2, ballY - 70/2, halfWudiP);  //画一个小的ball
    		else 
    			canvas.drawBitmap(wudiBall, ballX-70/2, ballY - 70/2, paint);  //画一个小的ball
    	}else 	canvas.drawBitmap(ball, ballX-ballwidth/2, ballY - ballheight/2, paint);  //画一个小的ball		
	}
    private void drawCoolBuff(Canvas canvas) {
    	int count = getCount(ItemType.Cool);
    	if (count > 400 && (count % 100)<50) 
    		canvas.drawBitmap(coolBg, 0, 0, halfCoolP);
    	else
    		canvas.drawBitmap(coolBg, 0, 0, CoolP);
	}
	
   
	private void drawbullet(Canvas canvas, int i) {
		// TODO Auto-generated method stub
		float direction;
		float tmp;
		bulletX  = bulletArray[i][0];
		bulletY  = bulletArray[i][1];
		angle    = bulletArray[i][2];
		direction= bulletArray[i][3]; 
		tmp = (float) (angle / Math.PI * 180);
		
		if (tmp * direction >0 && tmp * direction <= 45) 
			canvas.drawBitmap(bulth, bulletX-bulletwidth/2, bulletY - bulletheight/2, paint); 
		if (tmp * direction <0 && tmp * direction >=-45)   
			canvas.drawBitmap(bulo, bulletX-bulletwidth/2, bulletY - bulletheight/2, paint);
		if (direction > 0 && (tmp > 45 || tmp < -45))
			canvas.drawBitmap(bult,bulletX-bulletwidth/2, bulletY - bulletheight/2, paint);
		if (direction < 0 && (tmp > 45 || tmp < -45))   
			canvas.drawBitmap(buls, bulletX-bulletwidth/2, bulletY - bulletheight/2, paint);				
				
		if ((bulletX-ballX)*(bulletX-ballX)+(bulletY-ballY)*(bulletY-ballY) < MIN_DIS ) 
		{
			if (isWudi){
				bulletrt.deleteBullet(i);
			}else {
				gameover();			
				explode(canvas);
			}
		}
	}
	private void drawitem(Canvas canvas, int i) {
		// TODO Auto-generated method stub
		Item item = this.getItemArray(i);
		if (item.eaten){
			switch (item.type){
			case Gold:
				canvas.drawBitmap(goldEatenPic, item.x,item.y, paint);
				float[] posaddSecond = {item.x+30,item.y+40,item.x+55,item.y+40};
				canvas.drawPosText("+3", posaddSecond, CurrentP);	
				break;
			case Wudi:
				canvas.drawBitmap(wudiEatenPic, item.x,item.y, paint); 
				break;
			case Cool:
				canvas.drawBitmap(coolEatenPic, item.x,item.y, paint);
				float[] posCold = {item.x+30,item.y+40,item.x+55,item.y+40,item.x+80,item.y+40
						,item.x+105,item.y+40,item.x+130,item.y+40,item.x+155,item.y+40};
				canvas.drawPosText("Freeze", posCold, CurrentP);
				break;
			case Unknown:
				{
					if (item.unknownRandom<3)  //0-2
						{canvas.drawBitmap(goldEatenPic, item.x,item.y, paint); break;}
					if (item.unknownRandom<6) //3-5
						{canvas.drawBitmap(wudiEatenPic, item.x,item.y, paint); break;}
					if (item.unknownRandom<9) //6-8
						{canvas.drawBitmap(coolEatenPic, item.x,item.y, paint); break;}
				}
			}
		}
		else 
		{
			switch (item.type){
			case Gold:
				canvas.drawBitmap(goldPic, item.x,item.y, paint); break;
			case Wudi:
				canvas.drawBitmap(wudiPic, item.x,item.y, paint); break;
			case Cool:
				canvas.drawBitmap(coolPic, item.x,item.y, paint); break;
			case Unknown:
				{
					unknownRandom = item.unknownRandom;
					if (unknownRandom == 8) unknownRandom = 0;
					else unknownRandom = unknownRandom + 1;
					this.setItemArray(i, unknownRandom);
					if (unknownRandom<3)  //0-2
						{canvas.drawBitmap(goldPic, item.x,item.y, paint); break;}
					if (unknownRandom<6) //3-5
						{canvas.drawBitmap(wudiPic, item.x,item.y, paint); break;}
					if (unknownRandom<9) //6-8
						{canvas.drawBitmap(coolPic, item.x,item.y, paint); break;}
					canvas.drawBitmap(otherPic,item.x,item.y, paint); break;
				}
			}
			float x = item.x + goldPic.getHeight()/2;
			float y = item.y + goldPic.getHeight()/2;
			if ((x-ballX)*(x-ballX)+(y-ballY)*(y-ballY) < MIN_DIS)
			{
				item.eaten = true;
				ItemEatenEvent(item.type,item.unknownRandom);
			}
		}
	}
	private void ItemEatenEvent(ItemType type,int unknownRandom) {
		// TODO Auto-generated method stub
		if (type == ItemType.Unknown){
			if (unknownRandom<9) //6-8
				type = ItemType.Cool;
			if (unknownRandom<6) //3-5
				type = ItemType.Wudi;
			if (unknownRandom<3)  //0-2
				type = ItemType.Gold;
		}
		switch(type){
			case Gold:
				timect.goldeaten();
				goldnum++;
				break;
			case Wudi:
				isWudi = true;
				WudiCount = 0;
				wudinum++;
				break;
			case Cool:
				isCoolBuff = true;
				CoolCount = 0;
				coolnum++;
				break;
			case Unknown:
				break;
		}
	}
	private void explode(Canvas canvas) {
		// TODO Auto-generated method stub
		
		int width; 
		int height;
		explodeCount = explodeCount + 1;
		
	    if (explodeCount<1){
	    		width = boom.getWidth();
		        height= boom.getHeight();
				canvas.drawBitmap(boom,  ballX-width/2, ballY - height/2, paint);
	    }
	    else 
	    if (explodeCount<2){
	    		System.out.println(1);
				width = boomo.getWidth();
				height= boomo.getHeight();
				canvas.drawBitmap(boomo,  ballX-width/2, ballY - height/2, paint);
	    }
	    else
	    if (explodeCount<3){
	    		System.out.println(2);
				width = boomt.getWidth();
				height= boomt.getHeight();
				canvas.drawBitmap(boomt,  ballX-width/2, ballY - height/2, paint);
	    }else if(explodeCount<4){
	    	 	System.out.println(3);
			 	width = boomth.getWidth();
			 	height= boomth.getHeight();
			 	canvas.drawBitmap(boomth, ballX-width/2, ballY - height/2, paint);
	    }else if(explodeCount<5){
				System.out.println(4);
				width = boomf.getWidth();
				height= boomf.getHeight();
				canvas.drawBitmap(boomf,  ballX-width/2, ballY - height/2, paint);
				System.out.println(5);
		}else   {
			width = boomf.getWidth();
			height= boomf.getHeight();
			canvas.drawBitmap(boomf,  ballX-width/2, ballY - height/2, paint);
			odt.stopit();
			ga.Evaluation(goldnum,coolnum,wudinum,time[0],time[1],time[2],time[4],time[5]);
		}
	}
	private void gameover() {
		// TODO Auto-generated method stub
		
		timect.stopit();
		ballrt.stopit();
		bulletrt.stopit();
		ilt.stopit();
		
		ga.vibrate();
		ga.freshrecord(time[0],time[1],time[2],time[4],time[5]);
		record = ga.getrecord();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		// TODO Auto-generated method stub
        int x = (int)event.getX();
        int y = (int)event.getY();        
        if (x > Game1Activity.screenWidth - 70 && y > Game1Activity.screenHeight - 70)
        {
        	if (isPause) resumemsv();
        	else pausemsv();        	
        }
        else{
        	 aim_x = x;
             aim_y = y;
        }
        return super.onTouchEvent(event);
	}
	public void pausemsv(){
		isPause = true;
		timect.pauseit();
		ballrt.pauseit();
		bulletrt.pauseit();
		ilt.pauseit();
	}
	private void resumemsv() {
		// TODO Auto-generated method stub
		timect.resumeit();
		ballrt.resumeit();
		bulletrt.resumeit();
		ilt.resumeit();
		isPause = false;
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		odt.start();
		timect.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void restart() {
		// TODO Auto-generated method stub
		explodeCount = 0;
		bulletnum    = 0;
		itemnum		 = 0;
		isCoolBuff   = false;
		isWudi 		 = false;
		isPause		 = false;
		goldnum  = 0;  coolnum = 0; wudinum = 0;
		
		odt    = new OnDrawThread(this);
		ballrt = new BallRunThread(this);
		bulletrt = new BulletRunThread(this);
		timect  = new TimeCountThread(this);
		ilt     = new ItemThread(this);
		
		ballrt.restart();
		bulletrt.restart();
		odt.restart();
		timect.restart();
		ilt.restart();
		
		ballrt.start();
		bulletrt.start();
		odt.start();
		timect.start();	
		ilt.start();
	}
	public void stopAllThread() {
		// TODO Auto-generated method stub
		odt.stopit();
		timect.stopit();
		ballrt.stopit();
		bulletrt.stopit();
		ilt.stopit();
	}	
}
