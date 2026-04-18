package com.android.billingclient.api;

import com.android.billingclient.api.BillingClient;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzaf implements Callable<Void> {
    final /* synthetic */ zzah zza;

    zzaf(zzah zzahVar) {
        this.zza = zzahVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Void call() throws Exception {
        int iZza;
        synchronized (this.zza.zzb) {
            if (!this.zza.zzc) {
                int i = 3;
                try {
                    String packageName = this.zza.zza.zzf.getPackageName();
                    int i2 = 16;
                    iZza = 3;
                    while (true) {
                        if (i2 < 3) {
                            i2 = 0;
                            break;
                        }
                        try {
                            iZza = this.zza.zza.zzg.zza(i2, packageName, BillingClient.SkuType.SUBS);
                            if (iZza == 0) {
                                break;
                            }
                            i2--;
                        } catch (Exception unused) {
                            i = iZza;
                            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Exception while checking if billing is supported; try to reconnect");
                            this.zza.zza.zza = 0;
                            this.zza.zza.zzg = null;
                            iZza = i;
                        }
                    }
                    boolean z = true;
                    this.zza.zza.zzj = i2 >= 5;
                    this.zza.zza.zzi = i2 >= 3;
                    if (i2 < 3) {
                        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "In-app billing API does not support subscription on this device.");
                    }
                    int i3 = 16;
                    while (true) {
                        if (i3 < 3) {
                            break;
                        }
                        iZza = this.zza.zza.zzg.zza(i3, packageName, BillingClient.SkuType.INAPP);
                        if (iZza == 0) {
                            this.zza.zza.zzk = i3;
                            break;
                        }
                        i3--;
                    }
                    BillingClientImpl billingClientImpl = this.zza.zza;
                    billingClientImpl.zzs = billingClientImpl.zzk >= 16;
                    BillingClientImpl billingClientImpl2 = this.zza.zza;
                    billingClientImpl2.zzr = billingClientImpl2.zzk >= 15;
                    BillingClientImpl billingClientImpl3 = this.zza.zza;
                    billingClientImpl3.zzq = billingClientImpl3.zzk >= 14;
                    BillingClientImpl billingClientImpl4 = this.zza.zza;
                    billingClientImpl4.zzp = billingClientImpl4.zzk >= 12;
                    BillingClientImpl billingClientImpl5 = this.zza.zza;
                    billingClientImpl5.zzo = billingClientImpl5.zzk >= 10;
                    BillingClientImpl billingClientImpl6 = this.zza.zza;
                    billingClientImpl6.zzn = billingClientImpl6.zzk >= 9;
                    BillingClientImpl billingClientImpl7 = this.zza.zza;
                    billingClientImpl7.zzm = billingClientImpl7.zzk >= 8;
                    BillingClientImpl billingClientImpl8 = this.zza.zza;
                    if (billingClientImpl8.zzk < 6) {
                        z = false;
                    }
                    billingClientImpl8.zzl = z;
                    if (this.zza.zza.zzk < 3) {
                        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "In-app billing API version 3 is not supported on this device.");
                    }
                    if (iZza == 0) {
                        this.zza.zza.zza = 2;
                    } else {
                        this.zza.zza.zza = 0;
                        this.zza.zza.zzg = null;
                    }
                } catch (Exception unused2) {
                }
                if (iZza == 0) {
                    this.zza.zzf(zzam.zzp);
                } else {
                    this.zza.zzf(zzam.zza);
                }
            }
        }
        return null;
    }
}
