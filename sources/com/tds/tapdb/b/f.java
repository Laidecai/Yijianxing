package com.tds.tapdb.b;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public class f {

    private static class b implements ServiceConnection {
        private boolean a;
        private String b;

        private b() {
            this.a = false;
        }

        private String a(IBinder iBinder) throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                iBinder.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        public String a() {
            return this.b;
        }

        public boolean b() {
            return this.a;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.a = true;
            if (iBinder != null) {
                try {
                    this.b = a(iBinder);
                } catch (RemoteException e) {
                    n.a((Exception) e);
                }
            }
            synchronized (this) {
                notifyAll();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.a = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public static String a(Context context) {
        String strA;
        k.a(Looper.myLooper() != Looper.getMainLooper(), "getGmsAdId method Cannot be called from the main thread");
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        b bVar = new b();
        if (!context.bindService(intent, bVar, 1)) {
            return null;
        }
        if (bVar.b()) {
            return bVar.a();
        }
        synchronized (bVar) {
            try {
                try {
                    bVar.wait(5000L);
                    strA = bVar.a();
                } catch (InterruptedException e) {
                    n.a((Exception) e);
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return strA;
    }
}
