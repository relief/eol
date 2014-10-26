package relief.game1;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class OnDrawThread extends Thread{
   MySurfaceView msv;
   SurfaceHolder sh;
   private boolean keeprun = true;
   public OnDrawThread(MySurfaceView msv)
   {
	   super();
	   this.msv = msv;
	   sh = msv.getHolder();	   
   }
   public void stopit(){
		this.keeprun = false;
   }
   public void restart(){
		this.keeprun = true;
  }
   public void run(){
	   super.run();
	   Canvas canvas = null;
	   while(keeprun){
		   if (!keeprun) return;
		   try{
			   canvas = sh.lockCanvas(null);
			   synchronized(this.sh){
				   if (canvas != null){
					   msv.onDraw(canvas);					 
				   } }   
		   }
		   finally{
			   try{
				   if(sh != null) {
					   sh.unlockCanvasAndPost(canvas);					  
				   }}				 
			   catch(Exception e){e.printStackTrace();
			   }}
		   try{
			   Thread.sleep(Constant.ONDRAWSPEED);
			   		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
	   }
   }
}
