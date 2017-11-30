package com.aqap.matrix.faurecia.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {
	public static boolean copy(File copyFrom, File copyTo) {
		int bufferSize = 16 * 1024;
		boolean result = false;
		byte[] buffer = new byte[bufferSize];
		InputStream in = null;
		OutputStream out = null;
		try {
			try {
				in = new BufferedInputStream(new FileInputStream(copyFrom), bufferSize);
				out = new BufferedOutputStream(new FileOutputStream(copyTo), bufferSize);
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
			buffer = null;
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath 文件完整路径
	 * @return true/false 成功删除则返回true
	 */
	public static void removeFile(String filePath) {

		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 根据当前时间产生随机上传文件名
	 * 
	 * @return
	 */
	public static String generateFileName(String fileName) {
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());
		// 储存返回结果的StringBuffer
		StringBuffer result = new StringBuffer(formatDate);
		// 随机生成文件编号
		int random = new Random().nextInt(10000);
		// 追加文件随机编号
		result.append(random);
		// 追加文件后缀名称
		result.append(fileName.substring(fileName.lastIndexOf(".")));

		return result.toString();
	}

	public static void createDir(String filePath) {
		if (!new File(filePath).mkdirs()) {
			System.out.println("The folder exists");
		} else {
			System.out.println("The folder create success");
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param upload文件流
	 * @param newPath新文件路径和名称
	 * @throws Exception
	 */
	public static void copy(File upload, String newPath) throws Exception {
		FileOutputStream fos = new FileOutputStream(newPath);
		FileInputStream fis = new FileInputStream(upload);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
	}

}
