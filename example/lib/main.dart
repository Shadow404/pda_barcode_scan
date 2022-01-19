import 'package:pda_barcode_scan/barcode_scan_util.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  /// 条码内容
  String barcodeContent = "";

  @override
  void initState() {
    super.initState();

    /// 初始化时监听广播
    _onListen();
  }

  @override
  void dispose() {
    super.dispose();

    /// 离开时销毁
    _onCancel();
  }

  void _onListen() {
    BarcodeScanUtil.instance.listen((value) {
      setBarcodeContent(value);
    });
  }

  void _onCancel() {
    BarcodeScanUtil.instance.cancel();
  }

  void setBarcodeContent(String value) {
    setState(() {
      barcodeContent = value;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('监听PDA广播模式'),
        ),
        body: Center(
          child: Column(
            children: [
              const SizedBox(
                height: 30,
              ),
              Text('条码内容$barcodeContent'),
              const SizedBox(
                height: 30,
              ),
              ElevatedButton(
                onPressed: () {
                  _onListen();
                },
                child: const Text('开始监听'),
              ),
              ElevatedButton(
                onPressed: () {
                  _onCancel();
                },
                child: const Text('停止监听'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
