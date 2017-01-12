//
//  NSDate+LJFDate.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NSDate+Extension.h"

@interface NSDate (LJFDate)

+ (NSString *)monthAndDayChineseStringWithSelectTime:(NSString *)selectTime;

+ (NSString *)getImterValTimeWithSelecttime:(NSString *)selectTime;

/*
 * 获取默认选择的时间
 * 返回格式 YYYY-MM-dd HH:mm-HH:mm
 */
+ (NSString *)getDefaultSelectedStringWithNowDate:(NSDate *)date;


/*
 * 获取年月日时间
 * selectedTime格式 YYYY-MM-dd HH:mm-HH:mm
 */
+ (NSDate *)getDateWithSelectTime:(NSString *)selectedTime;

/*
 * 获取后一天选择时间
 * 返回格式 YYYY-MM-dd HH:mm-HH:mm
 */
+ (NSString *)getOffsetDaySelectedStringWithNowSelectedTime:(NSString *)nowSelectedTime offset:(int)offset;

/*
 * 获取YYYY-MM-DD时间
 */
+ (NSString *)getymdStrWithSelectedTime:(NSString *)selectedTime;

+ (NSDate *)getymdDateWithSelectedTime:(NSString *)selectedTime;

/*
 * 获取开始时间
 */
+ (NSString *)getBeginDateWithSelectedTime:(NSString *)selectedTime;

/*
 * 获取结束时间
 */
+ (NSString *)getEndDateWithSelectedTime:(NSString *)selectedTime;


+ (NSString *)getDefaultSelectedStringWithNowDate:(NSDate *)date DefualtTimeLong:(NSString *)timeLong;

+ (NSInteger)getBeginIndexWithBegindateStr:(NSString *)begindate;

+ (NSInteger)getEndIndexWithEndDateStr:(NSString *)enddate;

+ (NSString *)getSelectTimeWithBeginIndex:(NSInteger)beginIndex andEndIndex:(NSInteger)endIndex andSelectTime:(NSString *)selectTime;

+ (NSInteger)getCanOffsetIndexWithToday:(NSDate *)today;

+ (BOOL)canscheduleMeetingWithNowDate:(NSDate *)nowDate;

+ (void)getTimestampWithSystemDate:(NSDate *)systemDate ServerDate:(NSDate *)serverDate;

+ (NSDate *)getServerDate;

+ (BOOL)canShakeWithNowDate:(NSDate *)nowDate;

+ (NSString *)getWeekdayWithDate:(NSDate *)date;

+ (BOOL)checkAreadySelectTime:(NSString *)selectTime;

@end
