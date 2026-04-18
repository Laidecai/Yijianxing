package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.billingclient.BuildConfig;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
class BillingClientImpl extends BillingClient {
    private int zza;
    private final String zzb;
    private final Handler zzc;
    private zze zzd;
    private Context zze;
    private Context zzf;
    private com.google.android.gms.internal.play_billing.zzd zzg;
    private zzah zzh;
    private boolean zzi;
    private boolean zzj;
    private int zzk;
    private boolean zzl;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private boolean zzp;
    private boolean zzq;
    private boolean zzr;
    private boolean zzs;
    private boolean zzt;
    private ExecutorService zzu;

    private BillingClientImpl(Activity activity, boolean z, String str) {
        this(activity.getApplicationContext(), z, new zzaj(), str, null);
    }

    private void initialize(Context context, PurchasesUpdatedListener purchasesUpdatedListener, boolean z) {
        Context applicationContext = context.getApplicationContext();
        this.zzf = applicationContext;
        this.zzd = new zze(applicationContext, purchasesUpdatedListener);
        this.zze = context;
        this.zzt = z;
    }

    private int launchBillingFlowCpp(Activity activity, BillingFlowParams billingFlowParams) {
        return launchBillingFlow(activity, billingFlowParams).getResponseCode();
    }

    private void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, long j) {
        launchPriceChangeConfirmationFlow(activity, priceChangeFlowParams, new zzaj(j));
    }

    private void startConnection(long j) {
        zzaj zzajVar = new zzaj(j);
        if (isReady()) {
            com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Service connection is valid. No need to re-initialize.");
            zzajVar.onBillingSetupFinished(zzam.zzp);
            return;
        }
        int i = this.zza;
        if (i == 1) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Client is already in the process of connecting to billing service.");
            zzajVar.onBillingSetupFinished(zzam.zzd);
            return;
        }
        if (i == 3) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            zzajVar.onBillingSetupFinished(zzam.zzq);
            return;
        }
        this.zza = 1;
        this.zzd.zza();
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Starting in-app billing setup.");
        this.zzh = new zzah(this, zzajVar, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> listQueryIntentServices = this.zzf.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty()) {
            ResolveInfo resolveInfo = listQueryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                String str2 = resolveInfo.serviceInfo.name;
                if (!"com.android.vending".equals(str) || str2 == null) {
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "The device doesn't have valid Play Store.");
                } else {
                    ComponentName componentName = new ComponentName(str, str2);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    intent2.putExtra("playBillingLibraryVersion", this.zzb);
                    if (this.zzf.bindService(intent2, this.zzh, 1)) {
                        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Service was bonded successfully.");
                        return;
                    }
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Connection to Billing service is blocked.");
                }
            }
        }
        this.zza = 0;
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Billing service unavailable on device.");
        zzajVar.onBillingSetupFinished(zzam.zzc);
    }

    private final BillingResult zzA(String str) {
        try {
            return ((Integer) zzz(new zzt(this, str), 5000L, null).get(5000L, TimeUnit.MILLISECONDS)).intValue() == 0 ? zzam.zzp : zzam.zzi;
        } catch (Exception unused) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Exception while checking if billing is supported; try to reconnect");
            return zzam.zzq;
        }
    }

    public final void zzB(Runnable runnable) {
        if (Thread.interrupted()) {
            return;
        }
        this.zzc.post(runnable);
    }

    static /* synthetic */ Purchase.PurchasesResult zzd(BillingClientImpl billingClientImpl, String str) {
        String strValueOf = String.valueOf(str);
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf.length() != 0 ? "Querying owned items, item type: ".concat(strValueOf) : new String("Querying owned items, item type: "));
        ArrayList arrayList = new ArrayList();
        Bundle bundleZzh = com.google.android.gms.internal.play_billing.zza.zzh(billingClientImpl.zzn, billingClientImpl.zzt, billingClientImpl.zzb);
        String string = null;
        do {
            try {
                Bundle bundleZzk = billingClientImpl.zzn ? billingClientImpl.zzg.zzk(9, billingClientImpl.zzf.getPackageName(), str, string, bundleZzh) : billingClientImpl.zzg.zzd(3, billingClientImpl.zzf.getPackageName(), str, string);
                BillingResult billingResultZza = zzao.zza(bundleZzk, "BillingClient", "getPurchase()");
                if (billingResultZza != zzam.zzp) {
                    return new Purchase.PurchasesResult(billingResultZza, null);
                }
                ArrayList<String> stringArrayList = bundleZzk.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = bundleZzk.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = bundleZzk.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str2 = stringArrayList2.get(i);
                    String str3 = stringArrayList3.get(i);
                    String strValueOf2 = String.valueOf(stringArrayList.get(i));
                    com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf2.length() != 0 ? "Sku is owned: ".concat(strValueOf2) : new String("Sku is owned: "));
                    try {
                        Purchase purchase = new Purchase(str2, str3);
                        if (TextUtils.isEmpty(purchase.getPurchaseToken())) {
                            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchase);
                    } catch (JSONException e) {
                        String strValueOf3 = String.valueOf(e);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf3).length() + 48);
                        sb.append("Got an exception trying to decode the purchase: ");
                        sb.append(strValueOf3);
                        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
                        return new Purchase.PurchasesResult(zzam.zzl, null);
                    }
                }
                string = bundleZzk.getString("INAPP_CONTINUATION_TOKEN");
                String strValueOf4 = String.valueOf(string);
                com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf4.length() != 0 ? "Continuation token: ".concat(strValueOf4) : new String("Continuation token: "));
            } catch (Exception e2) {
                String strValueOf5 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf5).length() + 57);
                sb2.append("Got exception trying to get purchases: ");
                sb2.append(strValueOf5);
                sb2.append("; try to reconnect");
                com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb2.toString());
                return new Purchase.PurchasesResult(zzam.zzq, null);
            }
        } while (!TextUtils.isEmpty(string));
        return new Purchase.PurchasesResult(zzam.zzp, arrayList);
    }

    static /* synthetic */ void zzf(BillingClientImpl billingClientImpl, ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) {
        int iZze;
        String strZze;
        String purchaseToken = consumeParams.getPurchaseToken();
        try {
            String strValueOf = String.valueOf(purchaseToken);
            com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf.length() != 0 ? "Consuming purchase with token: ".concat(strValueOf) : new String("Consuming purchase with token: "));
            if (billingClientImpl.zzn) {
                Bundle bundleZzl = billingClientImpl.zzg.zzl(9, billingClientImpl.zzf.getPackageName(), purchaseToken, com.google.android.gms.internal.play_billing.zza.zzj(consumeParams, billingClientImpl.zzn, billingClientImpl.zzb));
                int i = bundleZzl.getInt("RESPONSE_CODE");
                strZze = com.google.android.gms.internal.play_billing.zza.zze(bundleZzl, "BillingClient");
                iZze = i;
            } else {
                iZze = billingClientImpl.zzg.zze(3, billingClientImpl.zzf.getPackageName(), purchaseToken);
                strZze = "";
            }
            BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
            builderNewBuilder.setResponseCode(iZze);
            builderNewBuilder.setDebugMessage(strZze);
            BillingResult billingResultBuild = builderNewBuilder.build();
            if (iZze == 0) {
                billingClientImpl.zzB(new zzu(billingClientImpl, consumeResponseListener, billingResultBuild, purchaseToken));
            } else {
                billingClientImpl.zzB(new zzv(billingClientImpl, iZze, consumeResponseListener, billingResultBuild, purchaseToken));
            }
        } catch (Exception e) {
            billingClientImpl.zzB(new zzw(billingClientImpl, e, consumeResponseListener, purchaseToken));
        }
    }

    static /* synthetic */ zzai zzg(BillingClientImpl billingClientImpl, String str) {
        String strValueOf = String.valueOf(str);
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf.length() != 0 ? "Querying purchase history, item type: ".concat(strValueOf) : new String("Querying purchase history, item type: "));
        ArrayList arrayList = new ArrayList();
        Bundle bundleZzh = com.google.android.gms.internal.play_billing.zza.zzh(billingClientImpl.zzn, billingClientImpl.zzt, billingClientImpl.zzb);
        String string = null;
        while (billingClientImpl.zzl) {
            try {
                Bundle bundleZzh2 = billingClientImpl.zzg.zzh(6, billingClientImpl.zzf.getPackageName(), str, string, bundleZzh);
                BillingResult billingResultZza = zzao.zza(bundleZzh2, "BillingClient", "getPurchaseHistory()");
                if (billingResultZza != zzam.zzp) {
                    return new zzai(billingResultZza, null);
                }
                ArrayList<String> stringArrayList = bundleZzh2.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = bundleZzh2.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = bundleZzh2.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str2 = stringArrayList2.get(i);
                    String str3 = stringArrayList3.get(i);
                    String strValueOf2 = String.valueOf(stringArrayList.get(i));
                    com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf2.length() != 0 ? "Purchase record found for sku : ".concat(strValueOf2) : new String("Purchase record found for sku : "));
                    try {
                        PurchaseHistoryRecord purchaseHistoryRecord = new PurchaseHistoryRecord(str2, str3);
                        if (TextUtils.isEmpty(purchaseHistoryRecord.getPurchaseToken())) {
                            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchaseHistoryRecord);
                    } catch (JSONException e) {
                        String strValueOf3 = String.valueOf(e);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf3).length() + 48);
                        sb.append("Got an exception trying to decode the purchase: ");
                        sb.append(strValueOf3);
                        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
                        return new zzai(zzam.zzl, null);
                    }
                }
                string = bundleZzh2.getString("INAPP_CONTINUATION_TOKEN");
                String strValueOf4 = String.valueOf(string);
                com.google.android.gms.internal.play_billing.zza.zza("BillingClient", strValueOf4.length() != 0 ? "Continuation token: ".concat(strValueOf4) : new String("Continuation token: "));
                if (TextUtils.isEmpty(string)) {
                    return new zzai(zzam.zzp, arrayList);
                }
            } catch (RemoteException e2) {
                String strValueOf5 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf5).length() + 64);
                sb2.append("Got exception trying to get purchase history: ");
                sb2.append(strValueOf5);
                sb2.append("; try to reconnect");
                com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb2.toString());
                return new zzai(zzam.zzq, null);
            }
        }
        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "getPurchaseHistory is not supported on current device");
        return new zzai(zzam.zzj, null);
    }

    private final BillingResult zzy(BillingResult billingResult) {
        this.zzd.zzb().onPurchasesUpdated(billingResult, null);
        return billingResult;
    }

    public final <T> Future<T> zzz(Callable<T> callable, long j, Runnable runnable) {
        long j2 = (long) (j * 0.95d);
        if (this.zzu == null) {
            this.zzu = Executors.newFixedThreadPool(com.google.android.gms.internal.play_billing.zza.zza, new zzq(this));
        }
        try {
            Future<T> futureSubmit = this.zzu.submit(callable);
            this.zzc.postDelayed(new zzr(this, futureSubmit, runnable), j2);
            return futureSubmit;
        } catch (Exception e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 28);
            sb.append("Async task throws exception ");
            sb.append(strValueOf);
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
            return null;
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void acknowledgePurchase(AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        if (!isReady()) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzam.zzq);
            return;
        }
        if (TextUtils.isEmpty(acknowledgePurchaseParams.getPurchaseToken())) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Please provide a valid purchase token.");
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzam.zzk);
        } else if (!this.zzn) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzam.zzb);
        } else if (zzz(new zzo(this, acknowledgePurchaseParams, acknowledgePurchaseResponseListener), 30000L, new zzp(this, acknowledgePurchaseResponseListener)) == null) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzC());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void consumeAsync(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) {
        if (!isReady()) {
            consumeResponseListener.onConsumeResponse(zzam.zzq, consumeParams.getPurchaseToken());
        } else if (zzz(new zzh(this, consumeParams, consumeResponseListener), 30000L, new zzi(this, consumeResponseListener, consumeParams)) == null) {
            consumeResponseListener.onConsumeResponse(zzC(), consumeParams.getPurchaseToken());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void endConnection() {
        try {
            this.zze = null;
            this.zzd.zzc();
            zzah zzahVar = this.zzh;
            if (zzahVar != null) {
                zzahVar.zza();
            }
            if (this.zzh != null && this.zzg != null) {
                com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Unbinding from service.");
                this.zzf.unbindService(this.zzh);
                this.zzh = null;
            }
            this.zzg = null;
            ExecutorService executorService = this.zzu;
            if (executorService != null) {
                executorService.shutdownNow();
                this.zzu = null;
            }
        } catch (Exception e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 48);
            sb.append("There was an exception while ending connection: ");
            sb.append(strValueOf);
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
        } finally {
            this.zza = 3;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0078  */
    @Override // com.android.billingclient.api.BillingClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.billingclient.api.BillingResult isFeatureSupported(java.lang.String r3) {
        /*
            Method dump skipped, instruction units count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.isFeatureSupported(java.lang.String):com.android.billingclient.api.BillingResult");
    }

    @Override // com.android.billingclient.api.BillingClient
    public final boolean isReady() {
        return (this.zza != 2 || this.zzg == null || this.zzh == null) ? false : true;
    }

    @Override // com.android.billingclient.api.BillingClient
    public final BillingResult launchBillingFlow(Activity activity, BillingFlowParams billingFlowParams) {
        String str;
        String str2;
        String str3;
        long j;
        Future futureZzz;
        boolean z;
        int i;
        String strOptString;
        String str4 = "BUY_INTENT";
        if (!isReady()) {
            BillingResult billingResult = zzam.zzq;
            zzy(billingResult);
            return billingResult;
        }
        ArrayList<SkuDetails> arrayListZza = billingFlowParams.zza();
        SkuDetails skuDetails = arrayListZza.get(0);
        String type = skuDetails.getType();
        if (type.equals(BillingClient.SkuType.SUBS) && !this.zzi) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Current client doesn't support subscriptions.");
            BillingResult billingResult2 = zzam.zzs;
            zzy(billingResult2);
            return billingResult2;
        }
        String oldSku = billingFlowParams.getOldSku();
        if (oldSku != null && !this.zzj) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Current client doesn't support subscriptions update.");
            BillingResult billingResult3 = zzam.zzt;
            zzy(billingResult3);
            return billingResult3;
        }
        if (billingFlowParams.zzc() && !this.zzl) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Current client doesn't support extra params for buy intent.");
            BillingResult billingResult4 = zzam.zzh;
            zzy(billingResult4);
            return billingResult4;
        }
        if (arrayListZza.size() > 1 && !this.zzs) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Current client doesn't support multi-item purchases.");
            BillingResult billingResult5 = zzam.zzu;
            zzy(billingResult5);
            return billingResult5;
        }
        String str5 = "";
        String str6 = "";
        int i2 = 0;
        while (i2 < arrayListZza.size()) {
            String strValueOf = String.valueOf(str6);
            String strValueOf2 = String.valueOf(arrayListZza.get(i2));
            String str7 = str5;
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + String.valueOf(strValueOf2).length());
            sb.append(strValueOf);
            sb.append(strValueOf2);
            String string = sb.toString();
            if (i2 < arrayListZza.size() - 1) {
                string = String.valueOf(string).concat(", ");
            }
            str6 = string;
            i2++;
            str5 = str7;
        }
        String str8 = str5;
        StringBuilder sb2 = new StringBuilder(String.valueOf(str6).length() + 41 + String.valueOf(type).length());
        sb2.append("Constructing buy intent for ");
        sb2.append(str6);
        sb2.append(", item type: ");
        sb2.append(type);
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", sb2.toString());
        if (this.zzl) {
            Bundle bundleZzg = com.google.android.gms.internal.play_billing.zza.zzg(billingFlowParams, this.zzn, this.zzt, this.zzb);
            ArrayList<String> arrayList = new ArrayList<>();
            ArrayList<String> arrayList2 = new ArrayList<>();
            ArrayList<String> arrayList3 = new ArrayList<>();
            ArrayList<Integer> arrayList4 = new ArrayList<>();
            str2 = "; try to reconnect";
            int size = arrayListZza.size();
            str3 = str6;
            int i3 = 0;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            while (i3 < size) {
                SkuDetails skuDetails2 = arrayListZza.get(i3);
                if (skuDetails2.zzb().isEmpty()) {
                    i = size;
                } else {
                    i = size;
                    arrayList.add(skuDetails2.zzb());
                }
                String str9 = str4;
                try {
                    strOptString = new JSONObject(skuDetails2.getOriginalJson()).optString("offer_id_token");
                } catch (JSONException unused) {
                    strOptString = str8;
                }
                String strZzc = skuDetails2.zzc();
                int iZzd = skuDetails2.zzd();
                arrayList2.add(strOptString);
                z2 |= !TextUtils.isEmpty(strOptString);
                arrayList3.add(strZzc);
                z3 |= !TextUtils.isEmpty(strZzc);
                arrayList4.add(Integer.valueOf(iZzd));
                z4 |= iZzd != 0;
                i3++;
                size = i;
                str4 = str9;
            }
            str = str4;
            if (!arrayList.isEmpty()) {
                bundleZzg.putStringArrayList("skuDetailsTokens", arrayList);
            }
            if (z2) {
                if (!this.zzq) {
                    BillingResult billingResult6 = zzam.zzi;
                    zzy(billingResult6);
                    return billingResult6;
                }
                bundleZzg.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList2);
            }
            if (z3) {
                bundleZzg.putStringArrayList("SKU_OFFER_ID_LIST", arrayList3);
            }
            if (z4) {
                bundleZzg.putIntegerArrayList("SKU_OFFER_TYPE_LIST", arrayList4);
            }
            if (TextUtils.isEmpty(skuDetails.zza())) {
                z = false;
            } else {
                bundleZzg.putString("skuPackageName", skuDetails.zza());
                z = true;
            }
            if (!TextUtils.isEmpty(null)) {
                bundleZzg.putString("accountName", null);
            }
            if (arrayListZza.size() > 1) {
                ArrayList<String> arrayList5 = new ArrayList<>(arrayListZza.size() - 1);
                ArrayList<String> arrayList6 = new ArrayList<>(arrayListZza.size() - 1);
                for (int i4 = 1; i4 < arrayListZza.size(); i4++) {
                    arrayList5.add(arrayListZza.get(i4).getSku());
                    arrayList6.add(arrayListZza.get(i4).getType());
                }
                bundleZzg.putStringArrayList("additionalSkus", arrayList5);
                bundleZzg.putStringArrayList("additionalSkuTypes", arrayList6);
            }
            if (!TextUtils.isEmpty(activity.getIntent().getStringExtra("PROXY_PACKAGE"))) {
                String stringExtra = activity.getIntent().getStringExtra("PROXY_PACKAGE");
                bundleZzg.putString("proxyPackage", stringExtra);
                try {
                    bundleZzg.putString("proxyPackageVersion", this.zzf.getPackageManager().getPackageInfo(stringExtra, 0).versionName);
                } catch (PackageManager.NameNotFoundException unused2) {
                    bundleZzg.putString("proxyPackageVersion", "package not found");
                }
            }
            j = 5000;
            futureZzz = zzz(new zzy(this, (this.zzr && z) ? 15 : this.zzn ? 9 : billingFlowParams.getVrPurchaseFlow() ? 7 : 6, skuDetails, type, billingFlowParams, bundleZzg), 5000L, null);
        } else {
            str = "BUY_INTENT";
            str2 = "; try to reconnect";
            str3 = str6;
            j = 5000;
            futureZzz = oldSku != null ? zzz(new zzz(this, billingFlowParams, skuDetails), 5000L, null) : zzz(new zzaa(this, skuDetails, type), 5000L, null);
        }
        try {
            Bundle bundle = (Bundle) futureZzz.get(j, TimeUnit.MILLISECONDS);
            int iZzd2 = com.google.android.gms.internal.play_billing.zza.zzd(bundle, "BillingClient");
            String strZze = com.google.android.gms.internal.play_billing.zza.zze(bundle, "BillingClient");
            if (iZzd2 == 0) {
                Intent intent = new Intent(activity, (Class<?>) ProxyBillingActivity.class);
                String str10 = str;
                intent.putExtra(str10, (PendingIntent) bundle.getParcelable(str10));
                activity.startActivity(intent);
                return zzam.zzp;
            }
            StringBuilder sb3 = new StringBuilder(52);
            sb3.append("Unable to buy item, Error response code: ");
            sb3.append(iZzd2);
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb3.toString());
            BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
            builderNewBuilder.setResponseCode(iZzd2);
            builderNewBuilder.setDebugMessage(strZze);
            BillingResult billingResultBuild = builderNewBuilder.build();
            zzy(billingResultBuild);
            return billingResultBuild;
        } catch (CancellationException | TimeoutException unused3) {
            String str11 = str3;
            StringBuilder sb4 = new StringBuilder(String.valueOf(str11).length() + 68);
            sb4.append("Time out while launching billing flow: ; for sku: ");
            sb4.append(str11);
            sb4.append(str2);
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb4.toString());
            BillingResult billingResult7 = zzam.zzr;
            zzy(billingResult7);
            return billingResult7;
        } catch (Exception unused4) {
            StringBuilder sb5 = new StringBuilder(String.valueOf(str3).length() + 69);
            sb5.append("Exception while launching billing flow: ; for sku: ");
            sb5.append(str3);
            sb5.append(str2);
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb5.toString());
            BillingResult billingResult8 = zzam.zzq;
            zzy(billingResult8);
            return billingResult8;
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryPurchaseHistoryAsync(String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        if (!isReady()) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzam.zzq, null);
        } else if (zzz(new zzk(this, str, purchaseHistoryResponseListener), 30000L, new zzl(this, purchaseHistoryResponseListener)) == null) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzC(), null);
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final Purchase.PurchasesResult queryPurchases(String str) {
        if (!isReady()) {
            return new Purchase.PurchasesResult(zzam.zzq, null);
        }
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Please provide a valid SKU type.");
            return new Purchase.PurchasesResult(zzam.zzg, null);
        }
        try {
            return (Purchase.PurchasesResult) zzz(new zzab(this, str), 5000L, null).get(5000L, TimeUnit.MILLISECONDS);
        } catch (CancellationException | TimeoutException unused) {
            return new Purchase.PurchasesResult(zzam.zzr, null);
        } catch (Exception unused2) {
            return new Purchase.PurchasesResult(zzam.zzl, null);
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams, SkuDetailsResponseListener skuDetailsResponseListener) {
        if (!isReady()) {
            skuDetailsResponseListener.onSkuDetailsResponse(zzam.zzq, null);
            return;
        }
        String skuType = skuDetailsParams.getSkuType();
        List<String> skusList = skuDetailsParams.getSkusList();
        if (TextUtils.isEmpty(skuType)) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Please fix the input params. SKU type can't be empty.");
            skuDetailsResponseListener.onSkuDetailsResponse(zzam.zzg, null);
            return;
        }
        if (skusList == null) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Please fix the input params. The list of SKUs can't be empty - set SKU list or SkuWithOffer list.");
            skuDetailsResponseListener.onSkuDetailsResponse(zzam.zzf, null);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : skusList) {
            zzas zzasVar = new zzas(null);
            zzasVar.zza(str);
            arrayList.add(zzasVar.zzb());
        }
        if (zzz(new zzad(this, skuType, arrayList, null, skuDetailsResponseListener), 30000L, new zzg(this, skuDetailsResponseListener)) == null) {
            skuDetailsResponseListener.onSkuDetailsResponse(zzC(), null);
        }
    }

    final zzap zza(String str, List<zzat> list, String str2) {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 20;
            ArrayList arrayList2 = new ArrayList(list.subList(i, i2 > size ? size : i2));
            ArrayList<String> arrayList3 = new ArrayList<>();
            int size2 = arrayList2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                arrayList3.add(((zzat) arrayList2.get(i3)).zza());
            }
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList3);
            bundle.putString("playBillingLibraryVersion", this.zzb);
            try {
                Bundle bundleZzm = this.zzo ? this.zzg.zzm(10, this.zzf.getPackageName(), str, bundle, com.google.android.gms.internal.play_billing.zza.zzi(this.zzk, this.zzt, this.zzb, null, arrayList2)) : this.zzg.zzb(3, this.zzf.getPackageName(), str, bundle);
                if (bundleZzm == null) {
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "querySkuDetailsAsync got null sku details list");
                    return new zzap(4, "Item is unavailable for purchase.", null);
                }
                if (!bundleZzm.containsKey("DETAILS_LIST")) {
                    int iZzd = com.google.android.gms.internal.play_billing.zza.zzd(bundleZzm, "BillingClient");
                    String strZze = com.google.android.gms.internal.play_billing.zza.zze(bundleZzm, "BillingClient");
                    if (iZzd == 0) {
                        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a detail list.");
                        return new zzap(6, strZze, arrayList);
                    }
                    StringBuilder sb = new StringBuilder(50);
                    sb.append("getSkuDetails() failed. Response code: ");
                    sb.append(iZzd);
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
                    return new zzap(iZzd, strZze, arrayList);
                }
                ArrayList<String> stringArrayList = bundleZzm.getStringArrayList("DETAILS_LIST");
                if (stringArrayList == null) {
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "querySkuDetailsAsync got null response list");
                    return new zzap(4, "Item is unavailable for purchase.", null);
                }
                for (int i4 = 0; i4 < stringArrayList.size(); i4++) {
                    try {
                        SkuDetails skuDetails = new SkuDetails(stringArrayList.get(i4));
                        String strValueOf = String.valueOf(skuDetails);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 17);
                        sb2.append("Got sku details: ");
                        sb2.append(strValueOf);
                        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", sb2.toString());
                        arrayList.add(skuDetails);
                    } catch (JSONException unused) {
                        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Got a JSON exception trying to decode SkuDetails.");
                        return new zzap(6, "Error trying to decode SkuDetails.", null);
                    }
                }
                i = i2;
            } catch (Exception e) {
                String strValueOf2 = String.valueOf(e);
                StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf2).length() + 63);
                sb3.append("querySkuDetailsAsync got a remote exception (try to reconnect).");
                sb3.append(strValueOf2);
                com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb3.toString());
                return new zzap(-1, "Service connection is disconnected.", null);
            }
        }
        return new zzap(0, "", arrayList);
    }

    public final BillingResult zzC() {
        int i = this.zza;
        return (i == 0 || i == 3) ? zzam.zzq : zzam.zzl;
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, PriceChangeConfirmationListener priceChangeConfirmationListener) {
        if (!isReady()) {
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(zzam.zzq);
            return;
        }
        if (priceChangeFlowParams == null || priceChangeFlowParams.getSkuDetails() == null) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Please fix the input params. priceChangeFlowParams must contain valid sku.");
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(zzam.zzn);
            return;
        }
        String sku = priceChangeFlowParams.getSkuDetails().getSku();
        if (sku == null) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Please fix the input params. priceChangeFlowParams must contain valid sku.");
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(zzam.zzn);
            return;
        }
        if (!this.zzm) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Current client doesn't support price change confirmation flow.");
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(zzam.zzi);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("playBillingLibraryVersion", this.zzb);
        bundle.putBoolean("subs_price_change", true);
        try {
            Bundle bundle2 = (Bundle) zzz(new zzs(this, sku, bundle), 5000L, null).get(5000L, TimeUnit.MILLISECONDS);
            int iZzd = com.google.android.gms.internal.play_billing.zza.zzd(bundle2, "BillingClient");
            String strZze = com.google.android.gms.internal.play_billing.zza.zze(bundle2, "BillingClient");
            BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
            builderNewBuilder.setResponseCode(iZzd);
            builderNewBuilder.setDebugMessage(strZze);
            BillingResult billingResultBuild = builderNewBuilder.build();
            if (iZzd == 0) {
                zzx zzxVar = new zzx(this, this.zzc, priceChangeConfirmationListener);
                Intent intent = new Intent(activity, (Class<?>) ProxyBillingActivity.class);
                intent.putExtra("SUBS_MANAGEMENT_INTENT", (PendingIntent) bundle2.getParcelable("SUBS_MANAGEMENT_INTENT"));
                intent.putExtra("result_receiver", zzxVar);
                activity.startActivity(intent);
                return;
            }
            StringBuilder sb = new StringBuilder(68);
            sb.append("Unable to launch price change flow, error response code: ");
            sb.append(iZzd);
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(billingResultBuild);
        } catch (CancellationException | TimeoutException unused) {
            StringBuilder sb2 = new StringBuilder(sku.length() + 70);
            sb2.append("Time out while launching Price Change Flow for sku: ");
            sb2.append(sku);
            sb2.append("; try to reconnect");
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb2.toString());
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(zzam.zzr);
        } catch (Exception unused2) {
            StringBuilder sb3 = new StringBuilder(sku.length() + 78);
            sb3.append("Exception caught while launching Price Change Flow for sku: ");
            sb3.append(sku);
            sb3.append("; try to reconnect");
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb3.toString());
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(zzam.zzq);
        }
    }

    private BillingClientImpl(Context context, boolean z, PurchasesUpdatedListener purchasesUpdatedListener, String str, String str2) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = str;
        initialize(context, purchasesUpdatedListener, z);
    }

    private BillingClientImpl(String str) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = str;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    BillingClientImpl(String str, boolean z, Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        String str2;
        try {
            str2 = (String) Class.forName("com.android.billingclient.ktx.BuildConfig").getField("VERSION_NAME").get(null);
        } catch (Exception unused) {
            str2 = BuildConfig.VERSION_NAME;
        }
        this(context, true, purchasesUpdatedListener, str2, null);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void startConnection(BillingClientStateListener billingClientStateListener) {
        if (isReady()) {
            com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Service connection is valid. No need to re-initialize.");
            billingClientStateListener.onBillingSetupFinished(zzam.zzp);
            return;
        }
        int i = this.zza;
        if (i == 1) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Client is already in the process of connecting to billing service.");
            billingClientStateListener.onBillingSetupFinished(zzam.zzd);
            return;
        }
        if (i == 3) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            billingClientStateListener.onBillingSetupFinished(zzam.zzq);
            return;
        }
        this.zza = 1;
        this.zzd.zza();
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Starting in-app billing setup.");
        this.zzh = new zzah(this, billingClientStateListener, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> listQueryIntentServices = this.zzf.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty()) {
            ResolveInfo resolveInfo = listQueryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                String str2 = resolveInfo.serviceInfo.name;
                if (!"com.android.vending".equals(str) || str2 == null) {
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "The device doesn't have valid Play Store.");
                } else {
                    ComponentName componentName = new ComponentName(str, str2);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    intent2.putExtra("playBillingLibraryVersion", this.zzb);
                    if (this.zzf.bindService(intent2, this.zzh, 1)) {
                        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Service was bonded successfully.");
                        return;
                    }
                    com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Connection to Billing service is blocked.");
                }
            }
        }
        this.zza = 0;
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Billing service unavailable on device.");
        billingClientStateListener.onBillingSetupFinished(zzam.zzc);
    }
}
