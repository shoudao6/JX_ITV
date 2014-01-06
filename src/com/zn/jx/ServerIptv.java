package com.zn.jx;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class ServerIptv extends Thread{
	
	private final String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";
	private final String COMPANYURL = "http://117.41.240.39/www.iptvtest.com/";//"http://202.99.114.17:8080/www.iptvgame.com/index.php";   //天津

	private final static int SEND_BUY_PROP_COINS=0;
	private final static int SEND_CHARGE_COINS=1;
	private String encryptedstr = null;
	private String lastResult = null;
	private md5 md5class = null;
//	private Base64 base64class = null;
	private String submit_url=null;
	private int type;
	private int rc;
	
	public ServerIptv() {
		md5class = new md5();
//		base64class = new Base64();

	}
	public void doSendBuyPropCoins( int coins){
		type=SEND_BUY_PROP_COINS;
		submit_url="userid="+GameData.userId+"&kindid="+GameData.kindID+"&proid="+GameData.proID+"&gold="+coins;
		encryptedstr = getUrl("iptv.consumer.php", submit_url);
		System.out.println(encryptedstr);
		new Thread(this).start();
		
	}
	public void doSendChargeCoins(int coins,int result){
		type=SEND_CHARGE_COINS;
		submit_url="userid="+GameData.userId+"&kindid="+GameData.kindID+"&proid="+GameData.proID+"&gold="+coins+"&result="+result;
		encryptedstr = getUrl("gameorder.php", submit_url);
		System.out.println(encryptedstr);
		new Thread(this).start();
	}
	
	public void run(){
		try {
			lastResult = new String(getViaHttpConnection(encryptedstr));
			switch(type){
			
			case SEND_CHARGE_COINS:
				if(lastResult.equals("0")){
					System.out.println("充值记录发送成功！！！");
				}else{
					System.out.println("result="+lastResult);
					System.out.println("充值记录发送失败！！！");
				}
				break;
			case SEND_BUY_PROP_COINS:
				if(lastResult.equals("0")){
					System.out.println("消费记录发送成功！！！");
				}else{
					System.out.println("result="+lastResult);
					System.out.println("消费记录发送失败！！！");
				}
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 经过处理后得到最终的url
	 */
	// ：?m=iptv.game.sudoku.add&input=
	public String getUrl(String action, String param) {
		// System.out.println("param="+param);
		String input = encrypt(param);
		// System.out.println("input="+input);
		input = md5class.getMD5ofStr((input + APP_KEY)) + input;
		// System.out.println("input="+input);
		String url = COMPANYURL + action
				+ "?input=" + input;
		return url;
	}
	/**
	 * 加密方法，并返回一个字符串值 被调用的方法
	 * 
	 * @param param
	 * @return
	 */
	private String encrypt(String param) {
		byte[] mkey = md5class.getMD5ofStr(APP_KEY).substring(1, 19).getBytes();

		int mlen = mkey.length;
		// 取APP_KEY 32位 MD5值中 的 1 -19位
		for (int i = 0; i < mlen; i++) {

			// System.out.println((char)mkey[i]);

		}

		//
		int mk;
		int num = param.length();// iptv=157510892888& 取文本长度 参数长度
		// System.out.println("num="+num);
		byte[] output = new byte[num];// 重新构建数组
		byte[] strbyte = param.getBytes();// ASCII码 十进制 转换
		for (int i = 0; i < num; i++) {
			mk = i % mlen;
			output[i] = (byte) ((int) strbyte[i] ^ (int) mkey[mk]);

		}

		// System.out.println("base64="+base64class.encode(new String(output)));
		// System.out.println(new String(output));
		return Base64.encode(output);
	}
	/**
	 * 连接网络，下载数据
	 */
	public byte[] getViaHttpConnection(String url) throws IOException {
		HttpConnection c = null;
		InputStream is = null;
		byte[] data = null;

		try {
			c = (HttpConnection) Connector.open(url);
			rc = c.getResponseCode();
			System.out.println("从服务器返回的值是：" + rc);
			if (rc != HttpConnection.HTTP_OK) {
				throw new IOException("HTTP response code: " + rc);
			}

			is = c.openInputStream();
//			String type = c.getType();
			int len = (int) c.getLength();
			if (len > 0) {
				int actual = 0;
				int bytesread = 0;
				data = new byte[len];
				while ((bytesread != len) && (actual != -1)) {
					actual = is.read(data, bytesread, len - bytesread);
					bytesread += actual;
				}
			} else {
//				int ch;
//				while ((ch = is.read()) != -1) {
//
//				}
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Not an HTTP URL");
		} finally {
			if (is != null)
				is.close();
			is = null;
			if (c != null)
				c.close();
			c = null;
			System.gc();
		}
		return data;
	}
}
