package com.zn.jx;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import com.cgc.jme.lib.main.CommonListener;
import com.cgc.jme.lib.main.CommonMain;


public class JX_ITV_PROP extends Canvas implements CommonListener{
	
	public final static int RESULT_SUCCESS=0;
	public final static int RESULT_FAILE=1;
	public final static int RESULT_CANCEL=2;
	
	public final static int TEST_RETURN_SUCCESS=0;
	public final static int TEST_RETURN_FAILE=1;
	public final static int NORMAL=2;
	
	private int testType;
	
	
	private String[] productID={"99880665","99880668","99880666","99880667"};  //99880665  ,99880050
	private Image propName;
	private Image propInfo;
//	private String propID;
	private int price;
	private Image bg;
	private Image buyBg;
	private Image chargeBg;
	private Image[] num_charge;
	private Image[] num_buyProp;
	private Image yuanbao;
	private Image options;
	private Image chargeSelected;
	private Image buyPropSelected;
	private Image button;
	private Image yes;
	private Image no;
	private Image button_back;
	private Image buy_success;
	private Image buy_faile;
	private Image yu_e;
	private int selectIndex;
	private MIDlet midlet;
	
	private final static int BUY_PROP=0;
	private final static int CHARGE=1;
	private final static int BUY_SUCCESS=2;
	private final static int BUY_FAILE=3;
	
	private int state;
	
	private int Coins;
	
	private int[] buyPropPosition={195,375};
	private int[] chargePosition={206,244,282,320};
	
	private int[] yuan={1,2,5,10};
	
	
	private JX_Listener listener;
	private Displayable lastCanvas;
	private int bgX=150;
	private int bgY=120;
	private int maxIndex=4;
	private int propType;
	
	/**
	 * 
	 * @param midlet
	 * @param listener
	 * @param testType 0返回成功          1返回失败         3正常
	 */
	public JX_ITV_PROP(MIDlet midlet,JX_Listener listener,int testType){
		this.midlet=midlet;
		this.listener=listener;
//		this.productID=productID;
		this.testType=testType;
		selectIndex=Tooles.BUTTON_NO;
		state=BUY_PROP;
		setFullScreenMode(true);
		if(testType==NORMAL){
			CommonMain.initialize(midlet);
			CommonMain.setCommonListener(this);
			CommonMain.doGameCoinLoad();
		}
		System.out.println("-----------------------");
		System.out.println("|                   			   |");
		System.out.println("|   JX_Version_1.0.3   |");
		System.out.println("|                   			   |");
		System.out.println("-----------------------");
	}
	private void initImage(){
		bg=Tooles.loadImage("/buyProp/bg.png");
		buyBg=Tooles.loadImage("/buyProp/buyBg.png");
		buy_success=Tooles.loadImage("/buyProp/buy_success.png");
		buy_faile=Tooles.loadImage("/buyProp/buy_faile.png");
		num_buyProp=new Image[10];
		Image once=Tooles.loadImage("/buyProp/num.png");
		for(int i=0;i<num_buyProp.length;i++){
			num_buyProp[i]=Image.createImage(once,11*i,0,11,17,0);
		}
		once=null;
		yu_e=Tooles.loadImage("/buyProp/yu_e.png");
		yuanbao=Tooles.loadImage("/buyProp/yuanbao.png");
		button=Tooles.loadImage("/buyProp/button.png");
		yes=Tooles.loadImage("/buyProp/yes.png");
		no=Tooles.loadImage("/buyProp/no.png");
		buyPropSelected=Tooles.loadImage("/buyProp/buyPropSelected.png");
		chargeBg=Tooles.loadImage("/charge/chargeBg.png");
		button_back=Tooles.loadImage("/charge/button_back.png");
		num_charge=new Image[10];
		once=Tooles.loadImage("/charge/num.png");
		for(int i=0;i<num_charge.length;i++){
			num_charge[i]=Image.createImage(once,12*i,0,12,18,0);
		}
		once=null;
		options=Tooles.loadImage("/charge/options.png");
		chargeSelected=Tooles.loadImage("/charge/chargeSelected.png");
	}
	private void removeImage(){
		bg=null;
		buyBg=null;
		buy_success=null;
		buy_faile=null;
//		for(int i=0;i<num_buyProp.length;i++){
//			num_buyProp[i]=null;
//		}
//		num_buyProp=null;
		yu_e=null;
		yuanbao=null;
		button=null;
		yes=null;
		no=null;
		buyPropSelected=null;
		chargeBg=null;
		button_back=null;
//		for(int i=0;i<num_charge.length;i++){
//			num_charge[i]=null;
//		}
//		num_charge=null;
		options=null;
		chargeSelected=null;
	}
	

