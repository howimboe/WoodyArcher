package com.thewhitecoconutstudio.archeryproject.tools;

public class GameValues {

	private static final GameValues INSTANCE = new GameValues();

	private float width = 0;
	private float height = 0;
	private float timeout = 60;
	
	public static GameValues getInstance()
	{
		return INSTANCE;
	}
	
	public float getTimeOut(){
		return timeout;
	}
	public void setWidth(float width){
		this.width = width;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public float getWidth(){
		return this.width;
	}
	
	public float getHeight(){
		return this.height;
	}
	
	public float getCenterX(){
		return this.width / 2;
	}
	
	public float getControllerX(){
		return this.getCenterX()+150;
	}
	
	public float getControllerY(){
		return 350;
	}
	
	public float getCenterY(){
		return this.height / 2;
	}
	
	public float getStartArrowX(){
		return this.getCenterX();
	}
	
	public float getStartArrowY(){
		return 100;
	}
}
