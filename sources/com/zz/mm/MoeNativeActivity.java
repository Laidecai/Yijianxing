package com.zz.mm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class MoeNativeActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory("android.intent.category.LAUNCHER") && action != null && action.equals("android.intent.action.MAIN")) {
                finish();
                return;
            }
        }
        Boolean.valueOf(false);
        String txtFile = readTxtFile("info.txt");
        final SharedPreferences sharedPreferences = getSharedPreferences("base", 0);
        if (Boolean.valueOf(sharedPreferences.getBoolean("isFirstStart", true)).booleanValue()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("隐私协议");
            builder.setMessage("隐私政策" + txtFile);
            builder.setCancelable(false);
            builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() { // from class: com.zz.mm.MoeNativeActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Process.killProcess(Process.myPid());
                }
            });
            builder.setPositiveButton("同意", new DialogInterface.OnClickListener() { // from class: com.zz.mm.MoeNativeActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putBoolean("isFirstStart", false);
                    editorEdit.commit();
                    MoeNativeActivity.this.startActivity(new Intent(MoeNativeActivity.this, (Class<?>) MainActivity.class));
                }
            });
            builder.show();
            return;
        }
        startActivity(new Intent(this, (Class<?>) MainActivity.class));
    }

    String readTxtFile(String fileName) {
        try {
            InputStream inputStreamOpen = getAssets().open(fileName);
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String strTrim = new String(bArr, "utf8").trim();
            inputStreamOpen.close();
            if (strTrim.equals("")) {
                System.err.print("请确认文件是否存在！！");
            } else {
                System.out.print("隐私说明加载完成！！");
            }
            return strTrim;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
