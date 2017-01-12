//
//  MeetingAttendanceInfo.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MeetingAttendanceInfo : NSObject
@property (nonatomic, copy) NSString *meetingAttendanceName;
@property (nonatomic, copy) NSString *meetingAttendanceNumber;
@property (nonatomic, copy) NSString *state;
@property (nonatomic, strong) NSString *attendanceTime;
-(instancetype)initWithDictionary:(NSDictionary *)dic;
@end
