//
//  RegularTool.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RegularTool : NSObject
//邮箱
+ (BOOL) validateEmail:(NSString *)email;

//手机号码验证
+ (BOOL) validateMobile:(NSString *)mobile;

//固话验证
+(BOOL) validateTelphone:(NSString *)telphone;
//GK号验证
+ (BOOL) validateGKNum:(NSString *)gKNum;

//车牌号验证
+ (BOOL) validateCarNo:(NSString *)carNo;

//车型
+ (BOOL) validateCarType:(NSString *)CarType;

//用户名
+ (BOOL) validateUserName:(NSString *)name;

//密码
+ (BOOL) validatePassword:(NSString *)passWord;

//昵称
+ (BOOL) validateNickname:(NSString *)nickname;

//身份证号
+ (BOOL) validateIdentityCard: (NSString *)identityCard;

+ (BOOL) validateNumber:(NSString *)number;

@end
