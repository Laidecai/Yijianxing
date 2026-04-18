package com.tds.lz4;

/* JADX INFO: loaded from: classes.dex */
enum LZ4Utils {
    ;

    private static final int MAX_INPUT_SIZE = 2113929216;

    static int hash(int i) {
        return (i * (-1640531535)) >>> 20;
    }

    static int hash64k(int i) {
        return (i * (-1640531535)) >>> 19;
    }

    static int hashHC(int i) {
        return (i * (-1640531535)) >>> 17;
    }

    static int maxCompressedLength(int i) {
        if (i >= 0) {
            if (i >= MAX_INPUT_SIZE) {
                throw new IllegalArgumentException("length must be < 2113929216");
            }
            return i + (i / 255) + 16;
        }
        throw new IllegalArgumentException("length must be >= 0, got " + i);
    }

    static class Match {
        int len;
        int ref;
        int start;

        Match() {
        }

        void fix(int i) {
            this.start += i;
            this.ref += i;
            this.len -= i;
        }

        int end() {
            return this.start + this.len;
        }
    }

    static void copyTo(Match match, Match match2) {
        match2.len = match.len;
        match2.start = match.start;
        match2.ref = match.ref;
    }
}
