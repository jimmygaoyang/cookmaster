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



	    private ExecutorService executorService; // 固定五个线程来
	    private ImageMemoryCache memoryCache;// 内存缓存
	    private ImageFileCache fileCache;// 文件缓存
	    private Map<String, ImageView> taskMap;// 存放任务
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
	        	if(runFlag)//如果runflag为真，就下载图片
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
	        /*** 加入新的任务 ***/
	        executorService.submit(new TaskWithResult(new TaskHandler(url, img),
	                url));
	    }

	    /*** 获得一个图片,从三个地方获取,首先是内存缓存,然后是文件缓存,最后从网络获取 ***/
	    private Bitmap getBitmap(String url) {
	        // 从内存缓存中获取图片
	        Bitmap result;
	        result = memoryCache.getBitmapFromCache(url);
	        if (result == null) {
	            // 文件缓存中获取
	            result = fileCache.getImage(url);
	            if (result == null) {
	                // 从网络获取
	                result = ImageGetForHttp.downloadBitmap(url);
	                if (result != null) {
	                    memoryCache.addBitmapToCache(url, result);
	                    fileCache.saveBmpToSd(result, url);                    
	                }
	            } else {
	                // 添加到内存缓存
	                memoryCache.addBitmapToCache(url, result);
	            }
	        }
	        return result;
	    }

	    /*** 完成消息 ***/
	    private class TaskHandler extends Handler {
	        String url;
	        ImageView img;

	        public TaskHandler(String url, ImageView img) {
	            this.url = url;
	            this.img = img;
	        }

	        @Override
	        public void handleMessage(Message msg) {
	            /*** 查看imageview需要显示的图片是否被改变 ***/
	            if (img.getTag().equals(url)) {
	                if (msg.obj != null) {
	                    Bitmap bitmap = (Bitmap) msg.obj;
	                    img.setImageBitmap(bitmap);
	                }
	            }
	        }
	    }

	    /*** 子线程任务 ***/
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

