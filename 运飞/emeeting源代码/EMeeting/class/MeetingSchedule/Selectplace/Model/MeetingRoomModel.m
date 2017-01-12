//
//  MeetingRoomModel.m
//  EMeeting
//
//  Created by efutureinfo on 16/3/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingRoomModel.h"

@implementation MeetingRoomModel

-(id)initWithDictionary:(NSDictionary *)dict
{
    if (self = [super init])
    {
        _iD = dict[@"ID"]?:@"";
        _pID = dict[@"PID"]?:@"";
        _addessChinese = dict[@"ASC"]?:@"";
        _orderID = dict[@"OID"]?:@"";
        _levelId = dict[@"LID"]?:@"";
        _isValidId = dict[@"IVID"]?:@"";
        _longAddessChinese = dict[@"LASC"]?:@"";
    }
    return self;
}


@end
