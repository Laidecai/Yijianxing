package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.IWebRequestListener;
import com.unity3d.splash.services.core.request.WebRequest;
import com.unity3d.splash.services.core.request.WebRequestError;
import com.unity3d.splash.services.core.request.WebRequestEvent;
import com.unity3d.splash.services.core.request.WebRequestThread;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class Request {

    /* JADX INFO: renamed from: com.unity3d.splash.services.core.api.Request$1 */
    static class AnonymousClass1 implements IWebRequestListener {
        final /* synthetic */ String val$id;

        AnonymousClass1(String str) {
            str = str;
        }

        @Override // com.unity3d.splash.services.core.request.IWebRequestListener
        public final void onComplete(String str, String str2, int i, Map map) {
            try {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, str, str, str2, Integer.valueOf(i), Request.getResponseHeadersMap(map));
            } catch (Exception e) {
                DeviceLog.exception("Error parsing response headers", e);
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str, "Error parsing response headers");
            }
        }

        @Override // com.unity3d.splash.services.core.request.IWebRequestListener
        public final void onFailed(String str, String str2) {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str, str2);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.core.api.Request$2 */
    static class AnonymousClass2 implements IWebRequestListener {
        final /* synthetic */ String val$id;

        AnonymousClass2(String str) {
            str = str;
        }

        @Override // com.unity3d.splash.services.core.request.IWebRequestListener
        public final void onComplete(String str, String str2, int i, Map map) {
            try {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, str, str, str2, Integer.valueOf(i), Request.getResponseHeadersMap(map));
            } catch (Exception e) {
                DeviceLog.exception("Error parsing response headers", e);
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str, "Error parsing response headers");
            }
        }

        @Override // com.unity3d.splash.services.core.request.IWebRequestListener
        public final void onFailed(String str, String str2) {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str, str2);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.core.api.Request$3 */
    static class AnonymousClass3 implements IWebRequestListener {
        final /* synthetic */ String val$id;

        AnonymousClass3(String str) {
            str = str;
        }

        @Override // com.unity3d.splash.services.core.request.IWebRequestListener
        public final void onComplete(String str, String str2, int i, Map map) {
            try {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, str, str, str2, Integer.valueOf(i), Request.getResponseHeadersMap(map));
            } catch (Exception e) {
                DeviceLog.exception("Error parsing response headers", e);
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str, "Error parsing response headers");
            }
        }

        @Override // com.unity3d.splash.services.core.request.IWebRequestListener
        public final void onFailed(String str, String str2) {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str, str2);
        }
    }

    @WebViewExposed
    public static void get(String str, String str2, JSONArray jSONArray, Integer num, Integer num2, WebViewCallback webViewCallback) {
        if (jSONArray != null && jSONArray.length() == 0) {
            jSONArray = null;
        }
        try {
            WebRequestThread.request(str2, WebRequest.RequestType.GET, getHeadersMap(jSONArray), null, num, num2, new IWebRequestListener() { // from class: com.unity3d.splash.services.core.api.Request.1
                final /* synthetic */ String val$id;

                AnonymousClass1(String str3) {
                    str = str3;
                }

                @Override // com.unity3d.splash.services.core.request.IWebRequestListener
                public final void onComplete(String str3, String str22, int i, Map map) {
                    try {
                        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, str, str3, str22, Integer.valueOf(i), Request.getResponseHeadersMap(map));
                    } catch (Exception e) {
                        DeviceLog.exception("Error parsing response headers", e);
                        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str3, "Error parsing response headers");
                    }
                }

                @Override // com.unity3d.splash.services.core.request.IWebRequestListener
                public final void onFailed(String str3, String str22) {
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str3, str22);
                }
            });
            webViewCallback.invoke(str3);
        } catch (Exception e) {
            DeviceLog.exception("Error mapping headers for the request", e);
            webViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, str3);
        }
    }

    public static HashMap getHeadersMap(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        HashMap map = new HashMap();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray2 = (JSONArray) jSONArray.get(i);
            List arrayList = (List) map.get(jSONArray2.getString(0));
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.add(jSONArray2.getString(1));
            map.put(jSONArray2.getString(0), arrayList);
        }
        return map;
    }

    public static JSONArray getResponseHeadersMap(Map map) {
        JSONArray jSONArray = new JSONArray();
        if (map != null && map.size() > 0) {
            for (String str : map.keySet()) {
                JSONArray jSONArray2 = null;
                for (String str2 : (List) map.get(str)) {
                    JSONArray jSONArray3 = new JSONArray();
                    jSONArray3.put(str);
                    jSONArray3.put(str2);
                    jSONArray2 = jSONArray3;
                }
                jSONArray.put(jSONArray2);
            }
        }
        return jSONArray;
    }

    @WebViewExposed
    public static void head(String str, String str2, JSONArray jSONArray, Integer num, Integer num2, WebViewCallback webViewCallback) {
        if (jSONArray != null && jSONArray.length() == 0) {
            jSONArray = null;
        }
        try {
            WebRequestThread.request(str2, WebRequest.RequestType.HEAD, getHeadersMap(jSONArray), num, num2, new IWebRequestListener() { // from class: com.unity3d.splash.services.core.api.Request.3
                final /* synthetic */ String val$id;

                AnonymousClass3(String str3) {
                    str = str3;
                }

                @Override // com.unity3d.splash.services.core.request.IWebRequestListener
                public final void onComplete(String str3, String str22, int i, Map map) {
                    try {
                        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, str, str3, str22, Integer.valueOf(i), Request.getResponseHeadersMap(map));
                    } catch (Exception e) {
                        DeviceLog.exception("Error parsing response headers", e);
                        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str3, "Error parsing response headers");
                    }
                }

                @Override // com.unity3d.splash.services.core.request.IWebRequestListener
                public final void onFailed(String str3, String str22) {
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str3, str22);
                }
            });
            webViewCallback.invoke(str3);
        } catch (Exception e) {
            DeviceLog.exception("Error mapping headers for the request", e);
            webViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, str3);
        }
    }

    @WebViewExposed
    public static void post(String str, String str2, String str3, JSONArray jSONArray, Integer num, Integer num2, WebViewCallback webViewCallback) {
        String str4 = (str3 == null || str3.length() != 0) ? str3 : null;
        if (jSONArray != null && jSONArray.length() == 0) {
            jSONArray = null;
        }
        try {
            WebRequestThread.request(str2, WebRequest.RequestType.POST, getHeadersMap(jSONArray), str4, num, num2, new IWebRequestListener() { // from class: com.unity3d.splash.services.core.api.Request.2
                final /* synthetic */ String val$id;

                AnonymousClass2(String str5) {
                    str = str5;
                }

                @Override // com.unity3d.splash.services.core.request.IWebRequestListener
                public final void onComplete(String str5, String str22, int i, Map map) {
                    try {
                        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, str, str5, str22, Integer.valueOf(i), Request.getResponseHeadersMap(map));
                    } catch (Exception e) {
                        DeviceLog.exception("Error parsing response headers", e);
                        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str5, "Error parsing response headers");
                    }
                }

                @Override // com.unity3d.splash.services.core.request.IWebRequestListener
                public final void onFailed(String str5, String str22) {
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, str, str5, str22);
                }
            });
            webViewCallback.invoke(str5);
        } catch (Exception e) {
            DeviceLog.exception("Error mapping headers for the request", e);
            webViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, str5);
        }
    }

    @WebViewExposed
    public static void setConcurrentRequestCount(Integer num, WebViewCallback webViewCallback) {
        WebRequestThread.setConcurrentRequestCount(num.intValue());
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setKeepAliveTime(Integer num, WebViewCallback webViewCallback) {
        WebRequestThread.setKeepAliveTime(num.longValue());
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setMaximumPoolSize(Integer num, WebViewCallback webViewCallback) {
        WebRequestThread.setMaximumPoolSize(num.intValue());
        webViewCallback.invoke(new Object[0]);
    }
}
