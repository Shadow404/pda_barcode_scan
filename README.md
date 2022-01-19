# barcode_scan

flutter pda 监听优博讯广播方式

# PDA设置
> PDA `输出方式` 必须设置为 `广播模式`

# 初始时监听

```dart
BarcodeScanUtil.instance.listen((value) {
  print('条码内容$value');
});
```

# 离开时销毁

```dart
BarcodeScanUtil.instance.cancel();
```


