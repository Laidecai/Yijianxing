package com.google.android.gms.internal.play_billing;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zzc extends zzf implements zzd {
    public static zzd zzo(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.vending.billing.IInAppBillingService");
        return iInterfaceQueryLocalInterface instanceof zzd ? (zzd) iInterfaceQueryLocalInterface : new zzb(iBinder);
    }
}
