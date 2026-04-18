package com.tds.common.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ParcelableUtil {
    public static byte[] marshall(Parcelable parcelable) {
        Parcel parcelObtain = Parcel.obtain();
        parcelable.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        return bArrMarshall;
    }

    public static Parcel unmarshall(byte[] bArr) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        return parcelObtain;
    }

    public static <T> T unmarshall(byte[] bArr, Parcelable.Creator<T> creator) {
        Parcel parcelUnmarshall = unmarshall(bArr);
        T tCreateFromParcel = creator.createFromParcel(parcelUnmarshall);
        parcelUnmarshall.recycle();
        return tCreateFromParcel;
    }
}
