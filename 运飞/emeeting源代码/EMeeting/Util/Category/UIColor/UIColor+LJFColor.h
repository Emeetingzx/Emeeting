//
//  UIColor+LJFColor.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIColor (LJFColor)

+ (UIColor *)colorWithRed:(NSInteger)red green:(NSInteger)green blue:(NSInteger)blue;

/**
 *  16进制转uicolor
 *
 *  @param color @"#FFFFFF" ,@"OXFFFFFF" ,@"FFFFFF"
 *
 *  @return uicolor
 */
+ (UIColor *)colorWithHexString:(NSString *)color;

@end
