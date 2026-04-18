package com.tds.common.widgets.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tds.common.R;
import com.tds.common.widgets.HoloThemeHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractAlertDialog extends SafeDialogFragment {
    protected Activity mActivity;

    public interface AlertClickCallback {
        void onLeftClick();

        void onRightClick();
    }

    public abstract View getContentView();

    public abstract int[] getLayoutParams();

    public abstract Event leftEvent();

    public abstract Event rightEvent();

    public static class Event {
        View.OnClickListener listener;
        String text;

        public Event(String str, View.OnClickListener onClickListener) {
            this.text = str;
            this.listener = onClickListener;
        }
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        Dialog dialog = getDialog();
        if (dialog == null || activity == null || activity.getWindowManager() == null || dialog.getWindow() == null) {
            return;
        }
        int[] layoutParams = getLayoutParams();
        dialog.getWindow().setLayout(layoutParams[0], layoutParams[1]);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        HoloThemeHelper.fixHoloDialogBlueLine(dialog);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.tds_common_view_alert, viewGroup, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (getArguments() == null) {
            dismiss();
        } else {
            initView(view);
        }
    }

    private void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.tv_alert_negative);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_alert_positive);
        if (leftEvent() != null) {
            textView.setText(leftEvent().text);
            textView.setOnClickListener(leftEvent().listener);
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        if (rightEvent() != null) {
            textView2.setText(rightEvent().text);
            textView2.setOnClickListener(rightEvent().listener);
            textView2.setVisibility(0);
        } else {
            textView2.setVisibility(8);
        }
        if (getContentView() != null) {
            ((ViewGroup) view.findViewById(R.id.tv_alert_container)).addView(getContentView(), 0, new ViewGroup.LayoutParams(-2, -2));
        }
    }
}
