#import "PdaBarcodeScanPlugin.h"
#if __has_include(<pda_barcode_scan/pda_barcode_scan-Swift.h>)
#import <pda_barcode_scan/pda_barcode_scan-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "pda_barcode_scan-Swift.h"
#endif

@implementation PdaBarcodeScanPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPdaBarcodeScanPlugin registerWithRegistrar:registrar];
}
@end
