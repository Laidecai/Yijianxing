package com.google.android.gms.internal.play_billing;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.billingclient.api.BillingClient;
import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public final class zzb extends zze implements zzd {
    zzb(IBinder iBinder) {
        super(iBinder, "com.android.vending.billing.IInAppBillingService");
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final int zza(int i, String str, String str2) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(i);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        Parcel parcelZzp = zzp(1, parcelZzo);
        int i2 = parcelZzp.readInt();
        parcelZzp.recycle();
        return i2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzb(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(3);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(2, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzc(int i, String str, String str2, String str3, String str4) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(3);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(str3);
        parcelZzo.writeString(null);
        Parcel parcelZzp = zzp(3, parcelZzo);
        Bundle bundle = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzd(int i, String str, String str2, String str3) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(3);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(str3);
        Parcel parcelZzp = zzp(4, parcelZzo);
        Bundle bundle = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final int zze(int i, String str, String str2) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(3);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        Parcel parcelZzp = zzp(5, parcelZzo);
        int i2 = parcelZzp.readInt();
        parcelZzp.recycle();
        return i2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzf(int i, String str, List<String> list, String str2, String str3, String str4) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(5);
        parcelZzo.writeString(str);
        parcelZzo.writeStringList(list);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(BillingClient.SkuType.SUBS);
        parcelZzo.writeString(null);
        Parcel parcelZzp = zzp(7, parcelZzo);
        Bundle bundle = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzg(int i, String str, String str2, String str3, String str4, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(i);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(str3);
        parcelZzo.writeString(null);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(8, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzh(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(6);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(str3);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(9, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final int zzi(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(7);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(10, parcelZzo);
        int i2 = parcelZzp.readInt();
        parcelZzp.recycle();
        return i2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzj(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(8);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(BillingClient.SkuType.SUBS);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(801, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzk(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(9);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        parcelZzo.writeString(str3);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(11, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzl(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(9);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(12, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzm(int i, String str, String str2, Bundle bundle, Bundle bundle2) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(10);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        zzg.zzb(parcelZzo, bundle);
        zzg.zzb(parcelZzo, bundle2);
        Parcel parcelZzp = zzp(901, parcelZzo);
        Bundle bundle3 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle3;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final Bundle zzn(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzo = zzo();
        parcelZzo.writeInt(9);
        parcelZzo.writeString(str);
        parcelZzo.writeString(str2);
        zzg.zzb(parcelZzo, bundle);
        Parcel parcelZzp = zzp(902, parcelZzo);
        Bundle bundle2 = (Bundle) zzg.zza(parcelZzp, Bundle.CREATOR);
        parcelZzp.recycle();
        return bundle2;
    }
}
