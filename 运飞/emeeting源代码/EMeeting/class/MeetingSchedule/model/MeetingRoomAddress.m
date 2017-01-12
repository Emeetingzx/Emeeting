//
//  MeetingRoomAddress.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingRoomAddress.h"

@implementation MeetingRoomAddress

-(id)initWithDictionary:(NSDictionary *)dict
{
    if (self = [super initWithDictionary:dict])
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
