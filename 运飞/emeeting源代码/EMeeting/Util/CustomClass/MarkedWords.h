//
//  MarkedWords.h
//  RedPacket
//
//  Created by efutureinfo on 15/12/8.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^finishBlock)(void);

@interface MarkedWords : UIView

/**
 *在某个视图上显示提示语
 *
 *@param message 提示语
 *@param view 提示语要显示的视图
*/
+ (void)showMarkedWordsWithMessage:(NSString *)message addToView:(UIView *)view;

/**
 *在某个视图上显示提示语，完成后执行某个操作
 *
 *@param message 提示语
 *@param view 提示语要显示的视图
 *@param block 提示语隐藏后回调
*/
+ (void)showMarkedWordsWithMessage:(NSString *)message addToView:(UIView *)view finished:(finishBlock)block;

@end
