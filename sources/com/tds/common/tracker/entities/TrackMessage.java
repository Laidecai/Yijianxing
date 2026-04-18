package com.tds.common.tracker.entities;

import android.os.Parcel;
import android.os.Parcelable;
import com.tds.common.tracker.TdsTrackerConfig;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TrackMessage implements Parcelable {
    public static final Parcelable.Creator<TrackMessage> CREATOR = new Parcelable.Creator<TrackMessage>() { // from class: com.tds.common.tracker.entities.TrackMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackMessage createFromParcel(Parcel parcel) {
            return new TrackMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackMessage[] newArray(int i) {
            return new TrackMessage[i];
        }
    };
    public final long createTime;
    public final Map<String, String> logCommonParams;
    public final Map<String, String> logContentsMap;
    public final TdsTrackerConfig tdsTrackerConfig;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TrackMessage(TdsTrackerConfig tdsTrackerConfig, Map<String, String> map, long j, Map<String, String> map2) {
        this.tdsTrackerConfig = tdsTrackerConfig;
        this.logContentsMap = map;
        this.createTime = j;
        this.logCommonParams = map2;
    }

    protected TrackMessage(Parcel parcel) {
        this.tdsTrackerConfig = (TdsTrackerConfig) parcel.readParcelable(TdsTrackerConfig.class.getClassLoader());
        HashMap map = new HashMap();
        this.logContentsMap = map;
        parcel.readMap(map, getClass().getClassLoader());
        this.createTime = parcel.readLong();
        HashMap map2 = new HashMap();
        this.logCommonParams = map2;
        parcel.readMap(map2, getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.tdsTrackerConfig, i);
        parcel.writeMap(this.logContentsMap);
        parcel.writeLong(this.createTime);
        parcel.writeMap(this.logCommonParams);
    }

    public String toString() {
        return "TrackMessage{tdsTrackerConfig=" + this.tdsTrackerConfig + ", logContentsMap=" + this.logContentsMap + '}';
    }
}
