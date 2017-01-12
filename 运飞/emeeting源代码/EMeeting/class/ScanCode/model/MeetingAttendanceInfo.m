//
//  MeetingAttendanceInfo.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingAttendanceInfo.h"

@implementation MeetingAttendanceInfo
-(instancetype)initWithDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _meetingAttendanceName=dic[@"MANA"]?:@"";
        _meetingAttendanceNumber=dic[@"MANU"]?:@"";
        _state=dic[@"ST"]?:@"";
        _attendanceTime=dic[@"AT"]?:@"";
    }
    return self;
    
}
@end
