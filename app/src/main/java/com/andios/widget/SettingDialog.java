package com.andios.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andios.activity.R;

public class SettingDialog extends Dialog implements View.OnClickListener {
	public Button bt1, bt2, btcancle;
	public SettingDialog(Context context, int theme) {
		super(context, theme);
		this.setContentView(R.layout.dialog_setting);
		init();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	private void init() {
		bt1 = (Button) this.findViewById(R.id.bt1);
		bt2 = (Button) this.findViewById(R.id.bt2);
		btcancle = (Button) this.findViewById(R.id.btcancle);
		btcancle.setOnClickListener(this);
	}
	/**
	 * 设置按钮文字
	 * @param a bt1
	 * @param b bt2
	 */
	public void setButtonContent(String a, String b) {
		bt1.setText(a);
		bt2.setText(b);
	}
	@Override
	public void onClick(View v) {
		this.cancel();
	}

//	public void showInputeDialog(final Context context){
//		final EditText editText=new EditText(context);
//		AlertDialog.Builder inputeDialog=new AlertDialog.Builder(context);
//		inputeDialog.setTitle("请输入项目名称").setView(editText);
//		inputeDialog.setPositiveButton("确定", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(context,"编辑成功",Toast.LENGTH_SHORT).show();
//				String work=editText.getText().toString();
//			}
//		}).show();
//	}
}
