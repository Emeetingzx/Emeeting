//
//  NSDate+LJFDate.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "NSDate+LJFDate.h"


@implementation NSDate (LJFDate)

+ (NSString *)monthAndDayChineseStringWithSelectTime:(NSString *)selectTime
{
    NSDate *date = [NSDate dateWithString:[selectTime substringToIndex:10] format:[NSDate ymdFormat]];
    
    return [NSString stringWithFormat:@"%ld月%ld日",(unsigned long)[NSDate month:date],(unsigned long)[NSDate day:date]];
}

+ (NSString *)getImterValTimeWithSelecttime:(NSString *)selectTime
{
    return [selectTime substringFromIndex:11];
}

+ (NSString *)getDefaultSelectedStringWithNowDate:(NSDate *)date
{
    NSInteger nowhour = [NSDate hour:date];
    
    if (nowhour<8||nowhour>=20)
    {
        NSString *nextdate = [NSDate stringWithDate:[NSDate offsetDays:1 fromDate:date] format:[NSDate ymdFormat]];
        
        return [NSString stringWithFormat:@"%@ 08:00-10:00",nextdate];
    }else if (nowhour == 19)
    {
        NSString *ymd = [NSDate stringWithDate:date format:[NSDate ymdFormat]];
        
        return [NSString stringWithFormat:@"%@ 20:00-21:00",ymd];
    }
    else
    {
        NSString *ymd = [NSDate stringWithDate:date format:[NSDate ymdFormat]];
        
        NSDate *nextHourDate = [date offsetHours:1];
        NSString *nextHourStr = [NSDate hour:nextHourDate] < 10?[NSString stringWithFormat:@"0%lu",(unsigned long)[NSDate hour:nextHourDate]]:[NSString stringWithFormat:@"%lu",(unsigned long)[NSDate hour:nextHourDate]];
        
        NSString *hm = [NSString stringWithFormat:@"%@:00-%lu:00",nextHourStr,[NSDate hour:nextHourDate]+2];
        
        return [NSString stringWithFormat:@"%@ %@",ymd,hm];
    }
}

+ (NSDate *)getDateWithSelectTime:(NSString *)selectedTime
{
    return [NSDate dateWithString:[selectedTime substringToIndex:10] format:[NSDate ymdFormat]];
}

+ (NSString *)getOffsetDaySelectedStringWithNowSelectedTime:(NSString *)nowSelectedTime offset:(int)offset
{
    NSDate *date = [NSDate dateWithString:[nowSelectedTime substringToIndex:10] format:[NSDate ymdFormat]];
    
    NSString *offsetday = [NSDate stringWithDate:[date offsetDays:offset] format:[NSDate ymdFormat]];
    
    return [NSString stringWithFormat:@"%@ %@",offsetday,[self getImterValTimeWithSelecttime:nowSelectedTime]];
}

+ (NSString *)getymdStrWithSelectedTime:(NSString *)selectedTime
{
    return [selectedTime substringToIndex:10];
}

+ (NSDate *)getymdDateWithSelectedTime:(NSString *)selectedTime
{
    return [NSDate dateWithString:[self getymdStrWithSelectedTime:selectedTime] format:[NSDate ymdFormat]];
}

+ (NSString *)getBeginDateWithSelectedTime:(NSString *)selectedTime
{
    NSArray *array = [selectedTime componentsSeparatedByCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@" "]];
    NSArray *array1 = [array[1] componentsSeparatedByCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"-"]];
    
    return [NSString stringWithFormat:@"%@ %@:00",array[0],array1[0]];
}

+ (NSString *)getEndDateWithSelectedTime:(NSString *)selectedTime
{
    NSArray *array = [selectedTime componentsSeparatedByCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@" "]];
    NSArray *array1 = [array[1] componentsSeparatedByCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"-"]];
    
    return [NSString stringWithFormat:@"%@ %@:00",array[0],array1[1]];
}

+ (NSString *)getDefaultSelectedStringWithNowDate:(NSDate *)date DefualtTimeLong:(NSString *)timeLong
{
    NSInteger offset = [timeLong integerValue];
    
    NSString *ymd = [NSDate stringWithDate:date format:[NSDate ymdFormat]];
    
    NSDate *nextHourDate = [date offsetHours:1];
    NSString *hm = [NSString stringWithFormat:@"%ld:00-%lu:00",(unsigned long)[NSDate hour:nextHourDate],[NSDate hour:nextHourDate]+offset];
    
    return [NSString stringWithFormat:@"%@ %@",ymd,hm];
}

+ (NSInteger)getBeginIndexWithBegindateStr:(NSString *)begindate
{
    NSInteger beginHour = [NSDate hour:[NSDate dateWithString:begindate format:[NSDate ymdHmsFormat]]];
    
    NSInteger beginMinute = [NSDate minute:[NSDate dateWithString:begindate format:[NSDate ymdHmsFormat]]];
    
    return (beginHour - 8)*2 + beginMinute/30;
}

+ (NSInteger)getEndIndexWithEndDateStr:(NSString *)enddate
{
    NSInteger endHour = [NSDate hour:[NSDate dateWithString:enddate format:[NSDate ymdHmsFormat]]];
    
    NSInteger endMinute = [NSDate minute:[NSDate dateWithString:enddate format:[NSDate ymdHmsFormat]]];
    
    return (endHour - 8)*2 + endMinute/30;
}

