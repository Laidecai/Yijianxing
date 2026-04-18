package com.tds.common.utils;

/* JADX INFO: loaded from: classes.dex */
public class EngineUtil {
    public static boolean isUnity() {
        try {
            Class.forName("com.unity3d.player.UnityPlayerActivity");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static boolean isUnreal() {
        try {
            try {
                Class.forName("com.epicgames.ue4.GameActivity");
                return true;
            } catch (ClassNotFoundException unused) {
                return false;
            }
        } catch (ClassNotFoundException unused2) {
            Class.forName("com.epicgames.unreal.GameActivity");
            return true;
        }
    }
}
