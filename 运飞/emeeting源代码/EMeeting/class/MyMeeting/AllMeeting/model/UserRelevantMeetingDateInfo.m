//
//  UserRelevantMeetingDateInfo.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "UserRelevantMeetingDateInfo.h"

@implementation UserRelevantMeetingDateInfo
-(instancetype)initWithDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _meetingsDate=dic[@"MD"]?:@"";
        _theDayMettingNumber=dic[@"TDMN"]?:@"";
    }
    return self;
}

@end
