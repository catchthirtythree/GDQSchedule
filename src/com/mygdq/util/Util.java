package com.mygdq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * General utility class.
 * 
 * @author Michael
 */
public class Util {
	/**
	 * GDQ URL.
	 */
	private static final String URL = "https://gamesdonequick.com/schedule";
	
	/**
	 * Typeface singleton manager to avoid memory leaks.
	 *  
	 * @author Michael
	 */
	public static class TypefaceSingleton {
		private static Map<String, Typeface> instances = new HashMap<String, Typeface>();
		private TypefaceSingleton() {}
		
		public static Typeface instance(Context context, String font) {
			if (!instances.containsKey(font)) {
				instances.put(font, Typeface.createFromAsset(context.getAssets(), font));
			}
			
			return instances.get(font);
		}
	}

	/**
	 * Get the month from a date.
	 * @param date
	 * @return
	 */
	public static int getDayFrom(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}
	
	/**
	 * I don't even know how to document this one but it works. This will probably fail for someone in a weird timezone.
	 * @param date
	 * @return
	 */
	public static Date getRealDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		
		TimeZone tz = gc.getTimeZone();
		gc.add(GregorianCalendar.MILLISECOND, +tz.getDSTSavings());
		
		return gc.getTime();
	}
	
	/**
	 * Get HTML from a url.
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String getHtmlFrom(String url) throws MalformedURLException, IOException {
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();
	
		BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
		    sb.append(line);
		}
		
		return sb.toString();
	}

	/**
	 * Type of phone networks.
	 */
	public static enum NetworkType { WIFI, MOBILE; }
	
	/**
	 * Check if the phone has a network connection.
	 * @param context
	 * @param ntype
	 * @return
	 */
	public static boolean haveNetworkConnection(Context context, NetworkType ntype) {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase(ntype.name()))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	    } 
	    
	    return haveConnectedWifi || haveConnectedMobile;
	}
	
	/**
	 * Reverse an array.
	 * @param a
	 */
	public static <T> void reverse(T[] a) {
		for(int i = 0; i < a.length / 2; i++) {
		    T temp = a[i];
		    a[i] = a[a.length - i - 1];
		    a[a.length - i - 1] = temp;
		}
	}
	
	/**
	 * Change the default font for the application.
	 * @param context
	 * @param staticTypefaceFieldName
	 * @param fontAssetName
	 */
	public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
		try {
			final Typeface newTypeface = Typeface.createFromAsset(context.getAssets(), fontAssetName);
			final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
			staticField.setAccessible(true);
			staticField.set(null, newTypeface);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test a connection by making an http request with the website.
	 * @param timeout
	 * @return
	 * @throws IOException
	 */
	public static boolean testNetworkConnection(int timeout) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
			if (connection == null) return false;
			connection.setRequestMethod("HEAD");
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.connect();
			
			int responseCode = connection.getResponseCode();
			return responseCode == 200;
		} catch (IOException e) {
			return false;
		}
	}
}