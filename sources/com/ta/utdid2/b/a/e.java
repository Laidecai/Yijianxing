package com.ta.utdid2.b.a;

import android.util.Xml;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.UByte;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes.dex */
public class e {
    public static final void a(Map map, OutputStream outputStream) throws XmlPullParserException, IOException {
        a aVar = new a();
        aVar.setOutput(outputStream, "utf-8");
        aVar.startDocument(null, true);
        aVar.setFeature(a.d(), true);
        a(map, (String) null, (XmlSerializer) aVar);
        aVar.endDocument();
    }

    public static Object b(XmlPullParser xmlPullParser, String[] strArr) throws XmlPullParserException, IOException {
        int next;
        Object objValueOf = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, com.alipay.sdk.cons.c.e);
        String name = xmlPullParser.getName();
        if (!name.equals("null")) {
            if (name.equals("string")) {
                String str = "";
                while (true) {
                    int next2 = xmlPullParser.next();
                    if (next2 == 1) {
                        throw new XmlPullParserException("Unexpected end of document in <string>");
                    }
                    if (next2 == 3) {
                        if (xmlPullParser.getName().equals("string")) {
                            strArr[0] = attributeValue;
                            return str;
                        }
                        throw new XmlPullParserException("Unexpected end tag in <string>: " + xmlPullParser.getName());
                    }
                    if (next2 == 4) {
                        str = str + xmlPullParser.getText();
                    } else if (next2 == 2) {
                        throw new XmlPullParserException("Unexpected start tag in <string>: " + xmlPullParser.getName());
                    }
                }
            } else if (name.equals("int")) {
                objValueOf = Integer.valueOf(Integer.parseInt(xmlPullParser.getAttributeValue(null, "value")));
            } else if (name.equals("long")) {
                objValueOf = Long.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            } else if (name.equals("float")) {
                objValueOf = Float.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            } else if (name.equals("double")) {
                objValueOf = Double.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            } else {
                if (!name.equals("boolean")) {
                    if (name.equals("int-array")) {
                        xmlPullParser.next();
                        int[] iArrM12a = m12a(xmlPullParser, "int-array", strArr);
                        strArr[0] = attributeValue;
                        return iArrM12a;
                    }
                    if (name.equals("map")) {
                        xmlPullParser.next();
                        HashMap mapM11a = m11a(xmlPullParser, "map", strArr);
                        strArr[0] = attributeValue;
                        return mapM11a;
                    }
                    if (name.equals("list")) {
                        xmlPullParser.next();
                        ArrayList arrayListA = a(xmlPullParser, "list", strArr);
                        strArr[0] = attributeValue;
                        return arrayListA;
                    }
                    throw new XmlPullParserException("Unknown tag: " + name);
                }
                objValueOf = Boolean.valueOf(xmlPullParser.getAttributeValue(null, "value"));
            }
        }
        do {
            next = xmlPullParser.next();
            if (next == 1) {
                throw new XmlPullParserException("Unexpected end of document in <" + name + ">");
            }
            if (next == 3) {
                if (xmlPullParser.getName().equals(name)) {
                    strArr[0] = attributeValue;
                    return objValueOf;
                }
                throw new XmlPullParserException("Unexpected end tag in <" + name + ">: " + xmlPullParser.getName());
            }
            if (next == 4) {
                throw new XmlPullParserException("Unexpected text in <" + name + ">: " + xmlPullParser.getName());
            }
        } while (next != 2);
        throw new XmlPullParserException("Unexpected start tag in <" + name + ">: " + xmlPullParser.getName());
    }

    public static final void a(Map map, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (map == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "map");
        if (str != null) {
            xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
        }
        for (Map.Entry entry : map.entrySet()) {
            a(entry.getValue(), (String) entry.getKey(), xmlSerializer);
        }
        xmlSerializer.endTag(null, "map");
    }

    public static final void a(List list, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (list == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "list");
        if (str != null) {
            xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a(list.get(i), (String) null, xmlSerializer);
        }
        xmlSerializer.endTag(null, "list");
    }

    public static final void a(byte[] bArr, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (bArr == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "byte-array");
        if (str != null) {
            xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
        }
        xmlSerializer.attribute(null, "num", Integer.toString(bArr.length));
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            int i = b >> 4;
            sb.append(i >= 10 ? (i + 97) - 10 : i + 48);
            int i2 = b & UByte.MAX_VALUE;
            sb.append(i2 >= 10 ? (i2 + 97) - 10 : i2 + 48);
        }
        xmlSerializer.text(sb.toString());
        xmlSerializer.endTag(null, "byte-array");
    }

    public static final void a(int[] iArr, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (iArr == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "int-array");
        if (str != null) {
            xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
        }
        xmlSerializer.attribute(null, "num", Integer.toString(iArr.length));
        for (int i : iArr) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Integer.toString(i));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "int-array");
    }

    public static final void a(Object obj, String str, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        String str2;
        if (obj == null) {
            xmlSerializer.startTag(null, "null");
            if (str != null) {
                xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
            }
            xmlSerializer.endTag(null, "null");
            return;
        }
        if (obj instanceof String) {
            xmlSerializer.startTag(null, "string");
            if (str != null) {
                xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
            }
            xmlSerializer.text(obj.toString());
            xmlSerializer.endTag(null, "string");
            return;
        }
        if (obj instanceof Integer) {
            str2 = "int";
        } else if (obj instanceof Long) {
            str2 = "long";
        } else if (obj instanceof Float) {
            str2 = "float";
        } else if (obj instanceof Double) {
            str2 = "double";
        } else {
            if (!(obj instanceof Boolean)) {
                if (obj instanceof byte[]) {
                    a((byte[]) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof int[]) {
                    a((int[]) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof Map) {
                    a((Map) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof List) {
                    a((List) obj, str, xmlSerializer);
                    return;
                }
                if (obj instanceof CharSequence) {
                    xmlSerializer.startTag(null, "string");
                    if (str != null) {
                        xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
                    }
                    xmlSerializer.text(obj.toString());
                    xmlSerializer.endTag(null, "string");
                    return;
                }
                throw new RuntimeException("writeValueXml: unable to write value " + obj);
            }
            str2 = "boolean";
        }
        xmlSerializer.startTag(null, str2);
        if (str != null) {
            xmlSerializer.attribute(null, com.alipay.sdk.cons.c.e, str);
        }
        xmlSerializer.attribute(null, "value", obj.toString());
        xmlSerializer.endTag(null, str2);
    }

    public static final HashMap a(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, null);
        return (HashMap) a(xmlPullParserNewPullParser, new String[1]);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static final HashMap m11a(XmlPullParser xmlPullParser, String str, String[] strArr) throws XmlPullParserException, IOException {
        HashMap map = new HashMap();
        int eventType = xmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                Object objB = b(xmlPullParser, strArr);
                if (strArr[0] != null) {
                    map.put(strArr[0], objB);
                } else {
                    throw new XmlPullParserException("Map value without name attribute: " + xmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return map;
                }
                throw new XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName());
            }
            eventType = xmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final ArrayList a(XmlPullParser xmlPullParser, String str, String[] strArr) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        int eventType = xmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                arrayList.add(b(xmlPullParser, strArr));
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return arrayList;
                }
                throw new XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName());
            }
            eventType = xmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException("Document ended before " + str + " end tag");
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static final int[] m12a(XmlPullParser xmlPullParser, String str, String[] strArr) throws XmlPullParserException, IOException {
        try {
            int[] iArr = new int[Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"))];
            int i = 0;
            int eventType = xmlPullParser.getEventType();
            do {
                if (eventType == 2) {
                    if (xmlPullParser.getName().equals("item")) {
                        try {
                            iArr[i] = Integer.parseInt(xmlPullParser.getAttributeValue(null, "value"));
                        } catch (NullPointerException unused) {
                            throw new XmlPullParserException("Need value attribute in item");
                        } catch (NumberFormatException unused2) {
                            throw new XmlPullParserException("Not a number in value attribute in item");
                        }
                    } else {
                        throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                    }
                } else if (eventType == 3) {
                    if (xmlPullParser.getName().equals(str)) {
                        return iArr;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName());
                    }
                    i++;
                }
                eventType = xmlPullParser.next();
            } while (eventType != 1);
            throw new XmlPullParserException("Document ended before " + str + " end tag");
        } catch (NullPointerException unused3) {
            throw new XmlPullParserException("Need num attribute in byte-array");
        } catch (NumberFormatException unused4) {
            throw new XmlPullParserException("Not a number in num attribute in byte-array");
        }
    }

    public static final Object a(XmlPullParser xmlPullParser, String[] strArr) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        while (eventType != 2) {
            if (eventType == 3) {
                throw new XmlPullParserException("Unexpected end tag at: " + xmlPullParser.getName());
            }
            if (eventType != 4) {
                try {
                    eventType = xmlPullParser.next();
                    if (eventType == 1) {
                        throw new XmlPullParserException("Unexpected end of document");
                    }
                } catch (Exception unused) {
                    throw new XmlPullParserException("Unexpected call next(): " + xmlPullParser.getName());
                }
            } else {
                throw new XmlPullParserException("Unexpected text: " + xmlPullParser.getText());
            }
        }
        return b(xmlPullParser, strArr);
    }
}
