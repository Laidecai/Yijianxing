package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import com.alipay.sdk.app.OpenAuthTask;
import com.alipay.sdk.util.l;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class PathParser {
    private static final String LOGTAG = "PathParser";

    static float[] copyOfRange(float[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int length = original.length;
        if (start < 0 || start > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i = end - start;
        int iMin = Math.min(i, length - start);
        float[] fArr = new float[i];
        System.arraycopy(original, start, fArr, 0, iMin);
        return fArr;
    }

    public static Path createPathFromPathData(String pathData) {
        Path path = new Path();
        PathDataNode[] pathDataNodeArrCreateNodesFromPathData = createNodesFromPathData(pathData);
        if (pathDataNodeArrCreateNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(pathDataNodeArrCreateNodesFromPathData, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + pathData, e);
        }
    }

    public static PathDataNode[] createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int i2 = 0;
        while (i < pathData.length()) {
            int iNextStart = nextStart(pathData, i);
            String strTrim = pathData.substring(i2, iNextStart).trim();
            if (strTrim.length() > 0) {
                addNode(arrayList, strTrim.charAt(0), getFloats(strTrim));
            }
            i2 = iNextStart;
            i = iNextStart + 1;
        }
        if (i - i2 == 1 && i2 < pathData.length()) {
            addNode(arrayList, pathData.charAt(i2), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] source) {
        if (source == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr = new PathDataNode[source.length];
        for (int i = 0; i < source.length; i++) {
            pathDataNodeArr[i] = new PathDataNode(source[i]);
        }
        return pathDataNodeArr;
    }

    public static boolean canMorph(PathDataNode[] nodesFrom, PathDataNode[] nodesTo) {
        if (nodesFrom == null || nodesTo == null || nodesFrom.length != nodesTo.length) {
            return false;
        }
        for (int i = 0; i < nodesFrom.length; i++) {
            if (nodesFrom[i].mType != nodesTo[i].mType || nodesFrom[i].mParams.length != nodesTo[i].mParams.length) {
                return false;
            }
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] target, PathDataNode[] source) {
        for (int i = 0; i < source.length; i++) {
            target[i].mType = source[i].mType;
            for (int i2 = 0; i2 < source[i].mParams.length; i2++) {
                target[i].mParams[i2] = source[i].mParams[i2];
            }
        }
    }

    private static int nextStart(String s, int end) {
        while (end < s.length()) {
            char cCharAt = s.charAt(end);
            if (((cCharAt - 'A') * (cCharAt - 'Z') <= 0 || (cCharAt - 'a') * (cCharAt - 'z') <= 0) && cCharAt != 'e' && cCharAt != 'E') {
                return end;
            }
            end++;
        }
        return end;
    }

    private static void addNode(ArrayList<PathDataNode> list, char cmd, float[] val) {
        list.add(new PathDataNode(cmd, val));
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    private static float[] getFloats(String s) {
        if (s.charAt(0) == 'z' || s.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[s.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = s.length();
            int i = 1;
            int i2 = 0;
            while (i < length) {
                extract(s, i, extractFloatResult);
                int i3 = extractFloatResult.mEndPosition;
                if (i < i3) {
                    fArr[i2] = Float.parseFloat(s.substring(i, i3));
                    i2++;
                }
                i = extractFloatResult.mEndWithNegOrDot ? i3 : i3 + 1;
            }
            return copyOfRange(fArr, 0, i2);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003a A[LOOP:0: B:3:0x0007->B:24:0x003a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void extract(java.lang.String r8, int r9, androidx.core.graphics.PathParser.ExtractFloatResult r10) {
        /*
            r0 = 0
            r10.mEndWithNegOrDot = r0
            r1 = r9
            r2 = 0
            r3 = 0
            r4 = 0
        L7:
            int r5 = r8.length()
            if (r1 >= r5) goto L3d
            char r5 = r8.charAt(r1)
            r6 = 32
            r7 = 1
            if (r5 == r6) goto L35
            r6 = 69
            if (r5 == r6) goto L33
            r6 = 101(0x65, float:1.42E-43)
            if (r5 == r6) goto L33
            switch(r5) {
                case 44: goto L35;
                case 45: goto L2a;
                case 46: goto L22;
                default: goto L21;
            }
        L21:
            goto L31
        L22:
            if (r3 != 0) goto L27
            r2 = 0
            r3 = 1
            goto L37
        L27:
            r10.mEndWithNegOrDot = r7
            goto L35
        L2a:
            if (r1 == r9) goto L31
            if (r2 != 0) goto L31
            r10.mEndWithNegOrDot = r7
            goto L35
        L31:
            r2 = 0
            goto L37
        L33:
            r2 = 1
            goto L37
        L35:
            r2 = 0
            r4 = 1
        L37:
            if (r4 == 0) goto L3a
            goto L3d
        L3a:
            int r1 = r1 + 1
            goto L7
        L3d:
            r10.mEndPosition = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.PathParser.extract(java.lang.String, int, androidx.core.graphics.PathParser$ExtractFloatResult):void");
    }

    public static boolean interpolatePathDataNodes(PathDataNode[] target, PathDataNode[] from, PathDataNode[] to, float fraction) {
        if (target == null || from == null || to == null) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        }
        if (target.length != from.length || from.length != to.length) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        }
        if (!canMorph(from, to)) {
            return false;
        }
        for (int i = 0; i < target.length; i++) {
            target[i].interpolatePathDataNode(from[i], to[i], fraction);
        }
        return true;
    }

    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char type, float[] params) {
            this.mType = type;
            this.mParams = params;
        }

        PathDataNode(PathDataNode n) {
            this.mType = n.mType;
            float[] fArr = n.mParams;
            this.mParams = PathParser.copyOfRange(fArr, 0, fArr.length);
        }

        public static void nodesToPath(PathDataNode[] node, Path path) {
            float[] fArr = new float[6];
            char c = 'm';
            for (int i = 0; i < node.length; i++) {
                addCommand(path, fArr, c, node[i].mType, node[i].mParams);
                c = node[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode nodeFrom, PathDataNode nodeTo, float fraction) {
            this.mType = nodeFrom.mType;
            int i = 0;
            while (true) {
                float[] fArr = nodeFrom.mParams;
                if (i >= fArr.length) {
                    return;
                }
                this.mParams[i] = (fArr[i] * (1.0f - fraction)) + (nodeTo.mParams[i] * fraction);
                i++;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static void addCommand(Path path, float[] current, char previousCmd, char cmd, float[] val) {
            int i;
            int i2;
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            char c = cmd;
            float f9 = current[0];
            float f10 = current[1];
            float f11 = current[2];
            float f12 = current[3];
            float f13 = current[4];
            float f14 = current[5];
            switch (c) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case l.d /* 99 */:
                    i = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case WXMediaMessage.IMediaObject.TYPE_MUSIC_VIDEO /* 76 */:
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                default:
                    i = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case OpenAuthTask.g /* 122 */:
                    path.close();
                    path.moveTo(f13, f14);
                    f9 = f13;
                    f11 = f9;
                    f10 = f14;
                    f12 = f10;
                    i = 2;
                    break;
            }
            float f15 = f9;
            float f16 = f10;
            float f17 = f13;
            float f18 = f14;
            int i3 = 0;
            char c2 = previousCmd;
            while (i3 < val.length) {
                if (c != 'A') {
                    if (c == 'C') {
                        i2 = i3;
                        int i4 = i2 + 2;
                        int i5 = i2 + 3;
                        int i6 = i2 + 4;
                        int i7 = i2 + 5;
                        path.cubicTo(val[i2 + 0], val[i2 + 1], val[i4], val[i5], val[i6], val[i7]);
                        f15 = val[i6];
                        float f19 = val[i7];
                        float f20 = val[i4];
                        float f21 = val[i5];
                        f16 = f19;
                        f12 = f21;
                        f11 = f20;
                    } else if (c == 'H') {
                        i2 = i3;
                        int i8 = i2 + 0;
                        path.lineTo(val[i8], f16);
                        f15 = val[i8];
                    } else if (c == 'Q') {
                        i2 = i3;
                        int i9 = i2 + 0;
                        int i10 = i2 + 1;
                        int i11 = i2 + 2;
                        int i12 = i2 + 3;
                        path.quadTo(val[i9], val[i10], val[i11], val[i12]);
                        float f22 = val[i9];
                        float f23 = val[i10];
                        f15 = val[i11];
                        f16 = val[i12];
                        f11 = f22;
                        f12 = f23;
                    } else if (c == 'V') {
                        i2 = i3;
                        int i13 = i2 + 0;
                        path.lineTo(f15, val[i13]);
                        f16 = val[i13];
                    } else if (c != 'a') {
                        if (c != 'c') {
                            if (c == 'h') {
                                int i14 = i3 + 0;
                                path.rLineTo(val[i14], 0.0f);
                                f15 += val[i14];
                            } else if (c != 'q') {
                                if (c == 'v') {
                                    int i15 = i3 + 0;
                                    path.rLineTo(0.0f, val[i15]);
                                    f4 = val[i15];
                                } else if (c == 'L') {
                                    int i16 = i3 + 0;
                                    int i17 = i3 + 1;
                                    path.lineTo(val[i16], val[i17]);
                                    f15 = val[i16];
                                    f16 = val[i17];
                                } else if (c == 'M') {
                                    int i18 = i3 + 0;
                                    f15 = val[i18];
                                    int i19 = i3 + 1;
                                    f16 = val[i19];
                                    if (i3 > 0) {
                                        path.lineTo(val[i18], val[i19]);
                                    } else {
                                        path.moveTo(val[i18], val[i19]);
                                        i2 = i3;
                                        f18 = f16;
                                        f17 = f15;
                                    }
                                } else if (c == 'S') {
                                    if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                                        f15 = (f15 * 2.0f) - f11;
                                        f16 = (f16 * 2.0f) - f12;
                                    }
                                    float f24 = f16;
                                    int i20 = i3 + 0;
                                    int i21 = i3 + 1;
                                    int i22 = i3 + 2;
                                    int i23 = i3 + 3;
                                    path.cubicTo(f15, f24, val[i20], val[i21], val[i22], val[i23]);
                                    f = val[i20];
                                    f2 = val[i21];
                                    f15 = val[i22];
                                    f16 = val[i23];
                                    f11 = f;
                                    f12 = f2;
                                } else if (c == 'T') {
                                    if (c2 == 'q' || c2 == 't' || c2 == 'Q' || c2 == 'T') {
                                        f15 = (f15 * 2.0f) - f11;
                                        f16 = (f16 * 2.0f) - f12;
                                    }
                                    int i24 = i3 + 0;
                                    int i25 = i3 + 1;
                                    path.quadTo(f15, f16, val[i24], val[i25]);
                                    float f25 = val[i24];
                                    float f26 = val[i25];
                                    i2 = i3;
                                    f12 = f16;
                                    f11 = f15;
                                    f15 = f25;
                                    f16 = f26;
                                } else if (c == 'l') {
                                    int i26 = i3 + 0;
                                    int i27 = i3 + 1;
                                    path.rLineTo(val[i26], val[i27]);
                                    f15 += val[i26];
                                    f4 = val[i27];
                                } else if (c == 'm') {
                                    int i28 = i3 + 0;
                                    f15 += val[i28];
                                    int i29 = i3 + 1;
                                    f16 += val[i29];
                                    if (i3 > 0) {
                                        path.rLineTo(val[i28], val[i29]);
                                    } else {
                                        path.rMoveTo(val[i28], val[i29]);
                                        i2 = i3;
                                        f18 = f16;
                                        f17 = f15;
                                    }
                                } else if (c == 's') {
                                    if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                                        float f27 = f15 - f11;
                                        f5 = f16 - f12;
                                        f6 = f27;
                                    } else {
                                        f6 = 0.0f;
                                        f5 = 0.0f;
                                    }
                                    int i30 = i3 + 0;
                                    int i31 = i3 + 1;
                                    int i32 = i3 + 2;
                                    int i33 = i3 + 3;
                                    path.rCubicTo(f6, f5, val[i30], val[i31], val[i32], val[i33]);
                                    f = val[i30] + f15;
                                    f2 = val[i31] + f16;
                                    f15 += val[i32];
                                    f3 = val[i33];
                                } else if (c == 't') {
                                    if (c2 == 'q' || c2 == 't' || c2 == 'Q' || c2 == 'T') {
                                        f7 = f15 - f11;
                                        f8 = f16 - f12;
                                    } else {
                                        f8 = 0.0f;
                                        f7 = 0.0f;
                                    }
                                    int i34 = i3 + 0;
                                    int i35 = i3 + 1;
                                    path.rQuadTo(f7, f8, val[i34], val[i35]);
                                    float f28 = f7 + f15;
                                    float f29 = f8 + f16;
                                    f15 += val[i34];
                                    f16 += val[i35];
                                    f12 = f29;
                                    f11 = f28;
                                }
                                f16 += f4;
                            } else {
                                int i36 = i3 + 0;
                                int i37 = i3 + 1;
                                int i38 = i3 + 2;
                                int i39 = i3 + 3;
                                path.rQuadTo(val[i36], val[i37], val[i38], val[i39]);
                                f = val[i36] + f15;
                                f2 = val[i37] + f16;
                                f15 += val[i38];
                                f3 = val[i39];
                            }
                            i2 = i3;
                        } else {
                            int i40 = i3 + 2;
                            int i41 = i3 + 3;
                            int i42 = i3 + 4;
                            int i43 = i3 + 5;
                            path.rCubicTo(val[i3 + 0], val[i3 + 1], val[i40], val[i41], val[i42], val[i43]);
                            f = val[i40] + f15;
                            f2 = val[i41] + f16;
                            f15 += val[i42];
                            f3 = val[i43];
                        }
                        f16 += f3;
                        f11 = f;
                        f12 = f2;
                        i2 = i3;
                    } else {
                        int i44 = i3 + 5;
                        int i45 = i3 + 6;
                        i2 = i3;
                        drawArc(path, f15, f16, val[i44] + f15, val[i45] + f16, val[i3 + 0], val[i3 + 1], val[i3 + 2], val[i3 + 3] != 0.0f, val[i3 + 4] != 0.0f);
                        f15 += val[i44];
                        f16 += val[i45];
                    }
                    i3 = i2 + i;
                    c2 = cmd;
                    c = c2;
                } else {
                    i2 = i3;
                    int i46 = i2 + 5;
                    int i47 = i2 + 6;
                    drawArc(path, f15, f16, val[i46], val[i47], val[i2 + 0], val[i2 + 1], val[i2 + 2], val[i2 + 3] != 0.0f, val[i2 + 4] != 0.0f);
                    f15 = val[i46];
                    f16 = val[i47];
                }
                f12 = f16;
                f11 = f15;
                i3 = i2 + i;
                c2 = cmd;
                c = c2;
            }
            current[0] = f15;
            current[1] = f16;
            current[2] = f11;
            current[3] = f12;
            current[4] = f17;
            current[5] = f18;
        }

        private static void drawArc(Path p, float x0, float y0, float x1, float y1, float a, float b, float theta, boolean isMoreThanHalf, boolean isPositiveArc) {
            double d;
            double d2;
            double radians = Math.toRadians(theta);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            double d3 = x0;
            double d4 = d3 * dCos;
            double d5 = y0;
            double d6 = a;
            double d7 = (d4 + (d5 * dSin)) / d6;
            double d8 = (((double) (-x0)) * dSin) + (d5 * dCos);
            double d9 = b;
            double d10 = d8 / d9;
            double d11 = y1;
            double d12 = ((((double) x1) * dCos) + (d11 * dSin)) / d6;
            double d13 = ((((double) (-x1)) * dSin) + (d11 * dCos)) / d9;
            double d14 = d7 - d12;
            double d15 = d10 - d13;
            double d16 = (d7 + d12) / 2.0d;
            double d17 = (d10 + d13) / 2.0d;
            double d18 = (d14 * d14) + (d15 * d15);
            if (d18 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d19 = (1.0d / d18) - 0.25d;
            if (d19 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d18);
                float fSqrt = (float) (Math.sqrt(d18) / 1.99999d);
                drawArc(p, x0, y0, x1, y1, a * fSqrt, b * fSqrt, theta, isMoreThanHalf, isPositiveArc);
                return;
            }
            double dSqrt = Math.sqrt(d19);
            double d20 = d14 * dSqrt;
            double d21 = dSqrt * d15;
            if (isMoreThanHalf == isPositiveArc) {
                d = d16 - d21;
                d2 = d17 + d20;
            } else {
                d = d16 + d21;
                d2 = d17 - d20;
            }
            double dAtan2 = Math.atan2(d10 - d2, d7 - d);
            double dAtan22 = Math.atan2(d13 - d2, d12 - d) - dAtan2;
            if (isPositiveArc != (dAtan22 >= 0.0d)) {
                dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
            }
            double d22 = d * d6;
            double d23 = d2 * d9;
            arcToBezier(p, (d22 * dCos) - (d23 * dSin), (d22 * dSin) + (d23 * dCos), d6, d9, d3, d5, radians, dAtan2, dAtan22);
        }

        private static void arcToBezier(Path p, double cx, double cy, double a, double b, double e1x, double e1y, double theta, double start, double sweep) {
            double d = a;
            int iCeil = (int) Math.ceil(Math.abs((sweep * 4.0d) / 3.141592653589793d));
            double dCos = Math.cos(theta);
            double dSin = Math.sin(theta);
            double dCos2 = Math.cos(start);
            double dSin2 = Math.sin(start);
            double d2 = -d;
            double d3 = d2 * dCos;
            double d4 = b * dSin;
            double d5 = (d3 * dSin2) - (d4 * dCos2);
            double d6 = d2 * dSin;
            double d7 = b * dCos;
            double d8 = (dSin2 * d6) + (dCos2 * d7);
            double d9 = sweep / ((double) iCeil);
            double d10 = start;
            double d11 = d8;
            double d12 = d5;
            int i = 0;
            double d13 = e1x;
            double d14 = e1y;
            while (i < iCeil) {
                double d15 = d10 + d9;
                double dSin3 = Math.sin(d15);
                double dCos3 = Math.cos(d15);
                double d16 = (cx + ((d * dCos) * dCos3)) - (d4 * dSin3);
                double d17 = cy + (d * dSin * dCos3) + (d7 * dSin3);
                double d18 = (d3 * dSin3) - (d4 * dCos3);
                double d19 = (dSin3 * d6) + (dCos3 * d7);
                double d20 = d15 - d10;
                double dTan = Math.tan(d20 / 2.0d);
                double dSin4 = (Math.sin(d20) * (Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d)) / 3.0d;
                double d21 = d13 + (d12 * dSin4);
                double d22 = dCos;
                double d23 = dSin;
                p.rLineTo(0.0f, 0.0f);
                p.cubicTo((float) d21, (float) (d14 + (d11 * dSin4)), (float) (d16 - (dSin4 * d18)), (float) (d17 - (dSin4 * d19)), (float) d16, (float) d17);
                i++;
                d9 = d9;
                dSin = d23;
                d13 = d16;
                d6 = d6;
                dCos = d22;
                d10 = d15;
                d11 = d19;
                d12 = d18;
                iCeil = iCeil;
                d14 = d17;
                d = a;
            }
        }
    }

    private PathParser() {
    }
}
