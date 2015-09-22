package com.my.cookmaster.view.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.os.Environment;

public class FileService {
	private Context context;
	public FileService(Context context) {
		super();
		this.context = context;
	}
	/**
	 * �����ļ�
	 * @param fileName �ļ�����
	 * @param content  �ļ�����
	 * @throws IOException
	 */
	public void save(String fileName, String content) throws IOException {
		//��˽�з�ʽ��д���ݣ������������ļ�ֻ�ܱ���Ӧ�÷���
		int dd;
		FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
		fileOutputStream.write(content.getBytes());
		fileOutputStream.close();
	}
	
	/**
	 * �����ļ�
	 * @param fileName �ļ�����
	 * @param content  �ļ�����
	 * @throws IOException
	 */
	public void saveToSDCard(String path,String fileName, String content) throws IOException {
		//File file = new File(new File("/mnt/sdcard"),fileName);
		//���ǲ�ͬ�汾��sdCardĿ¼��ͬ������ϵͳ�ṩ��API��ȡSD����Ŀ¼
		path = path;
        File fd = new File(path);//û���ļ��ʹ���
		if(!fd.exists()){
			fd.mkdirs();
		}
		//ɾ��֮ǰjson
		File fdCheck = new File(path+"/"+fileName);
		if(fdCheck.exists()){
			fdCheck.delete();
		}
		File file = new File(path,fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(content.getBytes());
		fileOutputStream.close();
	}
	
	/**
	 * �����ļ�
	 * @param fileName �ļ�����
	 * @param content  �ļ�����
	 * @throws IOException
	 */
	public void saveToSDCard(String path,String fileName, byte[] content) throws IOException {
		//File file = new File(new File("/mnt/sdcard"),fileName);
		//���ǲ�ͬ�汾��sdCardĿ¼��ͬ������ϵͳ�ṩ��API��ȡSD����Ŀ¼
		path = path;
        File fd = new File(path);//û���ļ��ʹ���
		if(!fd.exists()){
			fd.mkdirs();
		}
		//ɾ��֮ǰjson
		File fdCheck = new File(path+"/"+fileName);
		if(fdCheck.exists()){
			fdCheck.delete();
		}
		File file = new File(path,fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(content);
		fileOutputStream.close();
	}
	/**
	 * ��ȡ�ļ�����
	 * @param fileName �ļ�����
	 * @return �ļ�����
	 * @throws IOException
	 */
	public byte[] readBytes(String fileName) throws IOException {
		File file = new File(fileName);
		if(file == null)
			return null;
		
		FileInputStream fileInputStream = new FileInputStream(file);
		if(fileInputStream == null)
		{
			return null;
		}
		//��ÿ�ζ�ȡ������д�뵽�ڴ��У�Ȼ����ڴ��л�ȡ
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len =0;
		//ֻҪû���꣬���ϵĶ�ȡ
		while((len=fileInputStream.read(buffer))!=-1){
			outputStream.write(buffer, 0, len);
		}
		//�õ��ڴ���д�����������
		byte[] data = outputStream.toByteArray();
		return data;
	}
	
	/**
	 * ��ȡ�ļ�����
	 * @param fileName �ļ�����
	 * @return �ļ�����
	 * @throws IOException
	 */
	public String read(String fileName) throws IOException {
		File file = new File(fileName);
		if(file == null)
			return null;
		
		FileInputStream fileInputStream = new FileInputStream(file);
		if(fileInputStream == null)
		{
			return null;
		}
		//��ÿ�ζ�ȡ������д�뵽�ڴ��У�Ȼ����ڴ��л�ȡ
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len =0;
		//ֻҪû���꣬���ϵĶ�ȡ
		while((len=fileInputStream.read(buffer))!=-1){
			outputStream.write(buffer, 0, len);
		}
		//�õ��ڴ���д�����������
		byte[] data = outputStream.toByteArray();
		return new String(data);
	}
	
	
	
	
	
	
}