+ (NSString *)getSelectTimeWithBeginIndex:(NSInteger)beginIndex andEndIndex:(NSInteger)endIndex andSelectTime:(NSString *)selectTime
{
    NSInteger beginHour = beginIndex/2 + 8;
    NSString *bHour = beginHour>=10?[NSString stringWithFormat:@"%ld",(long)beginHour]:[NSString stringWithFormat:@"0%ld",(long)beginHour];
    NSString *bMinute = beginIndex%2>0?@"30":@"00";
    
    NSInteger endHour = endIndex/2 + 8;
    NSString *eHour = endHour>=10?[NSString stringWithFormat:@"%ld",(long)endHour]:[NSString stringWithFormat:@"0%ld",(long)endHour];
    NSString *eMinute = endIndex%2>0?@"30":@"00";
    
    return [NSString stringWithFormat:@"%@ %@:%@-%@:%@",[selectTime substringToIndex:10],bHour,bMinute,eHour,eMinute];
}

+ (NSString *)getWeekdayWithDate:(NSDate *)date
{
    NSInteger weekDay = [NSDate weekday:date];
    
    switch (weekDay)
    {
        case 1:
            return @"周日";
            break;
            
        case 2:
            return @"周一";
            break;
            
        case 3:
            return @"周二";
            break;
            
        case 4:
            return @"周三";
            break;
            
        case 5:
            return @"周四";
            break;
            
        case 6:
            return @"周五";
            break;
            
        case 7:
            return @"周六";
            break;
            
        default:
            break;
    }
    
    return @"周一";
}

+ (NSInteger)getCanOffsetIndexWithToday:(NSDate *)today
{
    NSInteger weekDay = [NSDate weekday:today];
    
    switch (weekDay)
    {
        case 1:
            return 4;
            break;
            
        case 2:
            return 3;
            break;
            
        case 3:
            return 3;
            break;
            
        case 4:
            return 3;
            break;
            
        case 5:
            return 5;
            break;
            
        case 6:
            return 5;
            break;
            
        case 7:
            return 5;
            break;
            
        default:
            break;
    }
    
    return 2;
}

+ (BOOL)canscheduleMeetingWithNowDate:(NSDate *)nowDate
{
    NSInteger hour = [NSDate hour:nowDate];
    NSInteger minute = [NSDate minute:nowDate];
    
    if (hour>=0 && hour<=7)
    {
        return NO;
    }else if (hour==8&&minute<15)
    {
        return NO;
    }else
    {
        return YES;
    }
}

+ (BOOL)canShakeWithNowDate:(NSDate *)nowDate
{
    NSInteger hour = [NSDate hour:nowDate];
    NSInteger minute = [NSDate minute:nowDate];
    
    if (hour>=20 || hour<=7)
    {
        return NO;
    }else if (hour==8&&minute<15)
    {
        return NO;
    }else
    {
        return YES;
    }
}


+ (void)getTimestampWithSystemDate:(NSDate *)systemDate ServerDate:(NSDate *)serverDate
{
    NSString *timeStamp = nil;
    
    NSTimeInterval late1=[systemDate timeIntervalSince1970]*1;
    
    NSTimeInterval late2=[serverDate timeIntervalSince1970]*1;
    
    if (late1 > late2)
    {
        timeStamp = [NSString stringWithFormat:@"1-%f",late1-late2];
    }else
    {
        timeStamp = [NSString stringWithFormat:@"0-%f",late2-late1];
    }
    
    [[NSUserDefaults standardUserDefaults] setObject:timeStamp forKey:@"SystemDateAndServerDate"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    NSLog(@"timeStamp == %@",timeStamp);
}

+ (NSDate *)getServerDate
{
    NSDate *serverDate = nil;
    
    NSString *timeStamp = [[NSUserDefaults standardUserDefaults] objectForKey:@"SystemDateAndServerDate"];
    
    if (timeStamp)
    {
        NSArray *arr = [timeStamp componentsSeparatedByString:@"-"];
        if ([@"0" isEqualToString:arr.firstObject])
        {
            serverDate = [NSDate dateWithTimeInterval:[arr[1] doubleValue] sinceDate:[NSDate date]];
        }else
        {
            serverDate = [NSDate dateWithTimeInterval:-[arr[1] doubleValue] sinceDate:[NSDate date]];
        }

    }else
    {
        serverDate = [NSDate date];
    }
    
    return serverDate;
}

+ (BOOL)checkAreadySelectTime:(NSString *)selectTime
{
    NSDate *beginDate = [NSDate dateWithString:[NSDate getBeginDateWithSelectedTime:selectTime] format:[NSDate ymdHmsFormat]];
    
    if ([[NSDate getServerDate] isSameDay:[NSDate getymdDateWithSelectedTime:selectTime]])
    {
        if ([[NSDate getServerDate] hour] < [beginDate hour])
        {
            return YES;
            
        }else if ([[NSDate getServerDate] hour] == [beginDate hour])
        {
            if ([[NSDate getServerDate] minute] < [beginDate minute])
            {
                return YES;
            }else
            {
                return NO;
            }
        }else
        {
            return NO;
        }
    }
    else
    {
        return YES;
    }
    
}


@end
