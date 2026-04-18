package com.taptap.services.update.download.core.dispatcher;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.taptap.services.update.download.DownloadListener;
import com.taptap.services.update.download.DownloadMonitor;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.OkDownload;
import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CallbackDispatcher {
    private static final String TAG = "CallbackDispatcher";
    private final DownloadListener transmit;
    private final Handler uiHandler;

    CallbackDispatcher(Handler handler, DownloadListener downloadListener) {
        this.uiHandler = handler;
        this.transmit = downloadListener;
    }

    public CallbackDispatcher() {
        Handler handler = new Handler(Looper.getMainLooper());
        this.uiHandler = handler;
        this.transmit = new DefaultTransmitListener(handler);
    }

    public boolean isFetchProcessMoment(DownloadTask downloadTask) {
        long minIntervalMillisCallbackProcess = downloadTask.getMinIntervalMillisCallbackProcess();
        return minIntervalMillisCallbackProcess <= 0 || SystemClock.uptimeMillis() - DownloadTask.TaskHideWrapper.getLastCallbackProcessTs(downloadTask) >= minIntervalMillisCallbackProcess;
    }

    public void endTasksWithError(Collection<DownloadTask> collection, Exception exc) {
        if (collection.size() <= 0) {
            return;
        }
        Util.d(TAG, "endTasksWithError error[" + collection.size() + "] realCause: " + exc);
        Iterator<DownloadTask> it = collection.iterator();
        while (it.hasNext()) {
            DownloadTask next = it.next();
            if (!next.isAutoCallbackToUIThread()) {
                next.getListener().taskEnd(next, EndCause.ERROR, exc);
                it.remove();
            }
        }
        this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.1
            final /* synthetic */ Collection val$errorCollection;
            final /* synthetic */ Exception val$realCause;

            AnonymousClass1(Collection collection2, Exception exc2) {
                collection = collection2;
                exc = exc2;
            }

            @Override // java.lang.Runnable
            public void run() {
                for (DownloadTask downloadTask : collection) {
                    downloadTask.getListener().taskEnd(downloadTask, EndCause.ERROR, exc);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Collection val$errorCollection;
        final /* synthetic */ Exception val$realCause;

        AnonymousClass1(Collection collection2, Exception exc2) {
            collection = collection2;
            exc = exc2;
        }

        @Override // java.lang.Runnable
        public void run() {
            for (DownloadTask downloadTask : collection) {
                downloadTask.getListener().taskEnd(downloadTask, EndCause.ERROR, exc);
            }
        }
    }

    public void endTasks(Collection<DownloadTask> collection, Collection<DownloadTask> collection2, Collection<DownloadTask> collection3) {
        if (collection.size() == 0 && collection2.size() == 0 && collection3.size() == 0) {
            return;
        }
        Util.d(TAG, "endTasks completed[" + collection.size() + "] sameTask[" + collection2.size() + "] fileBusy[" + collection3.size() + "]");
        if (collection.size() > 0) {
            Iterator<DownloadTask> it = collection.iterator();
            while (it.hasNext()) {
                DownloadTask next = it.next();
                if (!next.isAutoCallbackToUIThread()) {
                    next.getListener().taskEnd(next, EndCause.COMPLETED, null);
                    it.remove();
                }
            }
        }
        if (collection2.size() > 0) {
            Iterator<DownloadTask> it2 = collection2.iterator();
            while (it2.hasNext()) {
                DownloadTask next2 = it2.next();
                if (!next2.isAutoCallbackToUIThread()) {
                    next2.getListener().taskEnd(next2, EndCause.SAME_TASK_BUSY, null);
                    it2.remove();
                }
            }
        }
        if (collection3.size() > 0) {
            Iterator<DownloadTask> it3 = collection3.iterator();
            while (it3.hasNext()) {
                DownloadTask next3 = it3.next();
                if (!next3.isAutoCallbackToUIThread()) {
                    next3.getListener().taskEnd(next3, EndCause.FILE_BUSY, null);
                    it3.remove();
                }
            }
        }
        if (collection.size() == 0 && collection2.size() == 0 && collection3.size() == 0) {
            return;
        }
        this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.2
            final /* synthetic */ Collection val$completedTaskCollection;
            final /* synthetic */ Collection val$fileBusyCollection;
            final /* synthetic */ Collection val$sameTaskConflictCollection;

            AnonymousClass2(Collection collection4, Collection collection22, Collection collection32) {
                collection = collection4;
                collection = collection22;
                collection = collection32;
            }

            @Override // java.lang.Runnable
            public void run() {
                for (DownloadTask downloadTask : collection) {
                    downloadTask.getListener().taskEnd(downloadTask, EndCause.COMPLETED, null);
                }
                for (DownloadTask downloadTask2 : collection) {
                    downloadTask2.getListener().taskEnd(downloadTask2, EndCause.SAME_TASK_BUSY, null);
                }
                for (DownloadTask downloadTask3 : collection) {
                    downloadTask3.getListener().taskEnd(downloadTask3, EndCause.FILE_BUSY, null);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ Collection val$completedTaskCollection;
        final /* synthetic */ Collection val$fileBusyCollection;
        final /* synthetic */ Collection val$sameTaskConflictCollection;

        AnonymousClass2(Collection collection4, Collection collection22, Collection collection32) {
            collection = collection4;
            collection = collection22;
            collection = collection32;
        }

        @Override // java.lang.Runnable
        public void run() {
            for (DownloadTask downloadTask : collection) {
                downloadTask.getListener().taskEnd(downloadTask, EndCause.COMPLETED, null);
            }
            for (DownloadTask downloadTask2 : collection) {
                downloadTask2.getListener().taskEnd(downloadTask2, EndCause.SAME_TASK_BUSY, null);
            }
            for (DownloadTask downloadTask3 : collection) {
                downloadTask3.getListener().taskEnd(downloadTask3, EndCause.FILE_BUSY, null);
            }
        }
    }

    public void endTasksWithCanceled(Collection<DownloadTask> collection) {
        if (collection.size() <= 0) {
            return;
        }
        Util.d(TAG, "endTasksWithCanceled canceled[" + collection.size() + "]");
        Iterator<DownloadTask> it = collection.iterator();
        while (it.hasNext()) {
            DownloadTask next = it.next();
            if (!next.isAutoCallbackToUIThread()) {
                next.getListener().taskEnd(next, EndCause.CANCELED, null);
                it.remove();
            }
        }
        this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.3
            final /* synthetic */ Collection val$canceledCollection;

            AnonymousClass3(Collection collection2) {
                collection = collection2;
            }

            @Override // java.lang.Runnable
            public void run() {
                for (DownloadTask downloadTask : collection) {
                    downloadTask.getListener().taskEnd(downloadTask, EndCause.CANCELED, null);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$3 */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ Collection val$canceledCollection;

        AnonymousClass3(Collection collection2) {
            collection = collection2;
        }

        @Override // java.lang.Runnable
        public void run() {
            for (DownloadTask downloadTask : collection) {
                downloadTask.getListener().taskEnd(downloadTask, EndCause.CANCELED, null);
            }
        }
    }

    public DownloadListener dispatch() {
        return this.transmit;
    }

    static class DefaultTransmitListener implements DownloadListener {
        private final Handler uiHandler;

        DefaultTransmitListener(Handler handler) {
            this.uiHandler = handler;
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void taskStart(DownloadTask downloadTask) {
            Util.d(CallbackDispatcher.TAG, "taskStart: " + downloadTask.getId());
            inspectTaskStart(downloadTask);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.1
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass1(DownloadTask downloadTask2) {
                        downloadTask = downloadTask2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().taskStart(downloadTask);
                    }
                });
            } else {
                downloadTask2.getListener().taskStart(downloadTask2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$1 */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass1(DownloadTask downloadTask2) {
                downloadTask = downloadTask2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().taskStart(downloadTask);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void connectTrialStart(DownloadTask downloadTask, Map<String, List<String>> map) {
            Util.d(CallbackDispatcher.TAG, "-----> start trial task(" + downloadTask.getId() + ") " + map);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.2
                    final /* synthetic */ Map val$headerFields;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass2(DownloadTask downloadTask2, Map map2) {
                        downloadTask = downloadTask2;
                        map = map2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().connectTrialStart(downloadTask, map);
                    }
                });
            } else {
                downloadTask2.getListener().connectTrialStart(downloadTask2, map2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$2 */
        class AnonymousClass2 implements Runnable {
            final /* synthetic */ Map val$headerFields;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass2(DownloadTask downloadTask2, Map map2) {
                downloadTask = downloadTask2;
                map = map2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().connectTrialStart(downloadTask, map);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void connectTrialEnd(DownloadTask downloadTask, int i, Map<String, List<String>> map) {
            Util.d(CallbackDispatcher.TAG, "<----- finish trial task(" + downloadTask.getId() + ") code[" + i + "]" + map);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.3
                    final /* synthetic */ Map val$headerFields;
                    final /* synthetic */ int val$responseCode;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass3(DownloadTask downloadTask2, int i2, Map map2) {
                        downloadTask = downloadTask2;
                        i = i2;
                        map = map2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().connectTrialEnd(downloadTask, i, map);
                    }
                });
            } else {
                downloadTask2.getListener().connectTrialEnd(downloadTask2, i2, map2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$3 */
        class AnonymousClass3 implements Runnable {
            final /* synthetic */ Map val$headerFields;
            final /* synthetic */ int val$responseCode;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass3(DownloadTask downloadTask2, int i2, Map map2) {
                downloadTask = downloadTask2;
                i = i2;
                map = map2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().connectTrialEnd(downloadTask, i, map);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void downloadFromBeginning(DownloadTask downloadTask, BreakpointInfo breakpointInfo, ResumeFailedCause resumeFailedCause) {
            Util.d(CallbackDispatcher.TAG, "downloadFromBeginning: " + downloadTask.getId());
            inspectDownloadFromBeginning(downloadTask, breakpointInfo, resumeFailedCause);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.4
                    final /* synthetic */ ResumeFailedCause val$cause;
                    final /* synthetic */ BreakpointInfo val$info;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass4(DownloadTask downloadTask2, BreakpointInfo breakpointInfo2, ResumeFailedCause resumeFailedCause2) {
                        downloadTask = downloadTask2;
                        breakpointInfo = breakpointInfo2;
                        resumeFailedCause = resumeFailedCause2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().downloadFromBeginning(downloadTask, breakpointInfo, resumeFailedCause);
                    }
                });
            } else {
                downloadTask2.getListener().downloadFromBeginning(downloadTask2, breakpointInfo2, resumeFailedCause2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$4 */
        class AnonymousClass4 implements Runnable {
            final /* synthetic */ ResumeFailedCause val$cause;
            final /* synthetic */ BreakpointInfo val$info;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass4(DownloadTask downloadTask2, BreakpointInfo breakpointInfo2, ResumeFailedCause resumeFailedCause2) {
                downloadTask = downloadTask2;
                breakpointInfo = breakpointInfo2;
                resumeFailedCause = resumeFailedCause2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().downloadFromBeginning(downloadTask, breakpointInfo, resumeFailedCause);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void downloadFromBreakpoint(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
            Util.d(CallbackDispatcher.TAG, "downloadFromBreakpoint: " + downloadTask.getId());
            inspectDownloadFromBreakpoint(downloadTask, breakpointInfo);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.5
                    final /* synthetic */ BreakpointInfo val$info;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass5(DownloadTask downloadTask2, BreakpointInfo breakpointInfo2) {
                        downloadTask = downloadTask2;
                        breakpointInfo = breakpointInfo2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().downloadFromBreakpoint(downloadTask, breakpointInfo);
                    }
                });
            } else {
                downloadTask2.getListener().downloadFromBreakpoint(downloadTask2, breakpointInfo2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$5 */
        class AnonymousClass5 implements Runnable {
            final /* synthetic */ BreakpointInfo val$info;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass5(DownloadTask downloadTask2, BreakpointInfo breakpointInfo2) {
                downloadTask = downloadTask2;
                breakpointInfo = breakpointInfo2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().downloadFromBreakpoint(downloadTask, breakpointInfo);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void connectStart(DownloadTask downloadTask, int i, Map<String, List<String>> map) {
            Util.d(CallbackDispatcher.TAG, "-----> start connection task(" + downloadTask.getId() + ") block(" + i + ") " + map);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.6
                    final /* synthetic */ int val$blockIndex;
                    final /* synthetic */ Map val$requestHeaderFields;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass6(DownloadTask downloadTask2, int i2, Map map2) {
                        downloadTask = downloadTask2;
                        i = i2;
                        map = map2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().connectStart(downloadTask, i, map);
                    }
                });
            } else {
                downloadTask2.getListener().connectStart(downloadTask2, i2, map2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$6 */
        class AnonymousClass6 implements Runnable {
            final /* synthetic */ int val$blockIndex;
            final /* synthetic */ Map val$requestHeaderFields;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass6(DownloadTask downloadTask2, int i2, Map map2) {
                downloadTask = downloadTask2;
                i = i2;
                map = map2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().connectStart(downloadTask, i, map);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void connectEnd(DownloadTask downloadTask, int i, int i2, Map<String, List<String>> map) {
            Util.d(CallbackDispatcher.TAG, "<----- finish connection task(" + downloadTask.getId() + ") block(" + i + ") code[" + i2 + "]" + map);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.7
                    final /* synthetic */ int val$blockIndex;
                    final /* synthetic */ Map val$requestHeaderFields;
                    final /* synthetic */ int val$responseCode;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass7(DownloadTask downloadTask2, int i3, int i22, Map map2) {
                        downloadTask = downloadTask2;
                        i = i3;
                        i = i22;
                        map = map2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().connectEnd(downloadTask, i, i, map);
                    }
                });
            } else {
                downloadTask2.getListener().connectEnd(downloadTask2, i3, i22, map2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$7 */
        class AnonymousClass7 implements Runnable {
            final /* synthetic */ int val$blockIndex;
            final /* synthetic */ Map val$requestHeaderFields;
            final /* synthetic */ int val$responseCode;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass7(DownloadTask downloadTask2, int i3, int i22, Map map2) {
                downloadTask = downloadTask2;
                i = i3;
                i = i22;
                map = map2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().connectEnd(downloadTask, i, i, map);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void fetchStart(DownloadTask downloadTask, int i, long j) {
            Util.d(CallbackDispatcher.TAG, "fetchStart: " + downloadTask.getId());
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.8
                    final /* synthetic */ int val$blockIndex;
                    final /* synthetic */ long val$contentLength;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass8(DownloadTask downloadTask2, int i2, long j2) {
                        downloadTask = downloadTask2;
                        i = i2;
                        j = j2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().fetchStart(downloadTask, i, j);
                    }
                });
            } else {
                downloadTask2.getListener().fetchStart(downloadTask2, i2, j2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$8 */
        class AnonymousClass8 implements Runnable {
            final /* synthetic */ int val$blockIndex;
            final /* synthetic */ long val$contentLength;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass8(DownloadTask downloadTask2, int i2, long j2) {
                downloadTask = downloadTask2;
                i = i2;
                j = j2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().fetchStart(downloadTask, i, j);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void fetchProgress(DownloadTask downloadTask, int i, long j) {
            if (downloadTask.getMinIntervalMillisCallbackProcess() > 0) {
                DownloadTask.TaskHideWrapper.setLastCallbackProcessTs(downloadTask, SystemClock.uptimeMillis());
            }
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.9
                    final /* synthetic */ int val$blockIndex;
                    final /* synthetic */ long val$increaseBytes;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass9(DownloadTask downloadTask2, int i2, long j2) {
                        downloadTask = downloadTask2;
                        i = i2;
                        j = j2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().fetchProgress(downloadTask, i, j);
                    }
                });
            } else {
                downloadTask2.getListener().fetchProgress(downloadTask2, i2, j2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$9 */
        class AnonymousClass9 implements Runnable {
            final /* synthetic */ int val$blockIndex;
            final /* synthetic */ long val$increaseBytes;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass9(DownloadTask downloadTask2, int i2, long j2) {
                downloadTask = downloadTask2;
                i = i2;
                j = j2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().fetchProgress(downloadTask, i, j);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void fetchEnd(DownloadTask downloadTask, int i, long j) {
            Util.d(CallbackDispatcher.TAG, "fetchEnd: " + downloadTask.getId());
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.10
                    final /* synthetic */ int val$blockIndex;
                    final /* synthetic */ long val$contentLength;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass10(DownloadTask downloadTask2, int i2, long j2) {
                        downloadTask = downloadTask2;
                        i = i2;
                        j = j2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().fetchEnd(downloadTask, i, j);
                    }
                });
            } else {
                downloadTask2.getListener().fetchEnd(downloadTask2, i2, j2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$10 */
        class AnonymousClass10 implements Runnable {
            final /* synthetic */ int val$blockIndex;
            final /* synthetic */ long val$contentLength;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass10(DownloadTask downloadTask2, int i2, long j2) {
                downloadTask = downloadTask2;
                i = i2;
                j = j2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().fetchEnd(downloadTask, i, j);
            }
        }

        @Override // com.taptap.services.update.download.DownloadListener
        public void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc) {
            if (endCause == EndCause.ERROR) {
                Util.d(CallbackDispatcher.TAG, "taskEnd: " + downloadTask.getId() + " " + endCause + " " + exc);
            }
            inspectTaskEnd(downloadTask, endCause, exc);
            if (downloadTask.isAutoCallbackToUIThread()) {
                this.uiHandler.post(new Runnable() { // from class: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher.DefaultTransmitListener.11
                    final /* synthetic */ EndCause val$cause;
                    final /* synthetic */ Exception val$realCause;
                    final /* synthetic */ DownloadTask val$task;

                    AnonymousClass11(DownloadTask downloadTask2, EndCause endCause2, Exception exc2) {
                        downloadTask = downloadTask2;
                        endCause = endCause2;
                        exc = exc2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        downloadTask.getListener().taskEnd(downloadTask, endCause, exc);
                    }
                });
            } else {
                downloadTask2.getListener().taskEnd(downloadTask2, endCause2, exc2);
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.download.core.dispatcher.CallbackDispatcher$DefaultTransmitListener$11 */
        class AnonymousClass11 implements Runnable {
            final /* synthetic */ EndCause val$cause;
            final /* synthetic */ Exception val$realCause;
            final /* synthetic */ DownloadTask val$task;

            AnonymousClass11(DownloadTask downloadTask2, EndCause endCause2, Exception exc2) {
                downloadTask = downloadTask2;
                endCause = endCause2;
                exc = exc2;
            }

            @Override // java.lang.Runnable
            public void run() {
                downloadTask.getListener().taskEnd(downloadTask, endCause, exc);
            }
        }

        void inspectDownloadFromBreakpoint(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
            DownloadMonitor monitor = OkDownload.with().getMonitor();
            if (monitor != null) {
                monitor.taskDownloadFromBreakpoint(downloadTask, breakpointInfo);
            }
        }

        void inspectDownloadFromBeginning(DownloadTask downloadTask, BreakpointInfo breakpointInfo, ResumeFailedCause resumeFailedCause) {
            DownloadMonitor monitor = OkDownload.with().getMonitor();
            if (monitor != null) {
                monitor.taskDownloadFromBeginning(downloadTask, breakpointInfo, resumeFailedCause);
            }
        }

        void inspectTaskStart(DownloadTask downloadTask) {
            DownloadMonitor monitor = OkDownload.with().getMonitor();
            if (monitor != null) {
                monitor.taskStart(downloadTask);
            }
        }

        void inspectTaskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc) {
            DownloadMonitor monitor = OkDownload.with().getMonitor();
            if (monitor != null) {
                monitor.taskEnd(downloadTask, endCause, exc);
            }
        }
    }
}
