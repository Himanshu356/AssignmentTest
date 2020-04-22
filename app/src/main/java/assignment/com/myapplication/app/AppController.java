package assignment.com.myapplication.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import assignment.com.myapplication.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();
	private static AppController mInstance;
    private Thread.UncaughtExceptionHandler defaultUEH;
	private RequestQueue mRequestQueue;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath(getResources().getString(R.string.font_path))
				.setFontAttrId(R.attr.fontPath)
				.build()
		);

	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);

	}

}