package com.tds.common.widgets.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class SafeDialogFragment extends DialogFragment {
    @Override // android.app.DialogFragment, android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        try {
            super.onActivityCreated(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
