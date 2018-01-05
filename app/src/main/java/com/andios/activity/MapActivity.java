package com.andios.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andios.listener.MyOrientationListener;
import com.andios.util.Constants;
import com.andios.util.LocalInfo;
import com.andios.util.SharedHelper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YangZheWen on 2017/10/23.
 */
public class MapActivity extends AppCompatActivity {
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private Context context;
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude;
	private double mLongtitude;
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener myOrientationListener;
	private float mCurrentX;
	private LocationMode mLocationMode;
	private RelativeLayout mMarkerLy;
	private String Address;
	final String []permission={"Manifest.permission.ACCESS_COARSE_LOCATION"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		try {
			getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		this.context = this;
		initView();
		// 初始化定位
		initLocation();
		initMarker();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker marker)
			{
				Bundle extraInfo = marker.getExtraInfo();
				LocalInfo localInfo = (LocalInfo) extraInfo.getSerializable("localInfo");
				ImageView iv = (ImageView) mMarkerLy
						.findViewById(R.id.id_info_img);
				TextView distance = (TextView) mMarkerLy
						.findViewById(R.id.id_info_distance);
				TextView name = (TextView) mMarkerLy
						.findViewById(R.id.id_info_name);
				TextView zan = (TextView) mMarkerLy
						.findViewById(R.id.id_info_zan);
				iv.setImageResource(localInfo.getImgId());
				distance.setText(localInfo.getDistance());
				name.setText(localInfo.getName());
				zan.setText(localInfo.getZan() + "");
				InfoWindow infoWindow;
				TextView tv = new TextView(context);
				tv.setBackgroundResource(R.drawable.location_tips);
				tv.setPadding(30, 20, 30, 50);
				tv.setText(localInfo.getName());
				tv.setTextColor(Color.parseColor("#ffffff"));
				final LatLng latLng = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
				p.y -= 47;
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

				infoWindow = new InfoWindow(tv, ll,-47);
				mBaiduMap.showInfoWindow(infoWindow);
				mMarkerLy.setVisibility(View.VISIBLE);
				return true;
			}
		});
		mBaiduMap.setOnMapClickListener(new OnMapClickListener()
		{

			@Override
			public boolean onMapPoiClick(MapPoi arg0)
			{
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0)
			{
				mMarkerLy.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();
			}
		});
	}

	private void initMarker()
	{
		mMarkerLy = (RelativeLayout) findViewById(R.id.id_maker_ly);
	}

	private void initLocation()
	{
		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		// 初始化图标
		mIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.navi_map_gps_locked);
		myOrientationListener = new MyOrientationListener(context);

		myOrientationListener
				.setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
				{
					@Override
					public void onOrientationChanged(float x)
					{
						mCurrentX = x;
					}
				});

	}

	private void initView()
	{
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if (!isOPen(MapActivity.this)){
			AlertDialog.Builder builder=new AlertDialog.Builder(MapActivity.this);
			builder.setMessage("定位权限没有打开，是否打开定位权限？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					open(MapActivity.this);
				}
			}).show();
		}
		// 开启定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
		// 开启方向传感器
		myOrientationListener.start();
	}

	/**
	 * 打开设置权限的页面
	 * @param mapActivity
	 */
	private void open(MapActivity mapActivity) {
		ActivityCompat.requestPermissions(MapActivity.this,permission,123);
	}

	/**
	 * 判断应用权限管理中该应用是否打开了允许使用网络的权限
	 * -1: 没有打开  0: 已经打开
	 * @param mapActivity
	 * @return
	 */
	private boolean isOPen(MapActivity mapActivity) {
		int permissions=ContextCompat.checkSelfPermission(MapActivity.this,"android.permission.ACCESS_COARSE_LOCATION");
		if (permissions==PackageManager.PERMISSION_GRANTED) {
			return true;}
		else {
			return false;
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		// 停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		// 停止方向传感器
		myOrientationListener.stop();

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
			case R.id.id_map_common:
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				break;

			case R.id.id_map_site:
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				break;
			case R.id.id_correct:
				if (isOPen(MapActivity.this)&&Constants.signInlocation!=null) {
					signInOrOut(MapActivity.this,signInOrOut());
					setAddress();
					MapActivity.this.finish();
				}else {
					Toast.makeText(MapActivity.this,"位置获取失败，请检查定位权限是否允许",Toast.LENGTH_SHORT).show();
					goToSetting();
				}
				break;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location)
		{

			MyLocationData data = new MyLocationData.Builder()//
					.direction(mCurrentX)//
					.accuracy(location.getRadius())//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.build();
			mBaiduMap.setMyLocationData(data);
			// 设置自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// 更新经纬度
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			if (isFirstIn)
			{
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				setAddress(location.getAddrStr());
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;
				AlertDialog.Builder dialog=new AlertDialog.Builder(MapActivity.this);
				dialog.setMessage("位置到获取"+getAddress()+"，是否确认签到？");
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						signInOrOut(MapActivity.this,signInOrOut());
						setAddress();
						MapActivity.this.finish();
					}
				});
				dialog.show();
			}
		}
	}
	public void setAddress(String address) {
		Address = address;
		Constants.signInlocation=address;
	}
	public String getAddress() {
		return Address;
	}

	public void setAddress(){
		Constants.signInlocation=getAddress();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode==123){
			if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
				boolean b=shouldShowRequestPermissionRationale(permission[0]);
				if (!b){
					Toast.makeText(MapActivity.this,"请手动设置定位权限",Toast.LENGTH_SHORT).show();
					goToSetting();
				}else Toast.makeText(MapActivity.this,"权限申请成功",Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 跳转到设置权限的界面
	 */
	private void goToSetting(){
		Intent intent=new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri=Uri.fromParts("package",getPackageName(),null);
		intent.setData(uri);
		startActivity(intent);
		MapActivity.this.finish();
	}

	/**
	 * 签退的方法
	 * @param context
	 * @param url
	 */
	private void signInOrOut(final Context context, String url){
		StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String s) {
				Toast.makeText(context,Constants.signIn+"成功",Toast.LENGTH_SHORT).show();
				Log.i("as","-----------------------------------------------------------");
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Log.i("as","错误是"+volleyError.getMessage());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				SharedHelper sharedHelper=new SharedHelper(context);
				Map<String,String>data=sharedHelper.read();
				Map<String,String>map=new HashMap<>();
				map.put("user_id",data.get("user_id"));
				map.put("location",getAddress() /*Constants.signInlocation*/);
				map.put("p_id",Constants.project_id);
				return map;
			}
		};
		RequestQueue queue= Volley.newRequestQueue(context);
		queue.add(request);
	}

	/**
	 * 通过判断签到还是签退获取对应的URL
	 * @return
	 */
	private String signInOrOut(){
		String url;
		if (Constants.signInOrOut==0){
			url=Constants.url+"phone/signIn";
			Constants.signIn="签到";
		}else {
			url = Constants.url + "phone/signOut";
			Constants.signIn="签退";
		}
		return url;
	}
}
