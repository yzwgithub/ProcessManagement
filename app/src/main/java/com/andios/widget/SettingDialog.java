package com.andios.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.andios.activity.R;

/**Dialog Created by YangZheWen on 2017/10/24.
 * 自定义Dialog
 */
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
}
