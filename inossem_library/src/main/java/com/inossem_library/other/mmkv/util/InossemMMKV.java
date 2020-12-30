package com.inossem_library.other.mmkv.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.other.mmkv.constant.InossemMMKVConstant;
import com.tencent.mmkv.MMKV;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/**
 * @author 付中正
 * @time 2020/7/24 16:13
 * @email zhongzheng.fu@inossem.com
 */
public class InossemMMKV {
    private String tableName;
    private ExecutorService executorService;
    //用于线程安全，因为线程模型和mmkv底层封装，在写入时 是有锁且线程安全的，所以这里我们只负责计数
    private int putTaskNum = 0;
    private static String path;
    private boolean lock = false;
    /**
     * SINGLE_PROCESS_MODE = 1;
     * MULTI_PROCESS_MODE = 2;
     * CONTEXT_MODE_MULTI_PROCESS = 4;
     * ASHMEM_MODE = 8;
     * <p>
     * 暂时写死，如有需要可写Contents里
     */
    private int mode = 1;

    public static void initialize(Context context) {
        MMKV.initialize(context);
        path = MMKV.getRootDir();
    }

    private InossemMMKV(String table) {
        executorService = Executors.newSingleThreadExecutor();
        tableName = table;
        if (tableName == null) {
            tableName = InossemMMKVConstant.TABLE;
        }
    }

    /**
     * 单例模式 保证线程安全 待优化问题 保证线程安全的时候 在面对多fragment 并发请求时 效率明显降低
     * <p>
     * <p>
     * class CScopedLock {
     * NSRecursiveLock *m_oLock;
     * <p>
     * public:
     * CScopedLock(NSRecursiveLock *oLock) : m_oLock(oLock) { [m_oLock lock]; }    // 初始化时加锁
     * <p>
     * ~CScopedLock() {    // 析构时解锁
     * [m_oLock unlock];
     * m_oLock = nil;
     * }
     * };
     * 上述代码可以看出 ，MMKV 本身是线程安全的。但他只在写入的时候加锁保证线程安全，读取并不会。
     * <p>
     * 结合项目实际情况，在Activity中提交信息时，需要fragment写入数据，作为提交数据基础，为保证读取时是写入后的数据
     * <p>
     * 加入了双重校验的单例模式 ，来保证线程安全。
     * <p>
     * 毕竟线性前进 和 并发处理 比较起来 执行效率有很大区别，
     * <p>
     * 所以 普通的get 方法也并未删除 可根据项目实际情况 ，选择是否需要单例模式。
     */
    private volatile static InossemMMKV inossemMMKV = null;

    public static InossemMMKV get(String table) {
        if (inossemMMKV == null) {
            synchronized (InossemMMKV.class) {
                if (inossemMMKV == null) {
                    inossemMMKV = new InossemMMKV(table);
                } else {
                    if (table == null) {
                        inossemMMKV.tableName = InossemMMKVConstant.TABLE;
                    } else {
                        inossemMMKV.tableName = table;
                    }
                }
            }
        }
        return inossemMMKV;
    }

    public static InossemMMKV get() {
        return get(null);
    }

    /**
     * 正常的 get方法 可根据实际情况 自行选择
     *
     * @return
     */
//    public static InossemMMKV get(String table) {
//        return new InossemMMKV(table);
//    }
//
//    public static InossemMMKV get() {
//        return new InossemMMKV();
//    }

    /**
     * 或者 做判断处理 (不建议)
     */
//    public static InossemMMKV get(String table, boolean single) {
//        if (single) {
//            if (inossemMMKV == null) {
//                synchronized (InossemMMKV.class) {
//                    if (inossemMMKV == null) {
//                        inossemMMKV = new InossemMMKV(table);
//                    } else {
//                        inossemMMKV.tableName = table;
//                    }
//                }
//            }
//            return inossemMMKV;
//        } else {
//            return new InossemMMKV(table);
//        }
//    }
//
//    public static InossemMMKV get(boolean single) {
//        return get(null, single);
//    }
    private MMKV getDefault() {
        return MMKV.mmkvWithID(tableName, mode, InossemMMKVConstant.KEY, path);
    }

