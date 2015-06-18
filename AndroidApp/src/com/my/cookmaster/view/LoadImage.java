package com.my.cookmaster.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class LoadImage {



	    private ExecutorService executorService; // �̶�����߳���
	    private ImageMemoryCache memoryCache;// �ڴ滺��
	    private ImageFileCache fileCache;// �ļ�����
	    private Map<String, ImageView> taskMap;// �������
	    public Boolean runFlag = true;
	    
	    public void startRun()
	    {
	    	runFlag = true;
	    }
	    public void pauseRun()
	    {
	    	runFlag = false;
	    }

	    public LoadImage() {
	        executorService = Executors.newFixedThreadPool(10);

	        memoryCache = new ImageMemoryCache();
	        fileCache = new ImageFileCache();
	        taskMap = new HashMap<String, ImageView>();
	    }

	    public void addTask(String url, ImageView img) {
	        Bitmap bitmap=memoryCache.getBitmapFromCache(url);
	        if(bitmap!=null)
	        {
	            img.setImageBitmap(bitmap);
	        }else
	        {
	        synchronized (taskMap) {
	        taskMap.put(Integer.toString(img.hashCode()), img);
	        }
	        }
	    }

	    public void doTask() {
	        synchronized (taskMap) {
	        	if(runFlag)//���runflagΪ�棬������ͼƬ
	        	{
		            Collection<ImageView> con = taskMap.values();
		            for (ImageView i : con) {
		                if (i != null) {
		                    if (i.getTag() != null) {
		                        loadImage((String) i.getTag(), i);
		                    }
		                }
		            }
		            taskMap.clear();		
	        	}

	        }
	    }

	    private void loadImage(String url, ImageView img) {
	        /*** �����µ����� ***/
	        executorService.submit(new TaskWithResult(new TaskHandler(url, img),
	                url));
	    }

	    /*** ���һ��ͼƬ,�������ط���ȡ,�������ڴ滺��,Ȼ�����ļ�����,���������ȡ ***/
	    private Bitmap getBitmap(String url) {
	        // ���ڴ滺���л�ȡͼƬ
	        Bitmap result;
	        result = memoryCache.getBitmapFromCache(url);
	        if (result == null) {
	            // �ļ������л�ȡ
	            result = fileCache.getImage(url);
	            if (result == null) {
	                // �������ȡ
	                result = ImageGetForHttp.downloadBitmap(url);
	                if (result != null) {
	                    memoryCache.addBitmapToCache(url, result);
	                    fileCache.saveBmpToSd(result, url);                    
	                }
	            } else {
	                // ��ӵ��ڴ滺��
	                memoryCache.addBitmapToCache(url, result);
	            }
	        }
	        return result;
	    }

	    /*** �����Ϣ ***/
	    private class TaskHandler extends Handler {
	        String url;
	        ImageView img;

	        public TaskHandler(String url, ImageView img) {
	            this.url = url;
	            this.img = img;
	        }

	        @Override
	        public void handleMessage(Message msg) {
	            /*** �鿴imageview��Ҫ��ʾ��ͼƬ�Ƿ񱻸ı� ***/
	            if (img.getTag().equals(url)) {
	                if (msg.obj != null) {
	                    Bitmap bitmap = (Bitmap) msg.obj;
	                    img.setImageBitmap(bitmap);
	                }
	            }
	        }
	    }

	    /*** ���߳����� ***/
	    private class TaskWithResult implements Callable<String> {
	        private String url;
	        private Handler handler;

	        public TaskWithResult(Handler handler, String url) {
	            this.url = url;
	            this.handler = handler;
	        }

	        @Override
	        public String call() throws Exception {
	            Message msg = new Message();
	            msg.obj = getBitmap(url);
	            if (msg.obj != null) {
	                handler.sendMessage(msg);
	            }
	            return url;
	        }

	    }
	}

