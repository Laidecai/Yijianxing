package com.tds.common.permission;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.alipay.sdk.widget.d;
import com.tds.common.R;
import com.tds.common.utils.NavigationBarUtil;

/* JADX INFO: loaded from: classes.dex */
public class PermissionDialog extends DialogFragment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String TAG = "PermissionDialog";
    private PermissionDialogCallback mCallback;

    public static PermissionDialog newInstance(String str, String str2, String str3, String str4, PermissionDialogCallback permissionDialogCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(d.v, str);
        bundle.putString("content", str2);
        bundle.putString("cancel", str3);
        bundle.putString("confirm", str4);
        PermissionDialog permissionDialog = new PermissionDialog();
        permissionDialog.setArguments(bundle);
        permissionDialog.setPermissionCallback(permissionDialogCallback);
        return permissionDialog;
    }

    public void setPermissionCallback(PermissionDialogCallback permissionDialogCallback) {
        this.mCallback = permissionDialogCallback;
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.tds_common_permission_dialog);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.tds_common_permission_forward_setting, viewGroup, false);
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.tds.common.permission.PermissionDialog.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return i == 4;
            }
        });
        NavigationBarUtil.focusNotAle(getDialog().getWindow());
        super.onStart();
        NavigationBarUtil.hideNavigationBar(getDialog().getWindow());
        NavigationBarUtil.clearFocusNotAle(getDialog().getWindow());
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Bundle arguments = getArguments();
        if (arguments == null || getActivity() == null) {
            dismiss();
            return;
        }
        ((TextView) view.findViewById(R.id.tv_permission_title)).setText(arguments.getString(d.v));
        ((TextView) view.findViewById(R.id.tv_permission_content)).setText(arguments.getString("content"));
        Button button = (Button) view.findViewById(R.id.bt_permission_cancel);
        Button button2 = (Button) view.findViewById(R.id.bt_permission_ok);
        button.setText(arguments.getString("cancel"));
        button2.setText(arguments.getString("confirm"));
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.tds.common.permission.PermissionDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PermissionDialog.this.mCallback.onConfirm();
                PermissionDialog.this.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.tds.common.permission.PermissionDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PermissionDialog.this.mCallback.onClose();
                PermissionDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.iv_permission_close).setOnClickListener(new View.OnClickListener() { // from class: com.tds.common.permission.PermissionDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PermissionDialog.this.mCallback.onClose();
                PermissionDialog.this.dismiss();
            }
        });
    }
}
