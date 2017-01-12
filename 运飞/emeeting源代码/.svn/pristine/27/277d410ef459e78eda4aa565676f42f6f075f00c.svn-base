//
//  NotifyInfo.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/27.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "NotifyInfo.h"

@implementation NotifyInfo
-(instancetype)initWithDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _notifyType=dic[@"NT"]?:@"";
        _meetingID=dic[@"MID"]?:@"";
        //_notifyContent=dic[@"NC"]?:@"";
        _meetingNotifyType=dic[@"MNT"]?:@"";
        _meetingType=dic[@"MT"]?:@"";
        _valueErverNotifyType=dic[@"VNT"];
    }
    return self;
}
@end
