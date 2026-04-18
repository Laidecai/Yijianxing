package com.tds.common.permission;

/* JADX INFO: loaded from: classes.dex */
public class PermissionConfig {
    public String intentCancelText;
    public String intentConfirmText;
    public String intentReason;
    public String intentTitle;
    public String tipCancelText;
    public String tipConfirmText;
    public String tipReason;
    public String tipTitle;

    public PermissionConfig(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this(str, str2, str6, str3, str4, str5, str6, str7);
    }

    public PermissionConfig(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.intentTitle = str;
        this.intentReason = str2;
        this.intentCancelText = str3;
        this.intentConfirmText = str4;
        this.tipTitle = str5;
        this.tipReason = str6;
        this.tipCancelText = str7;
        this.tipConfirmText = str8;
    }
}
