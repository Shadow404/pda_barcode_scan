import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BarcodeScanUtil {
  static BarcodeScanUtil get instance => _instance;

  static final BarcodeScanUtil _instance = BarcodeScanUtil();

  EventChannel eventChannel = const EventChannel('barcode_scan_flutter');

  StreamSubscription? streamSubscription;
  Stream? stream;

  Stream start() {
    stream ??= eventChannel.receiveBroadcastStream();
    return stream!;
  }

  /// 监听扫码数据
  void listen(ValueChanged<String> codeHandle) {
    streamSubscription = start().listen((event) {
      if (event != null) {
        codeHandle.call(event.toString());
      }
    });
  }

  /// 关闭监听q
  void cancel() {
    if (streamSubscription != null) {
      streamSubscription?.cancel();
    }
  }
}
