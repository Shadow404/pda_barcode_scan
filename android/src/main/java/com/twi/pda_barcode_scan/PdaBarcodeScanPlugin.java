package com.twi.pda_barcode_scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * PdaBarcodeScanPlugin
 */
public class PdaBarcodeScanPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    private static final String CHANNEL = "com.twi.barcode_scan/plugin";

    /// 优博讯 广播
    private static final String YBX_SCAN_ACTION = "android.intent.ACTION_DECODE_DATA";
    /// 自定义 广播
    private static final String CUSTOM_ACTION = "com.twi.pro";

    private static final String CHARGING_CHANNEL = "barcode_scan_flutter";

    public PdaBarcodeScanPlugin() {

    }


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL);
        PdaBarcodeScanPlugin barcodeScanPlugin = new PdaBarcodeScanPlugin(flutterPluginBinding.getApplicationContext());
        channel.setMethodCallHandler(barcodeScanPlugin);

        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), CHARGING_CHANNEL);
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {

            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                PdaBarcodeScanPlugin.eventSink = events;
            }

            @Override
            public void onCancel(Object arguments) {
                // flutterPluginBinding.getApplicationContext().unregisterReceiver(barcodeScanPlugin);
            }
        });

    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


    private static EventChannel.EventSink eventSink;


    private PdaBarcodeScanPlugin(Context activity) {
        IntentFilter ybxIntentFilter = new IntentFilter();
        ybxIntentFilter.addAction(YBX_SCAN_ACTION);
        ybxIntentFilter.setPriority(Integer.MAX_VALUE);
        activity.registerReceiver(scanReceiver, ybxIntentFilter);

        IntentFilter customIntentFilter = new IntentFilter();
        customIntentFilter.addAction(CUSTOM_ACTION);
        customIntentFilter.setPriority(Integer.MAX_VALUE);
        activity.registerReceiver(scanReceiver, customIntentFilter);
    }

    private static final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionName = intent.getAction();
            if (YBX_SCAN_ACTION.equals(actionName)) {
                eventSink.success(intent.getStringExtra("barcode_string"));
            }
            if (CUSTOM_ACTION.equals(actionName)) {
                eventSink.success(intent.getStringExtra("twi"));
            }

        }
    };

}
