//
//  MeetingInfo.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingInfo.h"

@implementation MeetingInfo

- (NSDictionary *)networkingDictionary
{
    return @{
             @"MID":self.meetingID?:NULLObject,
             @"MN":self.meetingName?:NULLObject,
             @"BMRIDS":self.bookingsMeetingRoomIds?:NULLObject,
             @"BD":self.beginDate?:NULLObject,
             @"ED":self.endDate?:NULLObject,
             @"MT":self.meetingTime?:NULLObject,
             @"ML":self.meetingLevel?:NULLObject,
             @"OPN":self.organizePeopleNo?:NULLObject,
             @"OPCN":self.organizePeopleChineseName?:NULLObject,
             @"PMLL":self.participateMeetingLeaderLevel?:NULLObject,
             @"AT":self.applyTime?:NULLObject,
             @"MS":self.meetingState?:NULLObject,
             @"OS":self.operatingState?:NULLObject,
             @"PN":self.participantsNumber?:NULLObject,
             @"MTP":self.meetingType?:NULLObject,
             @"AP":self.accessPhone?:NULLObject,
             @"MNB":self.meetingNumber?:NULLObject,
             @"MPWD":self.meetingPassword?:NULLObject,
             };
}

-(instancetype)initWithDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _meetingID=dic[@"MID"]!=NULLObject?dic[@"MID"]:@"";
        _meetingName=dic[@"MN"]!=NULLObject?dic[@"MN"]:@"";
        _bookingsMeetingRoomIds=dic[@"BMRIDS"]!=NULLObject?dic[@"BMRIDS"]:@"";
        _beginDate=dic[@"BD"]?:@"";
        _endDate=dic[@"ED"]?:@"";
        _meetingTime=dic[@"MT"]?:@"";
        _meetingLevel= [self obtainMeetingLevel:dic[@"ML"]?:@""];
        _organizePeopleNo=dic[@"OPN"]?:@"";
        _organizePeopleChineseName= dic[@"OPCN"]?:@"";
        _participateMeetingLeaderLevel=[self obtainParticipateMeetingLeaderLeve:dic[@"PMLL"]?:@""];
        _applyTime=dic[@"AT"]?:@"";
        _meetingState=dic[@"MS"]?:@"";
        _operatingState=dic[@"OS"]?:@"";
        _participantsNumber=dic[@"PN"]?:@"";
        _meetingType= [self obtainMeetingType:dic[@"MTP"]?:@""];
        _accessPhone=dic[@"AP"]?:@"";
        _meetingNumber=dic[@"MNB"]?:@"";
        _meetingPassword=dic[@"MPWD"]?:@"";
        _showMeetingTime=[self obtainShowMeetingTime:dic];
        _isProlong=dic[@"IPRL"]?:@"";
        _isDominate=dic[@"IEC"]?:@"";
        _isSingnIn=dic[@"ISI"]?:@"";
    }
    return self;
}
-(instancetype)initWithMeetingDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _meetingID=dic[@"MID"]!=NULLObject?dic[@"MID"]:@"";
        _meetingName=dic[@"MN"]!=NULLObject?dic[@"MN"]:@"";
        _bookingsMeetingRoomIds=dic[@"BMRIDS"]!=NULLObject?dic[@"BMRIDS"]:@"";
        _beginDate=dic[@"BD"]?:@"";
        _endDate=dic[@"ED"]?:@"";
        _meetingTime=dic[@"MT"]?:@"";
        _meetingLevel= [self obtainMeetingLevel:dic[@"ML"]?:@""];
        _organizePeopleNo=dic[@"OPN"]?:@"";
        _organizePeopleChineseName= dic[@"OPCN"]?:@"";
        _participateMeetingLeaderLevel=[self obtainParticipateMeetingLeaderLeve:dic[@"PMLL"]?:@""];
        _applyTime=dic[@"AT"]?:@"";
        _meetingState=dic[@"MS"]?:@"";
        _operatingState=dic[@"OS"]?:@"";
        _participantsNumber=dic[@"PN"]?:@"";
        _meetingType= [self obtainMeetingType:dic[@"MTP"]?:@""];
        _accessPhone=dic[@"AP"]?:@"";
        _meetingNumber=dic[@"MNB"]?:@"";
        _meetingPassword=dic[@"MPWD"]?:@"";
        _showMeetingTime=[self obtainShowMeetingTime:dic];
        _isProlong=dic[@"IPRL"]?:@"";
         _isDominate=dic[@"IEC"]?:@"";
        _isSingnIn=dic[@"ISI"]?:@"";
    }
    return self;
}
-(NSString *)obtainMeetingLevel:(NSString *)meetingLevel{
    int mLevel=[meetingLevel intValue];
    switch (mLevel) {
        case 0:
            return @"C级";
            break;
        case 1:
            return @"A级";
            break;
        case 2:
            return @"B级";
            break;
        case 3:
            return @"C级";
            break;
        default:
            break;
    }
    return @"";
}
-(NSString *)obtainParticipateMeetingLeaderLeve:(NSString *)participateMeetingLeaderLeve{
    int mLevel=[participateMeetingLeaderLeve intValue];
    switch (mLevel) {
        case 0:
            return @"其他";
            break;
        case 1:
            return @"二层领导";
            break;
        case 2:
            return @"三层领导";
            break;
        case 3:
            return @"四层领导";
            break;
        case 4:
            return @"其他";
            break;
        default:
            break;
    }
    return @"";
}


-(NSString *)obtainMeetingType:(NSString *)meetingType{
    int mLevel=[meetingType intValue];
    switch (mLevel) {
        case 1:
            return @"常规会议";
            break;
        case 2:
            return @"电话会议桥";
            break;
        case 3:
            return @"培训会议";
            break;
        case 4:
            return @"网真会议";
            break;
        case 5:
            return @"云招标会议";
            break;
        case 6:
            return @"视频会议桥";
            break;
        default:
            break;
    }
    return @"";
}

-(NSString *)obtainShowMeetingTime:(NSDictionary *) dic{
    
    NSString *meetingTimeString=[NSString stringWithFormat:@"%@-%@",[dic[@"BD"] substringWithRange:NSMakeRange(0, 16)],[dic[@"ED"] substringWithRange:NSMakeRange(11, 5)]];
    return meetingTimeString;
}
@end