    public void removeValueForKey(String key) {
        getDefault().removeValueForKey(key);
    }

    public void removeAll() {
        getDefault().clearAll();
    }

    public <T> T get(String kye, Class<T> tClass) {
        return get(kye, tClass, null);
    }

    public <T> void put(String key, T t) {
        put(key, t, null);
    }


    /**
     * @param key      数据Key
     * @param callBack 主线程回调如不需要可传null :如当前已在子线程 不需要主线程回调的情况
     * @param classOfT 返回数据类型
     * @param <T>      返回数据
     */
    public <T> T get(String key, Class<T> classOfT, MMKVCallBack callBack) {
        T t = null;
        if (callBack == null) {
            long time = System.currentTimeMillis();
            while (lock) {
                long lockTime = System.currentTimeMillis() - time;
//                if (lockTime > 5000) {
//                    return null;
//                }
            }
            if (classOfT.equals(Integer.class)) {
                t = (T) Integer.valueOf(getDefault().decodeInt(key, 0));
            } else if (classOfT.equals(Long.class)) {
                t = (T) Long.valueOf(getDefault().decodeLong(key, 0));
            } else if (classOfT.equals(Float.class)) {
                t = (T) Float.valueOf(getDefault().decodeFloat(key, 0));
            } else if (classOfT.equals(Boolean.class)) {
                t = (T) Boolean.valueOf(getDefault().decodeBool(key, false));
            } else if (classOfT.equals(byte[].class)) {
                t = (T) getDefault().decodeBytes(key, null);
            } else if (classOfT.equals(Double.class)) {
                t = (T) Double.valueOf(getDefault().decodeDouble(key, 0));
            } else if (classOfT.equals(String.class)) {
                t = (T) getDefault().decodeString(key, "");
            } else if (classOfT.equals(Parcelable.class)) {
                t = getDefault().decodeParcelable(key, null);
            } else if (classOfT.equals(Set.class)) {
                t = (T) getDefault().decodeStringSet(key, null);
            } else {
                String json = getDefault().decodeString(key);
                t = new Gson().fromJson(json, classOfT);
            }
            return t;
        } else {
            executorService.execute(new AsyRunnable<T>(callBack) {
                @Override
                protected T matRun() {
                    T tt = null;
                    if (classOfT.equals(Integer.class)) {
                        tt = (T) Integer.valueOf(getDefault().decodeInt(key, 0));
                    } else if (classOfT.equals(Long.class)) {
                        tt = (T) Long.valueOf(getDefault().decodeLong(key, 0));
                    } else if (classOfT.equals(Float.class)) {
                        tt = (T) Float.valueOf(getDefault().decodeFloat(key, 0));
                    } else if (classOfT.equals(Boolean.class)) {
                        tt = (T) Boolean.valueOf(getDefault().decodeBool(key, false));
                    } else if (classOfT.equals(byte[].class)) {
                        tt = (T) getDefault().decodeBytes(key, null);
                    } else if (classOfT.equals(Double.class)) {
                        tt = (T) Double.valueOf(getDefault().decodeDouble(key, 0));
                    } else if (classOfT.equals(String.class)) {
                        tt = (T) getDefault().decodeString(key, "");
                    } else if (classOfT.equals(Parcelable.class)) {
                        tt = getDefault().decodeParcelable(key, null);
                    } else if (classOfT.equals(Set.class)) {
                        tt = (T) getDefault().decodeStringSet(key, null);
                    } else {
                        String json = getDefault().decodeString(key);
                        tt = new Gson().fromJson(json, classOfT);
                    }
                    return tt;
                }
            });
        }
        return t;
    }

