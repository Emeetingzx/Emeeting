//
//  ScanCodeViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/6.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>
#import <AudioToolbox/AudioToolbox.h>
@interface ScanCodeViewController : UIViewController<AVCaptureMetadataOutputObjectsDelegate>

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *lineImageViewcenterY;
@property (weak, nonatomic) IBOutlet UIView *maskView;

@end
