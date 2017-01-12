//
//  ProgressView.h
//  RedPacket
//
//  Created by efutureinfo.cn on 15/12/17.
//  Copyright © 2015年 itp. All rights reserved.
//
#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface ProgressView : NSObject

+(void)showHUDAddedTo:(UIView *) view ProgressText:(NSString *)progressText;
+(void)showTextHUDAddedTo:(UIView *) view ProgressText:(NSString *)progressTex;
+(void)showHUDAddedToProgress:(UIView *) view ProgressText:(NSString *)progressTex;
+ (void)showCustomProgressViewAddTo:(UIView *)view;
+ (void)hiddenCustomProgressViewAddTo:(UIView *)view;
@end
