package com.google.android.gms.internal.play_billing;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public interface zzd extends IInterface {
    int zza(int i, String str, String str2) throws RemoteException;

    Bundle zzb(int i, String str, String str2, Bundle bundle) throws RemoteException;

    Bundle zzc(int i, String str, String str2, String str3, String str4) throws RemoteException;

    Bundle zzd(int i, String str, String str2, String str3) throws RemoteException;

    int zze(int i, String str, String str2) throws RemoteException;

    Bundle zzf(int i, String str, List<String> list, String str2, String str3, String str4) throws RemoteException;

    Bundle zzg(int i, String str, String str2, String str3, String str4, Bundle bundle) throws RemoteException;

    Bundle zzh(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    int zzi(int i, String str, String str2, Bundle bundle) throws RemoteException;

    Bundle zzj(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle zzk(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle zzl(int i, String str, String str2, Bundle bundle) throws RemoteException;

    Bundle zzm(int i, String str, String str2, Bundle bundle, Bundle bundle2) throws RemoteException;

    Bundle zzn(int i, String str, String str2, Bundle bundle) throws RemoteException;
}
