package com.worldsay.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.worldsay.R;


public class GGDialog {

	public void showTwoSelcetDialog(Context context, String titleMsg,String message,
			final OnDialogButtonClickedListenered listener) {
		final Dialog dialog = new Dialog(context , R.style.dialog);
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_two_vhawk);

		TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
		TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
		TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
		TextView messageBody = (TextView) window.findViewById(R.id.messageBody);
		tv_title.setText(titleMsg);
		messageBody.setText(message);
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onCancelClicked();
				dialog.dismiss();
			}
		});
		tv_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onConfirmClicked();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void showImgSelcetDialog(Context context, String titleMsg,String message,
									final OnDialogButtonClickedListenered listener) {
		final Dialog dialog = new Dialog(context , R.style.dialog);
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_img_vhawk);

		TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
		TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
		TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
		TextView messageBody = (TextView) window.findViewById(R.id.messageBody);
		tv_title.setText(titleMsg);
		messageBody.setText(message);
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onCancelClicked();
				dialog.dismiss();
			}
		});
		tv_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onConfirmClicked();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	public void showOneSelectDialog(Context context){
		
		final Dialog dialog = new Dialog(context , R.style.dialog);
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_one_vhawk);
		
		TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
public void showOneSelectDialog(Context context,String title){
		
		final Dialog dialog = new Dialog(context , R.style.dialog);
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_one_vhawk);
		
		TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
		TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
		tv_title.setText(title);

		tv_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	
	

	public interface OnDialogButtonClickedListenered {
		public void onCancelClicked();

		public void onConfirmClicked();
	}

}
