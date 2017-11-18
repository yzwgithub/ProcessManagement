package com.andios.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andios.adapter.FeedBackAdapter;
import com.andios.activity.R;
import com.andios.util.CameraUtil;
import com.andios.widget.SettingDialog;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Created by ASUS on 2017/6/13.
 */

public class DiscoverFragment extends Fragment implements View.OnClickListener {

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
    private String []text;
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
        imgFbackAdd = (LinearLayout) getView().findViewById(R.id.img_fback_add);
        gvFbackImg = (GridView) getView().findViewById(R.id.gv_fback_img);
        setDialog = new SettingDialog(getActivity(), R.style.setting_dialog_style);
        setDialog.bt1.setOnClickListener(this);
        setDialog.bt2.setOnClickListener(this);
        imgFbackAdd.setOnClickListener(this);
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
}