	public void paint(Graphics g) {
		if(state==BUY_PROP){
			showBuyProp(g);
		}else if(state==CHARGE){
			showCharge(g);
		}else if(state==BUY_SUCCESS){
			showBuySuccess(g);
		}else if(state==BUY_FAILE){
			showBuyFaile(g);
		}
		
	}
	private void showBuySuccess(Graphics g){
		g.drawImage(bg, bgX, bgY, 0);
		g.drawImage(buy_success, bgX+120,bgY+120, 0);
		g.drawImage(yu_e, bgX+80, bgY+180, 0);
		Tooles.DrawNumber(num_charge, Coins, 360, bgY+180, 10, g);
		g.drawImage(yuanbao, 380, bgY+180, 0);
	}
	private void showBuyFaile(Graphics g){
		g.drawImage(bg, bgX, bgY, 0);
		g.drawImage(buy_faile, bgX+120,bgY+120, 0);
	}
	private void showBuyProp(Graphics g){
		g.drawImage(bg, bgX, bgY, 0);
		g.drawImage(buyBg, bgX+15, bgY+50, 0);
		g.drawImage(propName, 260, 170, 0);
		Tooles.DrawNumber(num_buyProp, price, 260, 205, 8, g);
		g.drawImage(yuanbao, 290, 203, 0);
		g.drawImage(propInfo, 246, 240, 0);
		g.drawImage(button, 200, 320, 0);
		g.drawImage(yes, 210, 325, 0);
		g.drawImage(button, 380, 320, 0);
		g.drawImage(no, 390, 325, 0);
		g.drawImage(buyPropSelected, buyPropPosition[selectIndex], 316, 0);
		
		g.drawImage(yu_e, 175, 375, 0);
		Tooles.DrawNumber(num_charge, Coins, 360, 378, 10, g);
		g.drawImage(yuanbao, 380, 375, 0);
	}
	private void showCharge(Graphics g){
		g.drawImage(chargeBg, bgX, bgY, 0);
		for(int i=0;i<4;i++){
			g.drawImage(options, 190, 210+38*i, 0);
		}
		g.drawImage(button_back, 360, 265, 0);
		if(selectIndex!=maxIndex){
			g.drawImage(chargeSelected, 185, chargePosition[selectIndex], 0);
		}else{
			g.drawImage(chargeSelected, 355, 262, 0);
		}
		for(int i=0;i<4;i++){
			Tooles.DrawNumber(num_charge, yuan[i], 215, 220+38*i, 10, g);
			Tooles.DrawNumber(num_charge, yuan[i]*10, 280, 220+38*i, 10, g);
		}
		Tooles.DrawNumber(num_charge, Coins, 360, 378, 10, g);
		g.drawImage(yuanbao, 380, 375, 0);
		
	}
	
