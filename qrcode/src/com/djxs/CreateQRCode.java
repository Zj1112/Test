package com.djxs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode {
static void createqrcode(int version,String content,String imgpath,String startRgb,String endRgb) throws IOException{
	Qrcode qrcode = new Qrcode();
	
	qrcode.setQrcodeVersion(version);
	//System.out.println("�汾�ţ�"+qrcode.getQrcodeVersion());
	qrcode.setQrcodeErrorCorrect('L');
	qrcode.setQrcodeEncodeMode('B');
//	System.out.println("�ɴ洢���ͣ�"+qrcode.getQrcodeErrorCorrect());
	int imgSize=67+(version-1)+12;
	BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize,
			BufferedImage.TYPE_INT_BGR);
	Graphics2D gs = bufferedImage.createGraphics();
	qrcode.setQrcodeVersion(version);
	gs.setBackground(Color.WHITE);
	gs.setColor(Color.BLACK);
	
	// ��ջ���
	gs.clearRect(0, 0, imgSize, version);

	// ��ά������
	
	// ͨ����ά��Ҫ�������ݻ�ȡһ���������͵Ķ�ά����
	boolean[][] calQrocde = qrcode.calQrcode(content.getBytes());
	// ����ƫ����
	

	int pixoff = 2;
	int startR=0, startG=0,startB=0;
	if(null!=startRgb){
		String[] rgb=startRgb.split(",");
		startR=Integer.valueOf(rgb[0]);
		startG=Integer.valueOf(rgb[1]);
		startB=Integer.valueOf(rgb[2]);
	}
	int endR=0, endG=0,endB=0;
	if(null!=endRgb){
		String[] rgb=endRgb.split(",");
		endR=Integer.valueOf(rgb[0]);
		endG=Integer.valueOf(rgb[1]);
		endB=Integer.valueOf(rgb[2]);
	}
	for (int i = 0; i < calQrocde.length; i++) {// ������
		
		for (int j = 0; j < calQrocde[i].length; j++) {// ������

			if (calQrocde[i][j]) {// true����ά����ɫ��false�����

			
			int r=startR+(endR-startR)*(j+1)/calQrocde.length;
			int g=startG+(endG-startG)*(j+1)/calQrocde.length;
			int b=startB+(endB-startB)*(j+1)/calQrocde.length;
			
				Color color = new Color(r, g, b);
				gs.setColor(color);
				gs.fillRect(i * 3+pixoff , j * 3+pixoff , 3, 3);
			}
		}

	}
	//���ͷ��
	BufferedImage logo=ImageIO.read(new File("D:/logo.jpg"));
	//ͷ���С
	int logoSize=imgSize/3;
	//ͷ�����ʼλ��
	int o=(imgSize-logoSize)/2;
	//����ά���ϻ�ͷ��
	gs.drawImage(logo,o, o, logoSize, logoSize, null);
	// �رջ滭����
	gs.dispose();
	// �ѻ�����ͼƬ������ڴ�
	bufferedImage.flush();
	try {
		ImageIO.write(bufferedImage, "png", new File(imgpath));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public static void main(String[] args) throws IOException {
		int version=5;
		String content = "kaoshichenggong";
		String imgpath="D:/��ά��/q.png";
		String startRgb="255,0,0";
		String endRgb="0,0,255";
		
		createqrcode(version,content,imgpath,startRgb,endRgb);
	
	}

}
