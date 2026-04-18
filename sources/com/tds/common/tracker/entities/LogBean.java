package com.tds.common.tracker.entities;

import com.tds.protobuf.AbstractMessageLite;
import com.tds.protobuf.ByteString;
import com.tds.protobuf.CodedInputStream;
import com.tds.protobuf.CodedOutputStream;
import com.tds.protobuf.ExtensionRegistryLite;
import com.tds.protobuf.GeneratedMessageLite;
import com.tds.protobuf.Internal;
import com.tds.protobuf.InvalidProtocolBufferException;
import com.tds.protobuf.MessageLiteOrBuilder;
import com.tds.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class LogBean {

    public interface LogContentOrBuilder extends MessageLiteOrBuilder {
        String getKey();

        ByteString getKeyBytes();

        String getValue();

        ByteString getValueBytes();
    }

    public interface LogGroupListOrBuilder extends MessageLiteOrBuilder {
        LogGroup getLogGroups(int i);

        int getLogGroupsCount();

        List<LogGroup> getLogGroupsList();
    }

    public interface LogGroupOrBuilder extends MessageLiteOrBuilder {
        String getCategory();

        ByteString getCategoryBytes();

        LogTag getLogTags(int i);

        int getLogTagsCount();

        List<LogTag> getLogTagsList();

        Log getLogs(int i);

        int getLogsCount();

        List<Log> getLogsList();

        String getMachineUUID();

        ByteString getMachineUUIDBytes();

        String getSource();

        ByteString getSourceBytes();

        String getTopic();

        ByteString getTopicBytes();
    }

    public interface LogOrBuilder extends MessageLiteOrBuilder {
        LogContent getContents(int i);

        int getContentsCount();

        List<LogContent> getContentsList();

        int getTime();
    }

    public interface LogTagOrBuilder extends MessageLiteOrBuilder {
        String getKey();

        ByteString getKeyBytes();

        String getValue();

        ByteString getValueBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private LogBean() {
    }

    public static final class LogContent extends GeneratedMessageLite<LogContent, Builder> implements LogContentOrBuilder {
        private static final LogContent DEFAULT_INSTANCE;
        public static final int KEY_FIELD_NUMBER = 1;
        private static volatile Parser<LogContent> PARSER = null;
        public static final int VALUE_FIELD_NUMBER = 2;
        private String key_ = "";
        private String value_ = "";

        private LogContent() {
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
        public String getKey() {
            return this.key_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
        public ByteString getKeyBytes() {
            return ByteString.copyFromUtf8(this.key_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setKey(String str) {
            Objects.requireNonNull(str);
            this.key_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearKey() {
            this.key_ = getDefaultInstance().getKey();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setKeyBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.key_ = byteString.toStringUtf8();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
        public String getValue() {
            return this.value_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
        public ByteString getValueBytes() {
            return ByteString.copyFromUtf8(this.value_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setValue(String str) {
            Objects.requireNonNull(str);
            this.value_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearValue() {
            this.value_ = getDefaultInstance().getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setValueBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.value_ = byteString.toStringUtf8();
        }

        @Override // com.tds.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.key_.isEmpty()) {
                codedOutputStream.writeString(1, getKey());
            }
            if (this.value_.isEmpty()) {
                return;
            }
            codedOutputStream.writeString(2, getValue());
        }

        @Override // com.tds.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = this.key_.isEmpty() ? 0 : 0 + CodedOutputStream.computeStringSize(1, getKey());
            if (!this.value_.isEmpty()) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getValue());
            }
            this.memoizedSerializedSize = iComputeStringSize;
            return iComputeStringSize;
        }

        public static LogContent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LogContent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LogContent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LogContent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static LogContent parseFrom(InputStream inputStream) throws IOException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogContent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogContent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (LogContent) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogContent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogContent) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogContent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LogContent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogContent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LogContent logContent) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(logContent);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LogContent, Builder> implements LogContentOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(LogContent.DEFAULT_INSTANCE);
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
            public String getKey() {
                return ((LogContent) this.instance).getKey();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
            public ByteString getKeyBytes() {
                return ((LogContent) this.instance).getKeyBytes();
            }

            public Builder setKey(String str) {
                copyOnWrite();
                ((LogContent) this.instance).setKey(str);
                return this;
            }

            public Builder clearKey() {
                copyOnWrite();
                ((LogContent) this.instance).clearKey();
                return this;
            }

            public Builder setKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((LogContent) this.instance).setKeyBytes(byteString);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
            public String getValue() {
                return ((LogContent) this.instance).getValue();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogContentOrBuilder
            public ByteString getValueBytes() {
                return ((LogContent) this.instance).getValueBytes();
            }

            public Builder setValue(String str) {
                copyOnWrite();
                ((LogContent) this.instance).setValue(str);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((LogContent) this.instance).clearValue();
                return this;
            }

            public Builder setValueBytes(ByteString byteString) {
                copyOnWrite();
                ((LogContent) this.instance).setValueBytes(byteString);
                return this;
            }
        }

        @Override // com.tds.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.$SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LogContent();
                case 2:
                    return DEFAULT_INSTANCE;
                case 3:
                    return null;
                case 4:
                    return new Builder(anonymousClass1);
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    LogContent logContent = (LogContent) obj2;
                    this.key_ = visitor.visitString(!this.key_.isEmpty(), this.key_, !logContent.key_.isEmpty(), logContent.key_);
                    this.value_ = visitor.visitString(!this.value_.isEmpty(), this.value_, true ^ logContent.value_.isEmpty(), logContent.value_);
                    GeneratedMessageLite.MergeFromVisitor mergeFromVisitor = GeneratedMessageLite.MergeFromVisitor.INSTANCE;
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    this.key_ = codedInputStream.readStringRequireUtf8();
                                } else if (tag != 18) {
                                    if (!codedInputStream.skipField(tag)) {
                                    }
                                } else {
                                    this.value_ = codedInputStream.readStringRequireUtf8();
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    if (PARSER == null) {
                        synchronized (LogContent.class) {
                            if (PARSER == null) {
                                PARSER = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                            break;
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            LogContent logContent = new LogContent();
            DEFAULT_INSTANCE = logContent;
            logContent.makeImmutable();
        }

        public static LogContent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LogContent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* JADX INFO: renamed from: com.tds.common.tracker.entities.LogBean$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.IS_INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.MAKE_IMMUTABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.VISIT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static final class Log extends GeneratedMessageLite<Log, Builder> implements LogOrBuilder {
        public static final int CONTENTS_FIELD_NUMBER = 2;
        private static final Log DEFAULT_INSTANCE;
        private static volatile Parser<Log> PARSER = null;
        public static final int TIME_FIELD_NUMBER = 1;
        private int bitField0_;
        private Internal.ProtobufList<LogContent> contents_ = emptyProtobufList();
        private int time_;

        private Log() {
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
        public int getTime() {
            return this.time_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTime(int i) {
            this.time_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTime() {
            this.time_ = 0;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
        public List<LogContent> getContentsList() {
            return this.contents_;
        }

        public List<? extends LogContentOrBuilder> getContentsOrBuilderList() {
            return this.contents_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
        public int getContentsCount() {
            return this.contents_.size();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
        public LogContent getContents(int i) {
            return this.contents_.get(i);
        }

        public LogContentOrBuilder getContentsOrBuilder(int i) {
            return this.contents_.get(i);
        }

        private void ensureContentsIsMutable() {
            if (this.contents_.isModifiable()) {
                return;
            }
            this.contents_ = GeneratedMessageLite.mutableCopy(this.contents_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setContents(int i, LogContent logContent) {
            Objects.requireNonNull(logContent);
            ensureContentsIsMutable();
            this.contents_.set(i, logContent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setContents(int i, LogContent.Builder builder) {
            ensureContentsIsMutable();
            this.contents_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContents(LogContent logContent) {
            Objects.requireNonNull(logContent);
            ensureContentsIsMutable();
            this.contents_.add(logContent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContents(int i, LogContent logContent) {
            Objects.requireNonNull(logContent);
            ensureContentsIsMutable();
            this.contents_.add(i, logContent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContents(LogContent.Builder builder) {
            ensureContentsIsMutable();
            this.contents_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContents(int i, LogContent.Builder builder) {
            ensureContentsIsMutable();
            this.contents_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllContents(Iterable<? extends LogContent> iterable) {
            ensureContentsIsMutable();
            AbstractMessageLite.addAll(iterable, this.contents_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearContents() {
            this.contents_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeContents(int i) {
            ensureContentsIsMutable();
            this.contents_.remove(i);
        }

        @Override // com.tds.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            int i = this.time_;
            if (i != 0) {
                codedOutputStream.writeUInt32(1, i);
            }
            for (int i2 = 0; i2 < this.contents_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.contents_.get(i2));
            }
        }

        @Override // com.tds.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int i2 = this.time_;
            int iComputeUInt32Size = i2 != 0 ? CodedOutputStream.computeUInt32Size(1, i2) + 0 : 0;
            for (int i3 = 0; i3 < this.contents_.size(); i3++) {
                iComputeUInt32Size += CodedOutputStream.computeMessageSize(2, this.contents_.get(i3));
            }
            this.memoizedSerializedSize = iComputeUInt32Size;
            return iComputeUInt32Size;
        }

        public static Log parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Log parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Log parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Log parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Log parseFrom(InputStream inputStream) throws IOException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Log parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Log parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Log) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Log parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Log) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Log parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Log parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Log) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Log log) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(log);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Log, Builder> implements LogOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(Log.DEFAULT_INSTANCE);
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
            public int getTime() {
                return ((Log) this.instance).getTime();
            }

            public Builder setTime(int i) {
                copyOnWrite();
                ((Log) this.instance).setTime(i);
                return this;
            }

            public Builder clearTime() {
                copyOnWrite();
                ((Log) this.instance).clearTime();
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
            public List<LogContent> getContentsList() {
                return Collections.unmodifiableList(((Log) this.instance).getContentsList());
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
            public int getContentsCount() {
                return ((Log) this.instance).getContentsCount();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogOrBuilder
            public LogContent getContents(int i) {
                return ((Log) this.instance).getContents(i);
            }

            public Builder setContents(int i, LogContent logContent) {
                copyOnWrite();
                ((Log) this.instance).setContents(i, logContent);
                return this;
            }

            public Builder setContents(int i, LogContent.Builder builder) {
                copyOnWrite();
                ((Log) this.instance).setContents(i, builder);
                return this;
            }

            public Builder addContents(LogContent logContent) {
                copyOnWrite();
                ((Log) this.instance).addContents(logContent);
                return this;
            }

            public Builder addContents(int i, LogContent logContent) {
                copyOnWrite();
                ((Log) this.instance).addContents(i, logContent);
                return this;
            }

            public Builder addContents(LogContent.Builder builder) {
                copyOnWrite();
                ((Log) this.instance).addContents(builder);
                return this;
            }

            public Builder addContents(int i, LogContent.Builder builder) {
                copyOnWrite();
                ((Log) this.instance).addContents(i, builder);
                return this;
            }

            public Builder addAllContents(Iterable<? extends LogContent> iterable) {
                copyOnWrite();
                ((Log) this.instance).addAllContents(iterable);
                return this;
            }

            public Builder clearContents() {
                copyOnWrite();
                ((Log) this.instance).clearContents();
                return this;
            }

            public Builder removeContents(int i) {
                copyOnWrite();
                ((Log) this.instance).removeContents(i);
                return this;
            }
        }

        @Override // com.tds.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.$SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new Log();
                case 2:
                    return DEFAULT_INSTANCE;
                case 3:
                    this.contents_.makeImmutable();
                    return null;
                case 4:
                    return new Builder(anonymousClass1);
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    Log log = (Log) obj2;
                    int i = this.time_;
                    boolean z = i != 0;
                    int i2 = log.time_;
                    this.time_ = visitor.visitInt(z, i, i2 != 0, i2);
                    this.contents_ = visitor.visitList(this.contents_, log.contents_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= log.bitField0_;
                    }
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    this.time_ = codedInputStream.readUInt32();
                                } else if (tag != 18) {
                                    if (!codedInputStream.skipField(tag)) {
                                    }
                                } else {
                                    if (!this.contents_.isModifiable()) {
                                        this.contents_ = GeneratedMessageLite.mutableCopy(this.contents_);
                                    }
                                    this.contents_.add((LogContent) codedInputStream.readMessage(LogContent.parser(), extensionRegistryLite));
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    if (PARSER == null) {
                        synchronized (Log.class) {
                            if (PARSER == null) {
                                PARSER = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                            break;
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            Log log = new Log();
            DEFAULT_INSTANCE = log;
            log.makeImmutable();
        }

        public static Log getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Log> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class LogTag extends GeneratedMessageLite<LogTag, Builder> implements LogTagOrBuilder {
        private static final LogTag DEFAULT_INSTANCE;
        public static final int KEY_FIELD_NUMBER = 1;
        private static volatile Parser<LogTag> PARSER = null;
        public static final int VALUE_FIELD_NUMBER = 2;
        private String key_ = "";
        private String value_ = "";

        private LogTag() {
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
        public String getKey() {
            return this.key_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
        public ByteString getKeyBytes() {
            return ByteString.copyFromUtf8(this.key_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setKey(String str) {
            Objects.requireNonNull(str);
            this.key_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearKey() {
            this.key_ = getDefaultInstance().getKey();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setKeyBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.key_ = byteString.toStringUtf8();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
        public String getValue() {
            return this.value_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
        public ByteString getValueBytes() {
            return ByteString.copyFromUtf8(this.value_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setValue(String str) {
            Objects.requireNonNull(str);
            this.value_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearValue() {
            this.value_ = getDefaultInstance().getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setValueBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.value_ = byteString.toStringUtf8();
        }

        @Override // com.tds.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.key_.isEmpty()) {
                codedOutputStream.writeString(1, getKey());
            }
            if (this.value_.isEmpty()) {
                return;
            }
            codedOutputStream.writeString(2, getValue());
        }

        @Override // com.tds.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = this.key_.isEmpty() ? 0 : 0 + CodedOutputStream.computeStringSize(1, getKey());
            if (!this.value_.isEmpty()) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getValue());
            }
            this.memoizedSerializedSize = iComputeStringSize;
            return iComputeStringSize;
        }

        public static LogTag parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LogTag parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LogTag parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LogTag parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static LogTag parseFrom(InputStream inputStream) throws IOException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogTag parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogTag parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (LogTag) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogTag parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogTag) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogTag parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LogTag parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogTag) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LogTag logTag) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(logTag);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LogTag, Builder> implements LogTagOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(LogTag.DEFAULT_INSTANCE);
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
            public String getKey() {
                return ((LogTag) this.instance).getKey();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
            public ByteString getKeyBytes() {
                return ((LogTag) this.instance).getKeyBytes();
            }

            public Builder setKey(String str) {
                copyOnWrite();
                ((LogTag) this.instance).setKey(str);
                return this;
            }

            public Builder clearKey() {
                copyOnWrite();
                ((LogTag) this.instance).clearKey();
                return this;
            }

            public Builder setKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((LogTag) this.instance).setKeyBytes(byteString);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
            public String getValue() {
                return ((LogTag) this.instance).getValue();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogTagOrBuilder
            public ByteString getValueBytes() {
                return ((LogTag) this.instance).getValueBytes();
            }

            public Builder setValue(String str) {
                copyOnWrite();
                ((LogTag) this.instance).setValue(str);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((LogTag) this.instance).clearValue();
                return this;
            }

            public Builder setValueBytes(ByteString byteString) {
                copyOnWrite();
                ((LogTag) this.instance).setValueBytes(byteString);
                return this;
            }
        }

        @Override // com.tds.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.$SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LogTag();
                case 2:
                    return DEFAULT_INSTANCE;
                case 3:
                    return null;
                case 4:
                    return new Builder(anonymousClass1);
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    LogTag logTag = (LogTag) obj2;
                    this.key_ = visitor.visitString(!this.key_.isEmpty(), this.key_, !logTag.key_.isEmpty(), logTag.key_);
                    this.value_ = visitor.visitString(!this.value_.isEmpty(), this.value_, true ^ logTag.value_.isEmpty(), logTag.value_);
                    GeneratedMessageLite.MergeFromVisitor mergeFromVisitor = GeneratedMessageLite.MergeFromVisitor.INSTANCE;
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    this.key_ = codedInputStream.readStringRequireUtf8();
                                } else if (tag != 18) {
                                    if (!codedInputStream.skipField(tag)) {
                                    }
                                } else {
                                    this.value_ = codedInputStream.readStringRequireUtf8();
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    if (PARSER == null) {
                        synchronized (LogTag.class) {
                            if (PARSER == null) {
                                PARSER = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                            break;
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            LogTag logTag = new LogTag();
            DEFAULT_INSTANCE = logTag;
            logTag.makeImmutable();
        }

        public static LogTag getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LogTag> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class LogGroup extends GeneratedMessageLite<LogGroup, Builder> implements LogGroupOrBuilder {
        public static final int CATEGORY_FIELD_NUMBER = 2;
        private static final LogGroup DEFAULT_INSTANCE;
        public static final int LOGS_FIELD_NUMBER = 1;
        public static final int LOGTAGS_FIELD_NUMBER = 6;
        public static final int MACHINEUUID_FIELD_NUMBER = 5;
        private static volatile Parser<LogGroup> PARSER = null;
        public static final int SOURCE_FIELD_NUMBER = 4;
        public static final int TOPIC_FIELD_NUMBER = 3;
        private int bitField0_;
        private Internal.ProtobufList<Log> logs_ = emptyProtobufList();
        private String category_ = "";
        private String topic_ = "";
        private String source_ = "";
        private String machineUUID_ = "";
        private Internal.ProtobufList<LogTag> logTags_ = emptyProtobufList();

        private LogGroup() {
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public List<Log> getLogsList() {
            return this.logs_;
        }

        public List<? extends LogOrBuilder> getLogsOrBuilderList() {
            return this.logs_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public int getLogsCount() {
            return this.logs_.size();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public Log getLogs(int i) {
            return this.logs_.get(i);
        }

        public LogOrBuilder getLogsOrBuilder(int i) {
            return this.logs_.get(i);
        }

        private void ensureLogsIsMutable() {
            if (this.logs_.isModifiable()) {
                return;
            }
            this.logs_ = GeneratedMessageLite.mutableCopy(this.logs_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLogs(int i, Log log) {
            Objects.requireNonNull(log);
            ensureLogsIsMutable();
            this.logs_.set(i, log);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLogs(int i, Log.Builder builder) {
            ensureLogsIsMutable();
            this.logs_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogs(Log log) {
            Objects.requireNonNull(log);
            ensureLogsIsMutable();
            this.logs_.add(log);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogs(int i, Log log) {
            Objects.requireNonNull(log);
            ensureLogsIsMutable();
            this.logs_.add(i, log);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogs(Log.Builder builder) {
            ensureLogsIsMutable();
            this.logs_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogs(int i, Log.Builder builder) {
            ensureLogsIsMutable();
            this.logs_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllLogs(Iterable<? extends Log> iterable) {
            ensureLogsIsMutable();
            AbstractMessageLite.addAll(iterable, this.logs_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLogs() {
            this.logs_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeLogs(int i) {
            ensureLogsIsMutable();
            this.logs_.remove(i);
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public String getCategory() {
            return this.category_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public ByteString getCategoryBytes() {
            return ByteString.copyFromUtf8(this.category_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCategory(String str) {
            Objects.requireNonNull(str);
            this.category_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCategory() {
            this.category_ = getDefaultInstance().getCategory();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCategoryBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.category_ = byteString.toStringUtf8();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public String getTopic() {
            return this.topic_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public ByteString getTopicBytes() {
            return ByteString.copyFromUtf8(this.topic_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTopic(String str) {
            Objects.requireNonNull(str);
            this.topic_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTopic() {
            this.topic_ = getDefaultInstance().getTopic();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTopicBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.topic_ = byteString.toStringUtf8();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public String getSource() {
            return this.source_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public ByteString getSourceBytes() {
            return ByteString.copyFromUtf8(this.source_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSource(String str) {
            Objects.requireNonNull(str);
            this.source_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSource() {
            this.source_ = getDefaultInstance().getSource();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSourceBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.source_ = byteString.toStringUtf8();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public String getMachineUUID() {
            return this.machineUUID_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public ByteString getMachineUUIDBytes() {
            return ByteString.copyFromUtf8(this.machineUUID_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMachineUUID(String str) {
            Objects.requireNonNull(str);
            this.machineUUID_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMachineUUID() {
            this.machineUUID_ = getDefaultInstance().getMachineUUID();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMachineUUIDBytes(ByteString byteString) {
            Objects.requireNonNull(byteString);
            checkByteStringIsUtf8(byteString);
            this.machineUUID_ = byteString.toStringUtf8();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public List<LogTag> getLogTagsList() {
            return this.logTags_;
        }

        public List<? extends LogTagOrBuilder> getLogTagsOrBuilderList() {
            return this.logTags_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public int getLogTagsCount() {
            return this.logTags_.size();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
        public LogTag getLogTags(int i) {
            return this.logTags_.get(i);
        }

        public LogTagOrBuilder getLogTagsOrBuilder(int i) {
            return this.logTags_.get(i);
        }

        private void ensureLogTagsIsMutable() {
            if (this.logTags_.isModifiable()) {
                return;
            }
            this.logTags_ = GeneratedMessageLite.mutableCopy(this.logTags_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLogTags(int i, LogTag logTag) {
            Objects.requireNonNull(logTag);
            ensureLogTagsIsMutable();
            this.logTags_.set(i, logTag);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLogTags(int i, LogTag.Builder builder) {
            ensureLogTagsIsMutable();
            this.logTags_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogTags(LogTag logTag) {
            Objects.requireNonNull(logTag);
            ensureLogTagsIsMutable();
            this.logTags_.add(logTag);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogTags(int i, LogTag logTag) {
            Objects.requireNonNull(logTag);
            ensureLogTagsIsMutable();
            this.logTags_.add(i, logTag);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogTags(LogTag.Builder builder) {
            ensureLogTagsIsMutable();
            this.logTags_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogTags(int i, LogTag.Builder builder) {
            ensureLogTagsIsMutable();
            this.logTags_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllLogTags(Iterable<? extends LogTag> iterable) {
            ensureLogTagsIsMutable();
            AbstractMessageLite.addAll(iterable, this.logTags_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLogTags() {
            this.logTags_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeLogTags(int i) {
            ensureLogTagsIsMutable();
            this.logTags_.remove(i);
        }

        @Override // com.tds.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.logs_.size(); i++) {
                codedOutputStream.writeMessage(1, this.logs_.get(i));
            }
            if (!this.category_.isEmpty()) {
                codedOutputStream.writeString(2, getCategory());
            }
            if (!this.topic_.isEmpty()) {
                codedOutputStream.writeString(3, getTopic());
            }
            if (!this.source_.isEmpty()) {
                codedOutputStream.writeString(4, getSource());
            }
            if (!this.machineUUID_.isEmpty()) {
                codedOutputStream.writeString(5, getMachineUUID());
            }
            for (int i2 = 0; i2 < this.logTags_.size(); i2++) {
                codedOutputStream.writeMessage(6, this.logTags_.get(i2));
            }
        }

        @Override // com.tds.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = 0;
            for (int i2 = 0; i2 < this.logs_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.logs_.get(i2));
            }
            if (!this.category_.isEmpty()) {
                iComputeMessageSize += CodedOutputStream.computeStringSize(2, getCategory());
            }
            if (!this.topic_.isEmpty()) {
                iComputeMessageSize += CodedOutputStream.computeStringSize(3, getTopic());
            }
            if (!this.source_.isEmpty()) {
                iComputeMessageSize += CodedOutputStream.computeStringSize(4, getSource());
            }
            if (!this.machineUUID_.isEmpty()) {
                iComputeMessageSize += CodedOutputStream.computeStringSize(5, getMachineUUID());
            }
            for (int i3 = 0; i3 < this.logTags_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(6, this.logTags_.get(i3));
            }
            this.memoizedSerializedSize = iComputeMessageSize;
            return iComputeMessageSize;
        }

        public static LogGroup parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LogGroup parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LogGroup parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LogGroup parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static LogGroup parseFrom(InputStream inputStream) throws IOException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogGroup parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogGroup parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (LogGroup) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogGroup parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogGroup) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogGroup parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LogGroup parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogGroup) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LogGroup logGroup) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(logGroup);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LogGroup, Builder> implements LogGroupOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(LogGroup.DEFAULT_INSTANCE);
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public List<Log> getLogsList() {
                return Collections.unmodifiableList(((LogGroup) this.instance).getLogsList());
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public int getLogsCount() {
                return ((LogGroup) this.instance).getLogsCount();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public Log getLogs(int i) {
                return ((LogGroup) this.instance).getLogs(i);
            }

            public Builder setLogs(int i, Log log) {
                copyOnWrite();
                ((LogGroup) this.instance).setLogs(i, log);
                return this;
            }

            public Builder setLogs(int i, Log.Builder builder) {
                copyOnWrite();
                ((LogGroup) this.instance).setLogs(i, builder);
                return this;
            }

            public Builder addLogs(Log log) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogs(log);
                return this;
            }

            public Builder addLogs(int i, Log log) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogs(i, log);
                return this;
            }

            public Builder addLogs(Log.Builder builder) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogs(builder);
                return this;
            }

            public Builder addLogs(int i, Log.Builder builder) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogs(i, builder);
                return this;
            }

            public Builder addAllLogs(Iterable<? extends Log> iterable) {
                copyOnWrite();
                ((LogGroup) this.instance).addAllLogs(iterable);
                return this;
            }

            public Builder clearLogs() {
                copyOnWrite();
                ((LogGroup) this.instance).clearLogs();
                return this;
            }

            public Builder removeLogs(int i) {
                copyOnWrite();
                ((LogGroup) this.instance).removeLogs(i);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public String getCategory() {
                return ((LogGroup) this.instance).getCategory();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public ByteString getCategoryBytes() {
                return ((LogGroup) this.instance).getCategoryBytes();
            }

            public Builder setCategory(String str) {
                copyOnWrite();
                ((LogGroup) this.instance).setCategory(str);
                return this;
            }

            public Builder clearCategory() {
                copyOnWrite();
                ((LogGroup) this.instance).clearCategory();
                return this;
            }

            public Builder setCategoryBytes(ByteString byteString) {
                copyOnWrite();
                ((LogGroup) this.instance).setCategoryBytes(byteString);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public String getTopic() {
                return ((LogGroup) this.instance).getTopic();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public ByteString getTopicBytes() {
                return ((LogGroup) this.instance).getTopicBytes();
            }

            public Builder setTopic(String str) {
                copyOnWrite();
                ((LogGroup) this.instance).setTopic(str);
                return this;
            }

            public Builder clearTopic() {
                copyOnWrite();
                ((LogGroup) this.instance).clearTopic();
                return this;
            }

            public Builder setTopicBytes(ByteString byteString) {
                copyOnWrite();
                ((LogGroup) this.instance).setTopicBytes(byteString);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public String getSource() {
                return ((LogGroup) this.instance).getSource();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public ByteString getSourceBytes() {
                return ((LogGroup) this.instance).getSourceBytes();
            }

            public Builder setSource(String str) {
                copyOnWrite();
                ((LogGroup) this.instance).setSource(str);
                return this;
            }

            public Builder clearSource() {
                copyOnWrite();
                ((LogGroup) this.instance).clearSource();
                return this;
            }

            public Builder setSourceBytes(ByteString byteString) {
                copyOnWrite();
                ((LogGroup) this.instance).setSourceBytes(byteString);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public String getMachineUUID() {
                return ((LogGroup) this.instance).getMachineUUID();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public ByteString getMachineUUIDBytes() {
                return ((LogGroup) this.instance).getMachineUUIDBytes();
            }

            public Builder setMachineUUID(String str) {
                copyOnWrite();
                ((LogGroup) this.instance).setMachineUUID(str);
                return this;
            }

            public Builder clearMachineUUID() {
                copyOnWrite();
                ((LogGroup) this.instance).clearMachineUUID();
                return this;
            }

            public Builder setMachineUUIDBytes(ByteString byteString) {
                copyOnWrite();
                ((LogGroup) this.instance).setMachineUUIDBytes(byteString);
                return this;
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public List<LogTag> getLogTagsList() {
                return Collections.unmodifiableList(((LogGroup) this.instance).getLogTagsList());
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public int getLogTagsCount() {
                return ((LogGroup) this.instance).getLogTagsCount();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupOrBuilder
            public LogTag getLogTags(int i) {
                return ((LogGroup) this.instance).getLogTags(i);
            }

            public Builder setLogTags(int i, LogTag logTag) {
                copyOnWrite();
                ((LogGroup) this.instance).setLogTags(i, logTag);
                return this;
            }

            public Builder setLogTags(int i, LogTag.Builder builder) {
                copyOnWrite();
                ((LogGroup) this.instance).setLogTags(i, builder);
                return this;
            }

            public Builder addLogTags(LogTag logTag) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogTags(logTag);
                return this;
            }

            public Builder addLogTags(int i, LogTag logTag) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogTags(i, logTag);
                return this;
            }

            public Builder addLogTags(LogTag.Builder builder) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogTags(builder);
                return this;
            }

            public Builder addLogTags(int i, LogTag.Builder builder) {
                copyOnWrite();
                ((LogGroup) this.instance).addLogTags(i, builder);
                return this;
            }

            public Builder addAllLogTags(Iterable<? extends LogTag> iterable) {
                copyOnWrite();
                ((LogGroup) this.instance).addAllLogTags(iterable);
                return this;
            }

            public Builder clearLogTags() {
                copyOnWrite();
                ((LogGroup) this.instance).clearLogTags();
                return this;
            }

            public Builder removeLogTags(int i) {
                copyOnWrite();
                ((LogGroup) this.instance).removeLogTags(i);
                return this;
            }
        }

        @Override // com.tds.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.$SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LogGroup();
                case 2:
                    return DEFAULT_INSTANCE;
                case 3:
                    this.logs_.makeImmutable();
                    this.logTags_.makeImmutable();
                    return null;
                case 4:
                    return new Builder(anonymousClass1);
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    LogGroup logGroup = (LogGroup) obj2;
                    this.logs_ = visitor.visitList(this.logs_, logGroup.logs_);
                    this.category_ = visitor.visitString(!this.category_.isEmpty(), this.category_, !logGroup.category_.isEmpty(), logGroup.category_);
                    this.topic_ = visitor.visitString(!this.topic_.isEmpty(), this.topic_, !logGroup.topic_.isEmpty(), logGroup.topic_);
                    this.source_ = visitor.visitString(!this.source_.isEmpty(), this.source_, !logGroup.source_.isEmpty(), logGroup.source_);
                    this.machineUUID_ = visitor.visitString(!this.machineUUID_.isEmpty(), this.machineUUID_, true ^ logGroup.machineUUID_.isEmpty(), logGroup.machineUUID_);
                    this.logTags_ = visitor.visitList(this.logTags_, logGroup.logTags_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= logGroup.bitField0_;
                    }
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    if (!this.logs_.isModifiable()) {
                                        this.logs_ = GeneratedMessageLite.mutableCopy(this.logs_);
                                    }
                                    this.logs_.add((Log) codedInputStream.readMessage(Log.parser(), extensionRegistryLite));
                                } else if (tag == 18) {
                                    this.category_ = codedInputStream.readStringRequireUtf8();
                                } else if (tag == 26) {
                                    this.topic_ = codedInputStream.readStringRequireUtf8();
                                } else if (tag == 34) {
                                    this.source_ = codedInputStream.readStringRequireUtf8();
                                } else if (tag == 42) {
                                    this.machineUUID_ = codedInputStream.readStringRequireUtf8();
                                } else if (tag != 50) {
                                    if (!codedInputStream.skipField(tag)) {
                                    }
                                } else {
                                    if (!this.logTags_.isModifiable()) {
                                        this.logTags_ = GeneratedMessageLite.mutableCopy(this.logTags_);
                                    }
                                    this.logTags_.add((LogTag) codedInputStream.readMessage(LogTag.parser(), extensionRegistryLite));
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    if (PARSER == null) {
                        synchronized (LogGroup.class) {
                            if (PARSER == null) {
                                PARSER = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                            break;
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            LogGroup logGroup = new LogGroup();
            DEFAULT_INSTANCE = logGroup;
            logGroup.makeImmutable();
        }

        public static LogGroup getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LogGroup> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class LogGroupList extends GeneratedMessageLite<LogGroupList, Builder> implements LogGroupListOrBuilder {
        private static final LogGroupList DEFAULT_INSTANCE;
        public static final int LOGGROUPS_FIELD_NUMBER = 1;
        private static volatile Parser<LogGroupList> PARSER;
        private Internal.ProtobufList<LogGroup> logGroups_ = emptyProtobufList();

        private LogGroupList() {
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupListOrBuilder
        public List<LogGroup> getLogGroupsList() {
            return this.logGroups_;
        }

        public List<? extends LogGroupOrBuilder> getLogGroupsOrBuilderList() {
            return this.logGroups_;
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupListOrBuilder
        public int getLogGroupsCount() {
            return this.logGroups_.size();
        }

        @Override // com.tds.common.tracker.entities.LogBean.LogGroupListOrBuilder
        public LogGroup getLogGroups(int i) {
            return this.logGroups_.get(i);
        }

        public LogGroupOrBuilder getLogGroupsOrBuilder(int i) {
            return this.logGroups_.get(i);
        }

        private void ensureLogGroupsIsMutable() {
            if (this.logGroups_.isModifiable()) {
                return;
            }
            this.logGroups_ = GeneratedMessageLite.mutableCopy(this.logGroups_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLogGroups(int i, LogGroup logGroup) {
            Objects.requireNonNull(logGroup);
            ensureLogGroupsIsMutable();
            this.logGroups_.set(i, logGroup);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLogGroups(int i, LogGroup.Builder builder) {
            ensureLogGroupsIsMutable();
            this.logGroups_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogGroups(LogGroup logGroup) {
            Objects.requireNonNull(logGroup);
            ensureLogGroupsIsMutable();
            this.logGroups_.add(logGroup);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogGroups(int i, LogGroup logGroup) {
            Objects.requireNonNull(logGroup);
            ensureLogGroupsIsMutable();
            this.logGroups_.add(i, logGroup);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogGroups(LogGroup.Builder builder) {
            ensureLogGroupsIsMutable();
            this.logGroups_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addLogGroups(int i, LogGroup.Builder builder) {
            ensureLogGroupsIsMutable();
            this.logGroups_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllLogGroups(Iterable<? extends LogGroup> iterable) {
            ensureLogGroupsIsMutable();
            AbstractMessageLite.addAll(iterable, this.logGroups_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLogGroups() {
            this.logGroups_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeLogGroups(int i) {
            ensureLogGroupsIsMutable();
            this.logGroups_.remove(i);
        }

        @Override // com.tds.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.logGroups_.size(); i++) {
                codedOutputStream.writeMessage(1, this.logGroups_.get(i));
            }
        }

        @Override // com.tds.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = 0;
            for (int i2 = 0; i2 < this.logGroups_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.logGroups_.get(i2));
            }
            this.memoizedSerializedSize = iComputeMessageSize;
            return iComputeMessageSize;
        }

        public static LogGroupList parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LogGroupList parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LogGroupList parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LogGroupList parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static LogGroupList parseFrom(InputStream inputStream) throws IOException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogGroupList parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogGroupList parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (LogGroupList) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LogGroupList parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogGroupList) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LogGroupList parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LogGroupList parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LogGroupList) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LogGroupList logGroupList) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(logGroupList);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LogGroupList, Builder> implements LogGroupListOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(LogGroupList.DEFAULT_INSTANCE);
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupListOrBuilder
            public List<LogGroup> getLogGroupsList() {
                return Collections.unmodifiableList(((LogGroupList) this.instance).getLogGroupsList());
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupListOrBuilder
            public int getLogGroupsCount() {
                return ((LogGroupList) this.instance).getLogGroupsCount();
            }

            @Override // com.tds.common.tracker.entities.LogBean.LogGroupListOrBuilder
            public LogGroup getLogGroups(int i) {
                return ((LogGroupList) this.instance).getLogGroups(i);
            }

            public Builder setLogGroups(int i, LogGroup logGroup) {
                copyOnWrite();
                ((LogGroupList) this.instance).setLogGroups(i, logGroup);
                return this;
            }

            public Builder setLogGroups(int i, LogGroup.Builder builder) {
                copyOnWrite();
                ((LogGroupList) this.instance).setLogGroups(i, builder);
                return this;
            }

            public Builder addLogGroups(LogGroup logGroup) {
                copyOnWrite();
                ((LogGroupList) this.instance).addLogGroups(logGroup);
                return this;
            }

            public Builder addLogGroups(int i, LogGroup logGroup) {
                copyOnWrite();
                ((LogGroupList) this.instance).addLogGroups(i, logGroup);
                return this;
            }

            public Builder addLogGroups(LogGroup.Builder builder) {
                copyOnWrite();
                ((LogGroupList) this.instance).addLogGroups(builder);
                return this;
            }

            public Builder addLogGroups(int i, LogGroup.Builder builder) {
                copyOnWrite();
                ((LogGroupList) this.instance).addLogGroups(i, builder);
                return this;
            }

            public Builder addAllLogGroups(Iterable<? extends LogGroup> iterable) {
                copyOnWrite();
                ((LogGroupList) this.instance).addAllLogGroups(iterable);
                return this;
            }

            public Builder clearLogGroups() {
                copyOnWrite();
                ((LogGroupList) this.instance).clearLogGroups();
                return this;
            }

            public Builder removeLogGroups(int i) {
                copyOnWrite();
                ((LogGroupList) this.instance).removeLogGroups(i);
                return this;
            }
        }

        @Override // com.tds.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.$SwitchMap$com$tds$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LogGroupList();
                case 2:
                    return DEFAULT_INSTANCE;
                case 3:
                    this.logGroups_.makeImmutable();
                    return null;
                case 4:
                    return new Builder(anonymousClass1);
                case 5:
                    this.logGroups_ = ((GeneratedMessageLite.Visitor) obj).visitList(this.logGroups_, ((LogGroupList) obj2).logGroups_);
                    GeneratedMessageLite.MergeFromVisitor mergeFromVisitor = GeneratedMessageLite.MergeFromVisitor.INSTANCE;
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag != 10) {
                                    if (!codedInputStream.skipField(tag)) {
                                    }
                                } else {
                                    if (!this.logGroups_.isModifiable()) {
                                        this.logGroups_ = GeneratedMessageLite.mutableCopy(this.logGroups_);
                                    }
                                    this.logGroups_.add((LogGroup) codedInputStream.readMessage(LogGroup.parser(), extensionRegistryLite));
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    if (PARSER == null) {
                        synchronized (LogGroupList.class) {
                            if (PARSER == null) {
                                PARSER = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                            break;
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            LogGroupList logGroupList = new LogGroupList();
            DEFAULT_INSTANCE = logGroupList;
            logGroupList.makeImmutable();
        }

        public static LogGroupList getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LogGroupList> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
