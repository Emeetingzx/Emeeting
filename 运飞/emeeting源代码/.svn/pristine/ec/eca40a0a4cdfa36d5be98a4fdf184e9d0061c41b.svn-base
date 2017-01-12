//
//  MeetingProlong.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/14.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingProlong.h"
#import "NSDate+LJFDate.h"
@implementation MeetingProlong
-(instancetype)initWithDictionary:(NSDictionary *)dic EndTime:(NSString *) endTime{
    if (self=[super init]) {
        _meetingRoomID=dic[@"MRID"]?:@"";
        _isProlong=dic[@"IPL"]?:@"";
        _prolongTime=[self backLongTime:dic[@"PLT"]==NULLObject?nil:dic[@"PLT"] EndTime:endTime];
        
    }
    return self;
    
}
-(NSString *)backLongTime:(NSString *) prolongTime  EndTime:(NSString *) endTime{
    if (prolongTime) {
        int minute=[prolongTime intValue];
        NSDate *endDate=[NSDate dateWithString: endTime format:[NSDate ymdHmsFormat]];
        NSDate *date=[endDate dateByAddingTimeInterval:minute*60];
       NSString *timeString=[NSDate stringWithDate:date format:[NSDate ymdHmsFormat]];
        timeString=[timeString substringWithRange:NSMakeRange(11,5)];
        return timeString;
    }
    
    return @"";
}
@end
