package relief.game1;

import relief.game1.Constant.ItemType;

public class Item {
	public float x,y,vx,vy,ay,r;
	public ItemType type;
	public boolean eaten;
	public int unknownRandom;  //0-8

	public Item(float x, float y, float vx, float vy, float ay, float r,ItemType type,int unknownRandom) {
		super();
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.ay = ay;
		this.r = r;
		this.type = type;
		this.unknownRandom = unknownRandom;
	}
	public Item(){
		super();
		this.x = 0f;
		this.y = 0f;
		this.vx = 0f;
		this.vy = 0f;
		this.ay = 0f;
		this.r = 0f;
		this.type = ItemType.Unknown;
		this.unknownRandom = 0;
	}
}
