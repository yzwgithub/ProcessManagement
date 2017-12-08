package com.andios.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andios.activity.MapActivity;
import com.andios.adapter.FeedBackAdapter;
import com.andios.activity.R;
import com.andios.dao.DataOperate;
import com.andios.dao.HistoryHelper;
import com.andios.util.CameraUtil;
import com.andios.widget.SettingDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * Created by YangZheWen 2017/11/21.
 *
 */

public class DiscoverFragment extends Fragment implements View.OnClickListener {
    DataOperate dataOperate=new DataOperate();
    // 拍照
    private final int REQ_CODE_CAMERA = 21;
    // 相册
    private final int REQ_CODE_PICTURE = 22;
    // 裁图
    private final int REQ_CODE_CUT = 23;
    private LinearLayout imgFbackAdd;
    private GridView gvFbackImg;
    private FeedBackAdapter adapter;
    private SettingDialog setDialog;
    private List<String> imgList;
    private Uri imgUrl;
    private Spinner spinner;
    private TextView textLocal,textTime;
    private EditText textWork,project_details;
    private Button signIn;
    private Button signOut;
    private Cursor cursor;
    private boolean isGet=false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feedback, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        spinner = (Spinner) getView().findViewById(R.id.spinner);
        textLocal = (TextView) getView().findViewById(R.id.getLocal);
        textWork = (EditText) getView().findViewById(R.id.work);
        project_details = (EditText) getView().findViewById(R.id.project_details);
        textTime = (TextView) getView().findViewById(R.id.textTime);
        imgFbackAdd = (LinearLayout) getView().findViewById(R.id.img_fback_add);
        gvFbackImg = (GridView) getView().findViewById(R.id.gv_fback_img);
        setDialog = new SettingDialog(getActivity(), R.style.setting_dialog_style);
        signIn= (Button) getView().findViewById(R.id.signIn);
        signOut= (Button) getView().findViewById(R.id.signOut);
        setDialog.bt1.setOnClickListener(this);
        setDialog.bt2.setOnClickListener(this);
        imgFbackAdd.setOnClickListener(this);
        textLocal.setOnClickListener(this);
        textTime.setOnClickListener(this);
        textWork.setOnClickListener(this);
        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        imgList = new ArrayList<>();
        gvFbackImg.setEnabled(false);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_fback_add:
                if(imgList != null &&imgList.size()<3){
                    setDialog.setButtonContent("通过摄像头拍摄", "从手机相册选择");
                    setDialog.show();
                }else{
                    Toast.makeText(getActivity(), "做多只能添加三张照片", Toast.LENGTH_SHORT).show();
                }break;
            case R.id.bt1:
                imgUrl = CameraUtil.getTempUri();
                startActivityForResult(CameraUtil.takePicture(imgUrl), REQ_CODE_CAMERA);
                setDialog.dismiss();
                break;
            case R.id.bt2:
                CameraUtil.selectPhoto();
                startActivityForResult(Intent.createChooser(CameraUtil.selectPhoto(),"选择照片"),REQ_CODE_PICTURE);
                setDialog.dismiss();
                break;
            case R.id.getLocal:
                isGet=true;
                Intent intent=new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
                break;
            case R.id.textTime:
                long time=System.currentTimeMillis();
                Date date=new Date(time);
                SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                textTime.setText(format.format(date));
                break;
            case R.id.signIn:
                cursor=dataOperate.select(getActivity());
                int id=cursor.getCount();
                String isWork=spinner.getSelectedItem().toString();
                String work=textWork.getText().toString();
                String textDate=textTime.getText().toString();
                String details=project_details.getText().toString();
                if (work.equals("")){
                    Toast.makeText(getContext(),"项目名称不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (details.equals("")){
                    Toast.makeText(getContext(),"项目描述不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!isGet){
                    Toast.makeText(getContext(),"当前位置不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (textDate.equals("获取当前时间")){
                    Toast.makeText(getContext(),"当前时间不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!work.equals("")&&!textDate.equals("获取当前时间")&&!details.equals("")/*&&!local.equals("获取当前位置")*/){
                    dataOperate.insert(getActivity(),id,isWork,work,textDate,details);
                    signInOrOut("http://192.168.1.138:8080/attendance/signIn?");
                    Toast.makeText(getActivity(),"签到成功！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signOut:
                cursor=dataOperate.select(getActivity());
                int id_out=cursor.getCount();
                String isWork_ou=spinner.getSelectedItem().toString();
                String work_ou=textWork.getText().toString();
                String textDate_ou=textTime.getText().toString();
                String details_ou=project_details.getText().toString();
                if (work_ou.equals("")){
                    Toast.makeText(getContext(),"项目名称不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (details_ou.equals("")){
                    Toast.makeText(getContext(),"项目描述不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!isGet){
                    Toast.makeText(getContext(),"当前位置不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (textDate_ou.equals("获取当前时间")){
                    Toast.makeText(getContext(),"当前时间不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!work_ou.equals("")&&!textDate_ou.equals("获取当前时间")&&!details_ou.equals("")/*&&!local.equals("获取当前位置")*/){
                    dataOperate.insert(getActivity(),id_out,isWork_ou,work_ou,textDate_ou,details_ou);
                    signInOrOut("http://192.168.1.138:8080/attendance/signIn?");
                    Toast.makeText(getActivity(),"签到成功！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void setListViewAdapter(){
        if(adapter == null){
            if(imgList != null){
                adapter = new FeedBackAdapter(imgList, getActivity());
                gvFbackImg.setAdapter(adapter);
                adapter.setListener(new FeedBackAdapter.ClickListenerDel() {
                    @Override
                    public void onClick(int position) {
                        imgList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_CAMERA:
                    if(data != null){
                        Uri uri = data.getData();
                        startActivityForResult(CameraUtil.cropPhoto(uri,imgUrl,150,150), REQ_CODE_CUT);
                    }else{
                        startActivityForResult(CameraUtil.cropPhoto(imgUrl,imgUrl,150,150), REQ_CODE_CUT);
                    }
                    break;
                case REQ_CODE_PICTURE:
                    if (data != null) {
                        imgUrl = CameraUtil.getTempUri();
                        startActivityForResult(CameraUtil.cropPhoto(data.getData(),imgUrl,150,150), REQ_CODE_CUT);
                    }
                    break;

                case REQ_CODE_CUT:
                    imgList.add(CameraUtil.getPathFromUri(getActivity(),imgUrl));
                    setListViewAdapter();
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void signInOrOut(String url){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.print("-----------------------------------------------------------");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.print(".............................................................");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("user_id","2");
                map.put("location","黄石市");
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
