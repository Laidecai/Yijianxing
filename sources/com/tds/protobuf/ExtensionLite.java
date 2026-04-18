package com.tds.protobuf;

import com.tds.protobuf.MessageLite;
import com.tds.protobuf.WireFormat;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExtensionLite<ContainingType extends MessageLite, Type> {
    public abstract Type getDefaultValue();

    public abstract WireFormat.FieldType getLiteType();

    public abstract MessageLite getMessageDefaultInstance();

    public abstract int getNumber();

    boolean isLite() {
        return true;
    }

    public abstract boolean isRepeated();
}
