package com.tds.common.tracker.entities;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TrackMessageList implements Parcelable {
    public static final Parcelable.Creator<TrackMessageList> CREATOR = new Parcelable.Creator<TrackMessageList>() { // from class: com.tds.common.tracker.entities.TrackMessageList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackMessageList createFromParcel(Parcel parcel) {
            return new TrackMessageList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackMessageList[] newArray(int i) {
            return new TrackMessageList[i];
        }
    };
    public List<TrackMessage> trackMessageList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TrackMessageList(List<TrackMessage> list) {
        this.trackMessageList = list;
    }

    protected TrackMessageList(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.trackMessageList = arrayList;
        parcel.readTypedList(arrayList, TrackMessage.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.trackMessageList);
    }
}