	public void do_buyProp(Image propName,Image propInfo,String propID,int price,int propType){
		
		this.propName=propName;
		this.propInfo=propInfo;
		GameData.proID=propID;
		this.price=price;
		this.propType=propType;
		initImage();
		if(lastCanvas==null){
		lastCanvas=Display.getDisplay(midlet).getCurrent();}
		Display.getDisplay(midlet).setCurrent(this);
	}
	public void setID(String userID,String kindID){
		GameData.userId=userID;
		GameData.kindID=kindID;
//		GameData.proID=proID;
	}
	public void keyPressed(int keyCode){
		switch(state){
		case BUY_PROP:
			buyPropKeyPressed(keyCode);
			break;
		case CHARGE:
			chargeKeyPressed(keyCode);
			break;
		}
		repaint();
	}
	private void buyPropKeyPressed(int keyCode){
		switch(keyCode){
		case Tooles.KEY_LEFT:
			if(selectIndex!=Tooles.BUTTON_YES){
				selectIndex=Tooles.BUTTON_YES;
			}
			break;
		case Tooles.KEY_RIGHT:
			if(selectIndex!=Tooles.BUTTON_NO){
				selectIndex=Tooles.BUTTON_NO;
			}
			break;
		case Tooles.KEY_FIRE:
			if(selectIndex==Tooles.BUTTON_YES){
				switch(testType){
				case TEST_RETURN_SUCCESS:
					state=BUY_SUCCESS;
					autoClose(1500);
					break;
				case TEST_RETURN_FAILE:
					state=BUY_FAILE;
					autoClose(1500);
					break;
				case NORMAL:
					if(Coins>=price){
						CommonMain.doGameCoinSave(Coins-price);
					}else{
						state=CHARGE;
						selectIndex=0;
					}
					break;
				}
			}else{
				backToGame(RESULT_CANCEL);
			}
			
			break;
		}
	}
	private void chargeKeyPressed(int keyCode){
		switch(keyCode){
		case Tooles.KEY_LEFT:
			if(selectIndex==maxIndex){
				selectIndex=0;
			}
			break;
		case Tooles.KEY_RIGHT:
			if(selectIndex!=maxIndex){
				selectIndex=maxIndex;
			}
			break;
		case Tooles.KEY_UP:
			if(selectIndex!=maxIndex)
				selectIndex=--selectIndex<0?0:selectIndex;
			break;
		case Tooles.KEY_DOWN:
			if(selectIndex!=maxIndex)
				selectIndex=++selectIndex>3?3:selectIndex;
			break;
		case Tooles.KEY_FIRE:
			if(selectIndex!=maxIndex){
				CommonMain.doPayment(productID[selectIndex], CommonMain.PAYMENT_TYPE_BILL);
			}else{
				backToGame(RESULT_CANCEL);
			}
			
			break;
		}
	}
	public void setButton_YesOrNo(int type){
		if(type==Tooles.BUTTON_YES){
			selectIndex=Tooles.BUTTON_YES;
		}else{
			selectIndex=Tooles.BUTTON_NO;
		}
	}
	public void onCommonResult(int connect_id,String connect_key,int result_state,
			int result_type,Object content) {
//		
		switch(connect_id)
		{
			case CommonMain.CONNECT_PAYMENT:
				switch(result_state){
				case CommonMain.RESULT_SUCCESS:
					CommonMain.doGameCoinLoad();
					new ServerIptv().doSendChargeCoins(yuan[selectIndex]*10, 0);
					state=BUY_PROP;
					break;
				case CommonMain.RESULT_FAILED:
					new ServerIptv().doSendChargeCoins(yuan[selectIndex]*10, 1);
					state=BUY_PROP;
					break;
				case CommonMain.RESULT_CANCELED:
					
					break;
				}
				break;
			case CommonMain.CONNECT_MARK_SAVE:
				break;
			case CommonMain.CONNECT_DATA_SAVE:
				if(result_state==CommonMain.RESULT_SUCCESS)
				{
					
				}
				break;
			case CommonMain.CONNECT_GAME_COIN_SAVE:
				switch(result_state){
				case CommonMain.RESULT_SUCCESS:
					state=BUY_SUCCESS;
					Coins=Coins-price;
					new ServerIptv().doSendBuyPropCoins(price);
					autoClose(1500);
					break;
				case CommonMain.RESULT_FAILED:
					state=BUY_FAILE;
					autoClose(1500);
					break;
				}
				break;
			case CommonMain.CONNECT_GAME_COIN_LOAD:
				if(result_state==CommonMain.RESULT_SUCCESS)
				{
					Coins = CommonMain.getCoins();
					System.out.println("Coins="+Coins);
				}
				break;
			case CommonMain.CONNECT_GAME_BEGIN:
				break;
			case CommonMain.CONNECT_GAME_END:
				break;
			case CommonMain.CONNECT_FAVORITE:
				break;
			case CommonMain.CONNECT_GAME_TOOL:
				break;
			case CommonMain.CONNECT_DATE_GET:
				if(result_state==CommonMain.RESULT_SUCCESS){
					System.out.println("成功获取日期为：" + CommonMain.getCurrentDate());
				}
				else{
					System.out.println("获取日期失败:" + result_state);
				}
				
				break;
			case 100:
				if(content!=null)
				{
//					byte[] bytes = (byte[])content;
				}
				break;
		}
		selectIndex=1;
		repaint();
	}
	private void backToGame(int type){
		reset();
		listener.ResultState(type,propType);
		Display.getDisplay(midlet).setCurrent(lastCanvas);
		
	}
	private void autoClose(int tm){
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				if(state==BUY_SUCCESS){
					backToGame(RESULT_SUCCESS);
				}else if(state==BUY_FAILE){
					backToGame(RESULT_FAILE);
				}
//				reset();
			}
		}, tm);
	}
	public void reset(){
		state=BUY_PROP;
		selectIndex=Tooles.BUTTON_NO;
		removeImage();
	}


}