    /**
     * @param key
     * @param t        数据类型
     * @param callBack 主线程回调不需要可传Null
     * @param <T>
     */
    public <T> void put(String key, T t, MMKVCallBack callBack) {
        //计数
        putTaskNum = putTaskNum + 1;
        //写锁
        if (!lock) {
            lock = true;
        }
        executorService.execute(new AsyRunnable(callBack) {
            @Override
            protected T matRun() {
                boolean isSuccess = false;
                try {
                    if (t instanceof Integer) {
                        isSuccess = getDefault().encode(key, (Integer) t);
                    } else if (t instanceof Long) {
                        isSuccess = getDefault().encode(key, (Long) t);
                    } else if (t instanceof Float) {
                        isSuccess = getDefault().encode(key, (Float) t);
                    } else if (t instanceof Boolean) {
                        isSuccess = getDefault().encode(key, (Boolean) t);
                    } else if (t instanceof byte[]) {
                        isSuccess = getDefault().encode(key, (byte[]) t);
                    } else if (t instanceof Double) {
                        isSuccess = getDefault().encode(key, (Double) t);
                    } else if (t instanceof String) {
                        isSuccess = getDefault().encode(key, (String) t);
                    } else if (t instanceof Parcelable) {
                        isSuccess = getDefault().encode(key, (Parcelable) t);
                    } else if (t instanceof Set) {
                        isSuccess = getDefault().encode(key, (Set<String>) t);
                    } else {
                        isSuccess = getDefault().encode(key, new Gson().toJson(t));
                    }
                } catch (Exception e) {

                } finally {
                    //写入完成
                    putTaskNum--;
                    if (putTaskNum == 0) {
                        lock = false;
                    }
                }

                return t;
            }
        });
    }

    private abstract class AsyRunnable<T> implements Runnable {
        private MMKVCallBack callBack;
        private T t;
        private Object o = null;

        public AsyRunnable(MMKVCallBack callBack) {
            this.callBack = callBack;
        }

        protected abstract T matRun();

        @Override
        public void run() {
            if (callBack == null) {
                matRun();
                return;
            } else {
                callBack.show();
                t = matRun();
                o = callBack.toDo(t);

            }

            if (Looper.myLooper() != Looper.getMainLooper()) {
                // If we finish marking off of the main thread, we need to
                // actually do it on the main thread to ensure correct ordering.
                Handler mainThread = new Handler(Looper.getMainLooper());
                mainThread.post(() -> callBack.onMainResult(o));
                return;
            }
        }
    }

    /**
     * @param <T> MMKV读取的数据类型
     * @param <K> 数据处理后 需要返回的数据类型
     */
    public interface MMKVCallBack<T, K> {
        /**
         * 子线程 处理数据
         *
         * @param t 需处理数据
         * @return k 处理后数据
         */
        @NonNull
        K toDo(T t);

        /**
         * 主线程回调数据
         *
         * @param k 处理后 的数据
         */
        @NonNull
        void onMainResult(K k);


        /**
         * 显示Dialog
         */
        void show();

    }

    /**
     * 实现类 不需要toDo处理数据时
     *
     * @param <T> 需读取的数据类
     */
    public abstract static class OnResultCallBack<T> extends OnToDoResultCallBack<T, T> {
        public OnResultCallBack(@NonNull Dialog Dialog) {
            super(Dialog);
        }

        @Override
        public T asyncToDo(T o) {
            return o;
        }
    }

    /**
     * 实现类 需要toDo处理数据时
     *
     * @param <T> 读取的数据类
     * @param <K> 处理后返回的数据类
     */
    public abstract static class OnToDoResultCallBack<T, K> implements MMKVCallBack<T, K> {

        public OnToDoResultCallBack(@NonNull Dialog Dialog) {
            progressDialog = Dialog;
            if (null == progressDialog) {
                throw new InossemException(ExceptionEnum.NULL_PARAMS, "Dialog不能为空");
            }
            //如果dialog没有显示的话 显示dialog
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        private Dialog progressDialog;

        //子线程处理数据
        public abstract K asyncToDo(T t);

        //IO线程回传数据
        public abstract void mainResult(K k);

        @NonNull
        @Override
        public K toDo(T t) {
            //如果dialog没有显示的话 显示dialog
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            return asyncToDo(t);
        }

        @NonNull
        @Override
        public void onMainResult(K k) {
            //如果dialog显示的话 关闭dialog
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            mainResult(k);
        }

        @Override
        public void show() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }
}
