package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public class ProxyBillingActivity extends Activity {
    private ResultReceiver zza;
    private boolean zzb;

    private final Intent zza() {
        Intent intent = new Intent("com.android.vending.billing.PURCHASES_UPDATED");
        intent.setPackage(getApplicationContext().getPackageName());
        return intent;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
            r4 = this;
            super.onActivityResult(r5, r6, r7)
            r0 = 0
            java.lang.String r1 = "ProxyBillingActivity"
            r2 = 100
            if (r5 != r2) goto L5a
            com.android.billingclient.api.BillingResult r5 = com.google.android.gms.internal.play_billing.zza.zzc(r7, r1)
            int r5 = r5.getResponseCode()
            r2 = -1
            if (r6 != r2) goto L1b
            if (r5 == 0) goto L19
            r6 = -1
            goto L1b
        L19:
            r5 = 0
            goto L39
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 85
            r2.<init>(r3)
            java.lang.String r3 = "Activity finished with resultCode "
            r2.append(r3)
            r2.append(r6)
            java.lang.String r6 = " and billing's responseCode: "
            r2.append(r6)
            r2.append(r5)
            java.lang.String r6 = r2.toString()
            com.google.android.gms.internal.play_billing.zza.zzb(r1, r6)
        L39:
            android.os.ResultReceiver r6 = r4.zza
            if (r6 == 0) goto L49
            if (r7 != 0) goto L41
            r7 = 0
            goto L45
        L41:
            android.os.Bundle r7 = r7.getExtras()
        L45:
            r6.send(r5, r7)
            goto L75
        L49:
            android.content.Intent r5 = r4.zza()
            if (r7 == 0) goto L56
            android.os.Bundle r6 = r7.getExtras()
            r5.putExtras(r6)
        L56:
            r4.sendBroadcast(r5)
            goto L75
        L5a:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r7 = 69
            r6.<init>(r7)
            java.lang.String r7 = "Got onActivityResult with wrong requestCode: "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r5 = "; skipping..."
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            com.google.android.gms.internal.play_billing.zza.zzb(r1, r5)
        L75:
            r4.zzb = r0
            r4.finish()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.ProxyBillingActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        PendingIntent pendingIntent;
        super.onCreate(bundle);
        if (bundle != null) {
            com.google.android.gms.internal.play_billing.zza.zza("ProxyBillingActivity", "Launching Play Store billing flow from savedInstanceState");
            this.zzb = bundle.getBoolean("send_cancelled_broadcast_if_finished", false);
            if (bundle.containsKey("result_receiver")) {
                this.zza = (ResultReceiver) bundle.getParcelable("result_receiver");
                return;
            }
            return;
        }
        com.google.android.gms.internal.play_billing.zza.zza("ProxyBillingActivity", "Launching Play Store billing flow");
        if (getIntent().hasExtra("BUY_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("BUY_INTENT");
        } else if (getIntent().hasExtra("SUBS_MANAGEMENT_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("SUBS_MANAGEMENT_INTENT");
            this.zza = (ResultReceiver) getIntent().getParcelableExtra("result_receiver");
        } else {
            pendingIntent = null;
        }
        try {
            this.zzb = true;
            startIntentSenderForResult(pendingIntent.getIntentSender(), 100, new Intent(), 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 53);
            sb.append("Got exception while trying to start a purchase flow: ");
            sb.append(strValueOf);
            com.google.android.gms.internal.play_billing.zza.zzb("ProxyBillingActivity", sb.toString());
            ResultReceiver resultReceiver = this.zza;
            if (resultReceiver != null) {
                resultReceiver.send(6, null);
            } else {
                Intent intentZza = zza();
                intentZza.putExtra("RESPONSE_CODE", 6);
                intentZza.putExtra("DEBUG_MESSAGE", "An internal error occurred.");
                sendBroadcast(intentZza);
            }
            this.zzb = false;
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && this.zzb) {
            Intent intentZza = zza();
            intentZza.putExtra("RESPONSE_CODE", 1);
            intentZza.putExtra("DEBUG_MESSAGE", "Billing dialog closed.");
            sendBroadcast(intentZza);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        ResultReceiver resultReceiver = this.zza;
        if (resultReceiver != null) {
            bundle.putParcelable("result_receiver", resultReceiver);
        }
        bundle.putBoolean("send_cancelled_broadcast_if_finished", this.zzb);
    }
}
