//
//  Tools.h
//  test
//
//  Created by efutureinfo on 15/12/28.
//  Copyright © 2015年 efutureinfo. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/**
 *设备宽度
 */
#define APPW [UIScreen mainScreen].bounds.size.width

/**
 *设备高度
 */
#define APPH [UIScreen mainScreen].bounds.size.height

/**
 *判断设备
 */
#define IPHONE4 APPH==480  //iPhone4 和iPhone4s
#define IPHONE5 APPH==568  //iPhone5 和iPhone5s
#define IPHONE6 APPH==667  //iPhone6 和iPhone6s
#define IPHONE6P APPH==736 //iPhone6Plus 和iPhone6s Plus

/**
 *设置rgb颜色
 *@prame a 透明度
 */
#define RGBA(r,g,b,a)      [UIColor colorWithRed:(float)r/255.0f green:(float)g/255.0f blue:(float)b/255.0f alpha:a]


@interface Tools : NSObject

/**
 *计算中文文字宽度
 *
 *@prame string 文字
 *@prame font 文字大小
 *@prame height 文字高度
 */
+(CGFloat)widthOfString:(NSString *)string font:(UIFont *)font height:(CGFloat)height;

/**
 *计算中文文字高度
 *
 *@prame string 文字
 *@prame font 文字大小
 *@prame width 文字宽度
 */
+(CGFloat)heightOfString:(NSString *)string font:(UIFont *)font width:(CGFloat)width;

/**
 *身份证校验
 *
 *@prame identityCard 身份证号码
 *符合返回YES，不符合返回NO
 */
+ (BOOL) justIdentityCard: (NSString *)identityCard;

/**
 *用户名校验
 *
 *@prame name 用户名
 *符合返回YES，不符合返回NO
 */
+ (BOOL) justUserName:(NSString *)name;

/**
 *密码校验
 *
 *@prame passWord 密码
 *符合返回YES，不符合返回NO
 */
+ (BOOL) justPassword:(NSString *)passWord;

/**
 *电话号码校验
 *
 *@prame mobile 电话号码
 *符合返回YES，不符合返回NO
 */
+ (BOOL) justMobile:(NSString *)mobile;

/**
 *邮箱校验
 *
 *@prame email 邮箱
 *符合返回YES，不符合返回NO
 */
+ (BOOL) justEmail:(NSString *)email;




@end
