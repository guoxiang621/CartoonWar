package com.xiang.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
* @ClassName: GetImageUtil
* @Description: ��ȡͼƬ�Ĺ�����
* @author guoxiang
* @date 2019��8��17�� 
*
*/
public class GetImageUtil {

	// URL  Uniform Resource Locatorͳһ��Դ��λ��
	
	// ��ȡͼƬ�ķ���
	public static Image getImg(String imgName) {
		// ����
		URL resource = GetImageUtil.class.getClassLoader().getResource("com/xiang/img/" + imgName);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}
}
