package de.fiw.fhws.lecturers.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class VolleySingleton {
	private static VolleySingleton instance;
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private static Context context;

	private VolleySingleton(Context context) {
		this.context = context;
		requestQueue = getRequestQueue();

		imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
			int size = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8);
			private final LruCache<String, Bitmap> cache = new LruCache<>(size);

			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}
		});
	}

	public static synchronized VolleySingleton getInstance(Context context) {
		if (instance == null) {
			instance = new VolleySingleton(context);
		}
		return instance;
	}

	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			int size = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8);
			Cache cache = new DiskBasedCache(context.getCacheDir(), size);
			Network network = new BasicNetwork(new HurlStack());
			requestQueue = new RequestQueue(cache, network);
			requestQueue.start();
		}

		return requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> request) {
		getRequestQueue().add(request);
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}
}
