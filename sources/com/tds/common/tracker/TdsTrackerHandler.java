package com.tds.common.tracker;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.tds.common.TapCommon;
import com.tds.common.account.AccountUtil;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.net.PlatformXUA;
import com.tds.common.net.TdsApiClient;
import com.tds.common.net.TdsHttp;
import com.tds.common.reactor.RxBus;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.tracker.entities.LogBean;
import com.tds.common.tracker.entities.TrackMessage;
import com.tds.common.tracker.entities.TrackMessageList;
import com.tds.common.tracker.entities.TwoTuple;
import com.tds.common.tracker.model.TrackEventUpdateAction;
import com.tds.common.tracker.session.SessionIdManager;
import com.tds.common.utils.CommonUtils;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.FileUtil;
import com.tds.common.utils.GUIDHelper;
import com.tds.common.utils.ListUtil;
import com.tds.common.utils.Lz4Util;
import com.tds.common.utils.NetworkUtil;
import com.tds.common.utils.ParcelableUtil;
import com.tds.common.utils.SP;
import com.tds.common.utils.TimeUtil;
import com.tds.common.utils.UIUtils;
import com.tds.tapdb.b.g;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TdsTrackerHandler extends Handler {
    private static final long DEFAULT_SAVE_TIME_INTERVAL = 500;
    public static final int DELAY_MILLIS = 5000;
    private static final int LOG_DELAY = 2;
    private static final int LOG_READ_FROM_CACHE = 0;
    private static final int LOG_SEND = 1;
    public static final int MESSAGES_EXCEED = 2000;
    private static final int SAVE_UNSENT_LOGS = 3;
    private static final String SUFFIX_TOPIC_COUNT_PREFERENCE = "_topic_count_preference";
    private static final String SUFFIX_TOPIC_DATA_SIZE_PREFERENCE = "_topic_data_size_preference";
    public static final String TAG = "TdsTrackerHandler";
    private static final String TOPIC_PREFERENCE = "topic_preference";
    private static final Set<String> unReadCacheSet = new HashSet();
    private long lastSaveTime;
    private SP mSp;
    Map<String, CopyOnWriteArrayList<TrackMessage>> messageListMap;
    private boolean readUnSendData;
    TdsApiClient tdsApiClient;

    public TdsTrackerHandler(Looper looper) {
        super(looper);
        this.lastSaveTime = -1L;
        this.readUnSendData = false;
        this.messageListMap = new ConcurrentHashMap();
        this.tdsApiClient = new TdsApiClient.Builder().baseUrl("").tdsClient(TdsHttp.newClientBuilder().trustAllCerts(false).build()).build();
        this.mSp = SP.getSP(TAG);
        this.readUnSendData = false;
        SessionIdManager.getInstance().registerSession(2);
        sendReadFromCacheMessage();
    }

    private boolean checkSPState() {
        if (this.mSp == null) {
            this.mSp = SP.getSP(TAG);
        }
        return this.mSp != null;
    }

    public void sendReadFromCacheMessage() {
        sendMessage(Message.obtain(this, 0));
    }

    public void sendTrackMessage(TdsTrackerConfig tdsTrackerConfig, Map<String, String> map) {
        sendMessage(Message.obtain(this, 1, new TrackMessage(tdsTrackerConfig, map, TimeUtil.getUnixTimestamp(), makeCommonParams(tdsTrackerConfig))));
    }

    public void sendLogMessageDelayed() {
        removeMessages(2);
        sendMessageDelayed(Message.obtain(this, 2), 5000L);
    }

    public void sendSaveLogMessage(TdsTrackerConfig tdsTrackerConfig) {
        removeMessages(3);
        Message messageObtain = Message.obtain(this, 3);
        messageObtain.obj = tdsTrackerConfig;
        sendMessage(messageObtain);
    }

    public void sendSaveLogMessageDelay(TdsTrackerConfig tdsTrackerConfig, long j) {
        removeMessages(3);
        Message messageObtain = Message.obtain(this, 3);
        messageObtain.obj = tdsTrackerConfig;
        sendMessageDelayed(messageObtain, j);
    }

    public static Map<String, String> getCommonParams(Context context, String str) {
        HashMap map = new HashMap();
        map.put(CommonParam.SDK_VERSION, "32900001");
        map.put(CommonParam.SDK_VERSION_NAME, "3.29.0");
        map.put(CommonParam.DEVICE_ID, GUIDHelper.INSTANCE.getUID());
        map.put(CommonParam.INSTALL_UUID, GUIDHelper.INSTANCE.getUID());
        map.put(CommonParam.T_LOG_ID, UUID.randomUUID().toString());
        map.put(CommonParam.DEVICE_VERSION, DeviceUtils.getManufacturer());
        map.put(CommonParam.MODEL, DeviceUtils.getModel());
        map.put(CommonParam.CPU, DeviceUtils.getCpuInfo());
        map.put(CommonParam.CPU_ABIS, DeviceUtils.getCpuABIS().replace("[", "").replace("]", ""));
        map.put(CommonParam.OS_PARAM, DeviceUtils.getPlatform());
        map.put("sv", DeviceUtils.getOSVersion());
        map.put("timestamp", System.currentTimeMillis() + "");
        map.put(CommonParam.LOCALE, Locale.getDefault().toString());
        map.put(CommonParam.TIMEZONE, TimeZone.getDefault().getID());
        map.put(CommonParam.PROCESS_SESSION_ID, SessionIdManager.getInstance().getSessionId(2));
        map.put(CommonParam.SDK_LOCALE, LocalizeManager.getPreferredLanguageString());
        map.put(CommonParam.PN, "TapSDK");
        map.put(CommonParam.TRACK_CODE, "1");
        map.put(CommonParam.HARDWARE, DeviceUtils.getCPUHardware());
        map.put(CommonParam.TDS_USER_ID, AccountUtil.getCurrentTdsId());
        map.put(CommonParam.OPEN_ID, AccountUtil.getCurrentTapId());
        if (context != null) {
            Point realScreenSize = UIUtils.getRealScreenSize(context);
            map.put(CommonParam.SR, UIUtils.getScreenSizeInfo(context));
            map.put(CommonParam.SR_WIDTH, "" + Math.max(realScreenSize.x, realScreenSize.y));
            map.put(CommonParam.SR_HEIGHT, "" + Math.min(realScreenSize.x, realScreenSize.y));
            map.put(CommonParam.APP_PACKAGE_NAME, context.getPackageName());
            map.put(CommonParam.APP_VERSION, DeviceUtils.getPackageVersion(context));
            map.put(CommonParam.APP_VERSION_CODE, String.valueOf(DeviceUtils.getPackageVersionCode(context)));
            map.put(CommonParam.RAM, DeviceUtils.getRemainingRamSize(context));
            map.put(CommonParam.ROM, DeviceUtils.getRemainingRomSize());
            map.put(CommonParam.NETWORK_TYPE, NetworkUtil.getConnectedType(context));
            map.put(CommonParam.MOBILE_TYPE, NetworkUtil.getNetworkType(context));
        }
        if (str == null) {
            str = "";
        }
        map.put(CommonParam.PROJECT, str);
        return map;
    }

    public Map<String, String> makeCommonParams(TdsTrackerConfig tdsTrackerConfig) {
        HashMap map = new HashMap(getCommonParams(null, tdsTrackerConfig.sdkModel));
        map.put(CommonParam.SDK_VERSION, String.valueOf(tdsTrackerConfig.sdkVersionCode));
        map.put(CommonParam.SDK_VERSION_NAME, String.valueOf(tdsTrackerConfig.sdkVersionName));
        map.put(CommonParam.APP_PACKAGE_NAME, tdsTrackerConfig.appPackageName);
        map.put(CommonParam.APP_VERSION, tdsTrackerConfig.appVersion);
        map.put(CommonParam.APP_VERSION_CODE, tdsTrackerConfig.appVersionCode);
        map.put(CommonParam.RAM, tdsTrackerConfig.ramSize);
        map.put(CommonParam.ROM, tdsTrackerConfig.romSize);
        map.put(CommonParam.NETWORK_TYPE, tdsTrackerConfig.networkType);
        map.put(CommonParam.MOBILE_TYPE, tdsTrackerConfig.mobileType);
        String str = tdsTrackerConfig.screenSize;
        map.put(CommonParam.SR, str);
        if (!TextUtils.isEmpty(str) && str.contains("*")) {
            int iIndexOf = str.indexOf("*");
            int i = Integer.parseInt(str.substring(0, iIndexOf));
            int i2 = Integer.parseInt(str.substring(iIndexOf + 1));
            map.put(CommonParam.SR_WIDTH, "" + Math.max(i, i2));
            map.put(CommonParam.SR_HEIGHT, "" + Math.min(i, i2));
        }
        return map;
    }

    private boolean hasUnSendTrackMessage() {
        Iterator<Map.Entry<String, CopyOnWriteArrayList<TrackMessage>>> it = this.messageListMap.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue().size() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkShouldUpload() {
        return hasUnSendTrackMessage();
    }

    private boolean checkAbnormal(List<TrackMessage> list) {
        boolean z = list != null && list.size() >= 2000;
        if (z) {
            TdsTrackerConfig tdsTrackerConfig = list.get(0).tdsTrackerConfig;
            list.clear();
            saveUnSendTrackMessageToFile(tdsTrackerConfig);
            RxBus.getInstance().send(new TwoTuple(tdsTrackerConfig.topic, String.valueOf(0)));
        }
        return z;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Log.i(TAG, "------handleMessage------");
        try {
            int i = message.what;
            if (i != 0) {
                if (i == 1) {
                    Log.i(TAG, "LOG_SEND");
                    if (!(message.obj instanceof TrackMessage)) {
                        return;
                    }
                    TrackMessage trackMessage = (TrackMessage) message.obj;
                    RxBus.getInstance().send(new TrackEventUpdateAction(new TwoTuple(trackMessage.tdsTrackerConfig.topic, trackMessage.logContentsMap.get(CommonParam.TDS_USER_ID))));
                    unReadCacheSet.add(trackMessage.tdsTrackerConfig.cachePath);
                    doUploadLog(trackMessage);
                    sendSaveLogMessage(trackMessage.tdsTrackerConfig);
                } else if (i == 2) {
                    Log.i(TAG, "LOG_DELAY");
                    doUploadLog();
                } else if (i == 3) {
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    long j = this.lastSaveTime;
                    if (j == -1 || jUptimeMillis - j > DEFAULT_SAVE_TIME_INTERVAL) {
                        saveUnSendTrackMessageToFile((TdsTrackerConfig) message.obj);
                        this.lastSaveTime = jUptimeMillis;
                    } else {
                        sendSaveLogMessageDelay((TdsTrackerConfig) message.obj, DEFAULT_SAVE_TIME_INTERVAL);
                    }
                }
            } else if (!this.readUnSendData) {
                readUnSendTrackMessageFromCache();
                this.readUnSendData = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readUnSendTrackMessageFromCache() {
        Log.i(TAG, "-------readUnSendTrackMessageFromCache-------start");
        if (checkSPState()) {
            for (String str : this.mSp.getStringSet(TOPIC_PREFERENCE, new HashSet())) {
                try {
                    Log.i(TAG, "cachePath:" + str);
                    String strSubstring = str.substring(str.lastIndexOf("/")).substring(1);
                    Log.i(TAG, "topic:" + strSubstring);
                    int i = this.mSp.getInt(strSubstring + SUFFIX_TOPIC_DATA_SIZE_PREFERENCE, 0);
                    Log.i(TAG, "topic:" + i);
                    List<TrackMessage> arrayList = ((TrackMessageList) ParcelableUtil.unmarshall(Lz4Util.decompressorByte(FileUtil.returnFileByte(str), i), TrackMessageList.CREATOR)).trackMessageList;
                    Log.i(TAG, "unSendtrackMessageList size:" + arrayList.size());
                    if (ListUtil.isEmpty(arrayList)) {
                        arrayList = new ArrayList();
                    }
                    for (TrackMessage trackMessage : arrayList) {
                        if (trackMessage != null && trackMessage.tdsTrackerConfig != null && !TextUtils.isEmpty(trackMessage.tdsTrackerConfig.topic)) {
                            CopyOnWriteArrayList<TrackMessage> copyOnWriteArrayList = this.messageListMap.get(trackMessage.tdsTrackerConfig.topic);
                            if (copyOnWriteArrayList == null) {
                                copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                                this.messageListMap.put(trackMessage.tdsTrackerConfig.topic, copyOnWriteArrayList);
                            }
                            copyOnWriteArrayList.add(trackMessage);
                        }
                    }
                    RxBus.getInstance().send(new TwoTuple(strSubstring, String.valueOf(arrayList.size())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (checkShouldUpload()) {
                doUploadLog();
            }
            Log.i(TAG, "-------readUnSendTrackMessageFromCache-------end");
        }
    }

    private void saveUnSendTrackMessageToFile(TdsTrackerConfig tdsTrackerConfig) {
        Log.i(TAG, "-------saveUnSendTrackMessageToFile-------start");
        if (checkSPState()) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            String str = tdsTrackerConfig.cachePath;
            TwoTuple<Integer, byte[]> unSendByteData = getUnSendByteData(tdsTrackerConfig.topic);
            int iIntValue = unSendByteData.paramA.intValue();
            byte[] bArr = unSendByteData.paramB;
            Set<String> stringSet = this.mSp.getStringSet(TOPIC_PREFERENCE, new HashSet());
            Log.i(TAG, "topic:" + tdsTrackerConfig.topic);
            if (bArr == null) {
                Log.i(TAG, "no unsave data");
                stringSet.remove(tdsTrackerConfig.cachePath);
                this.mSp.putInt(tdsTrackerConfig.topic + SUFFIX_TOPIC_DATA_SIZE_PREFERENCE, 0);
                this.mSp.putInt(tdsTrackerConfig.topic + SUFFIX_TOPIC_COUNT_PREFERENCE, 0);
                FileUtil.deleteFile(str);
            } else {
                Log.i(TAG, "save data");
                int length = bArr.length;
                this.mSp.putInt(tdsTrackerConfig.topic + SUFFIX_TOPIC_DATA_SIZE_PREFERENCE, length);
                this.mSp.putInt(tdsTrackerConfig.topic + SUFFIX_TOPIC_COUNT_PREFERENCE, iIntValue);
                stringSet.add(tdsTrackerConfig.cachePath);
                FileUtil.createFile(Lz4Util.compressedByte(bArr), str);
            }
            this.mSp.putStringSet(TOPIC_PREFERENCE, stringSet);
            Log.i(TAG, "-------saveUnSendTrackMessageToFile-------end:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
        }
    }

    private TwoTuple<Integer, byte[]> getUnSendByteData(String str) {
        byte[] bArrMarshall;
        Log.i(TAG, "-------getUnSendByteData-------start");
        CopyOnWriteArrayList<TrackMessage> copyOnWriteArrayList = this.messageListMap.get(str);
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() <= 0) {
            bArrMarshall = null;
        } else {
            try {
                bArrMarshall = ParcelableUtil.marshall(new TrackMessageList(copyOnWriteArrayList));
            } catch (Exception e) {
                e.printStackTrace();
                bArrMarshall = null;
            }
        }
        int size = copyOnWriteArrayList != null ? copyOnWriteArrayList.size() : 0;
        Log.i(TAG, "-------getUnSendByteData-------end");
        return new TwoTuple<>(Integer.valueOf(size), bArrMarshall);
    }

    public void doUploadLog(TrackMessage trackMessage) {
        CopyOnWriteArrayList<TrackMessage> copyOnWriteArrayList;
        Log.i(TAG, "-------doUploadLog(TrackMessage trackMessage)-------start");
        TdsTrackerConfig tdsTrackerConfig = trackMessage.tdsTrackerConfig;
        if (this.messageListMap.containsKey(tdsTrackerConfig.topic)) {
            copyOnWriteArrayList = this.messageListMap.get(tdsTrackerConfig.topic);
        } else {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.messageListMap.put(tdsTrackerConfig.topic, copyOnWriteArrayList);
        }
        if (copyOnWriteArrayList == null || checkAbnormal(copyOnWriteArrayList)) {
            return;
        }
        copyOnWriteArrayList.add(trackMessage);
        RxBus.getInstance().send(new TwoTuple(tdsTrackerConfig.topic, String.valueOf(copyOnWriteArrayList.size())));
        if (checkSPState()) {
            this.mSp.putInt(tdsTrackerConfig.topic + SUFFIX_TOPIC_COUNT_PREFERENCE, copyOnWriteArrayList.size());
        }
        while (copyOnWriteArrayList.size() >= tdsTrackerConfig.groupSize) {
            List<TrackMessage> listSubList = copyOnWriteArrayList.subList(0, tdsTrackerConfig.groupSize);
            if (!sendTrackData(listSubList)) {
                break;
            }
            copyOnWriteArrayList.removeAll(listSubList);
            saveUnSendTrackMessageToFile(tdsTrackerConfig);
            RxBus.getInstance().send(new TwoTuple(tdsTrackerConfig.topic, String.valueOf(copyOnWriteArrayList.size())));
        }
        if (checkShouldUpload()) {
            sendLogMessageDelayed();
        }
        Log.i(TAG, "-------doUploadLog(TrackMessage trackMessage)-------end");
    }

    private boolean sendTrackData(List<TrackMessage> list) {
        preHandleCommonParams(list);
        TdsTrackerConfig tdsTrackerConfig = list.get(0).tdsTrackerConfig;
        try {
            byte[] bArrMakeProtoBufferData = makeProtoBufferData(list);
            byte[] bArrCompressedByte = Lz4Util.compressedByte(bArrMakeProtoBufferData);
            HashMap map = new HashMap();
            String upperCase = CommonUtils.getMD5(bArrCompressedByte).toUpperCase();
            String unixTimestampStr = TimeUtil.getUnixTimestampStr();
            String strSignatureToBase64 = "";
            try {
                strSignatureToBase64 = signatureToBase64(String.format("POST\n%s\napplication/x-protobuf\nx-log-apiversion:0.6.0\nx-log-bodyrawsize:%d\nx-log-compresstype:lz4\nx-log-signaturemethod:hmac-sha1\nx-log-timestamp:%s\n/putrecords/%s/%s", upperCase, Integer.valueOf(bArrMakeProtoBufferData.length), unixTimestampStr, tdsTrackerConfig.project, tdsTrackerConfig.logStore), tdsTrackerConfig.accessKeySecret);
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            String str = String.format("LOG %s:%s", tdsTrackerConfig.accessKeyId, strSignatureToBase64);
            map.put("x-log-timestamp", unixTimestampStr);
            map.put("Content-MD5", upperCase);
            map.put("Content-Length", String.valueOf(bArrCompressedByte.length));
            map.put("x-log-bodyrawsize", String.valueOf(bArrMakeProtoBufferData.length));
            map.put(g.v, str);
            map.put("Content-Type", "application/x-protobuf");
            map.put("x-log-apiversion", "0.6.0");
            map.put("x-log-compresstype", "lz4");
            map.put("x-log-signaturemethod", "hmac-sha1");
            map.put("Host", tdsTrackerConfig.endPoint);
            map.put("User-Agent", PlatformXUA.getInstance().getTrackUA());
            map.put("accept", "*/*");
            map.put(g.u, "identity");
            HashMap map2 = null;
            if (!TextUtils.isEmpty(tdsTrackerConfig.clientId)) {
                map2 = new HashMap();
                map2.put(CommonParam.CLIENT_ID, tdsTrackerConfig.clientId);
            } else if (TapCommon.getTapConfig() != null && !TextUtils.isEmpty(TapCommon.getTapConfig().clientId)) {
                map2 = new HashMap();
                map2.put(CommonParam.CLIENT_ID, TapCommon.getTapConfig().clientId);
            }
            TdsApiClient tdsApiClient = this.tdsApiClient;
            StringBuilder sb = new StringBuilder();
            sb.append("https://");
            sb.append(tdsTrackerConfig.endPoint);
            sb.append("/putrecords/");
            sb.append(tdsTrackerConfig.project);
            sb.append("/");
            sb.append(tdsTrackerConfig.logStore);
            return tdsApiClient.postProtoBuff(sb.toString(), map2, map, bArrCompressedByte) < 500;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private void preHandleCommonParams(List<TrackMessage> list) {
        if (list == null) {
            return;
        }
        for (TrackMessage trackMessage : list) {
            if (trackMessage != null && trackMessage.tdsTrackerConfig != null) {
                String str = trackMessage.tdsTrackerConfig.logStore;
                if (!TextUtils.isEmpty(str) && str.equals("tapsdk")) {
                    Map<String, String> map = trackMessage.logCommonParams;
                    String[] strArr = {CommonParam.SDK_VERSION, CommonParam.SDK_VERSION_NAME, CommonParam.SR};
                    for (int i = 0; i < 3; i++) {
                        map.remove(strArr[i]);
                    }
                }
            }
        }
    }

    private void doUploadLog() {
        Log.i(TAG, "-------doUploadLog()-------start");
        for (Map.Entry<String, CopyOnWriteArrayList<TrackMessage>> entry : this.messageListMap.entrySet()) {
            if (entry.getValue() != null) {
                CopyOnWriteArrayList<TrackMessage> value = entry.getValue();
                while (value.size() > 0) {
                    TdsTrackerConfig tdsTrackerConfig = value.get(0).tdsTrackerConfig;
                    int iMin = Math.min(value.size(), tdsTrackerConfig.groupSize);
                    try {
                        if (checkAbnormal(value)) {
                            return;
                        }
                        List<TrackMessage> listSubList = value.subList(0, iMin);
                        if (sendTrackData(listSubList)) {
                            value.removeAll(listSubList);
                            saveUnSendTrackMessageToFile(tdsTrackerConfig);
                            RxBus.getInstance().send(new TwoTuple(tdsTrackerConfig.topic, String.valueOf(value.size())));
                        } else {
                            sendLogMessageDelayed();
                            return;
                        }
                    } catch (Exception e) {
                        sendLogMessageDelayed();
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
        Log.i(TAG, "-------doUploadLog()-------end");
    }

    private String signatureToBase64(String str, String str2) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(str2.getBytes(), "HmacSHA1"));
        return new String(Base64.encode(mac.doFinal(str.getBytes(StandardCharsets.UTF_8)), 2), StandardCharsets.UTF_8);
    }

    private byte[] makeProtoBufferData(List<TrackMessage> list) {
        return makeLogGroup(list).toByteArray();
    }

    private LogBean.LogGroup makeLogGroup(List<TrackMessage> list) {
        LogBean.LogGroup.Builder topic = LogBean.LogGroup.newBuilder().setTopic(list.get(0).tdsTrackerConfig.topic);
        topic.addAllLogs(makeLogs(list));
        return topic.build();
    }

    private List<LogBean.Log> makeLogs(List<TrackMessage> list) {
        ArrayList arrayList = new ArrayList();
        for (TrackMessage trackMessage : list) {
            Log.d("trackerData", "contents:" + trackMessage.logContentsMap + "\n common params" + trackMessage.logCommonParams + "\n");
            arrayList.add(LogBean.Log.newBuilder().setTime((int) trackMessage.createTime).addAllContents(makeLogContents(trackMessage.logContentsMap, trackMessage.logCommonParams)).build());
        }
        return arrayList;
    }

    private List<LogBean.LogContent> makeLogContents(Map<String, String> map, Map<String, String> map2) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayList.add(LogBean.LogContent.newBuilder().setKey(entry.getKey()).setValue(entry.getValue()).build());
        }
        for (Map.Entry<String, String> entry2 : map2.entrySet()) {
            arrayList.add(LogBean.LogContent.newBuilder().setKey(entry2.getKey()).setValue(entry2.getValue()).build());
        }
        return arrayList;
    }
}
