package com.tds.protobuf;

/* JADX INFO: loaded from: classes.dex */
interface MutabilityOracle {
    public static final MutabilityOracle IMMUTABLE = new MutabilityOracle() { // from class: com.tds.protobuf.MutabilityOracle.1
        @Override // com.tds.protobuf.MutabilityOracle
        public void ensureMutable() {
            throw new UnsupportedOperationException();
        }
    };

    void ensureMutable();
}
