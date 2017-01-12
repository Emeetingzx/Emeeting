//
//  MeetingRoomInfo.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingRoomInfo.h"
#import "Tools.h"

@implementation MeetingRoomInfo

-(id)initWithDictionary:(NSDictionary *)dict
{
    self = [super initWithDictionary:dict];
    if (self)
    {
        _meetingRoomId = dict[@"MRID"]?:@"";
        _meetingRoomName = dict[@"MRN"]?:@"";
        _meetingRoomAddressIds = dict[@"MRAIDS"]?:@"";
        _meetingRoomScale = dict[@"MRS"]?:@"";
        _projectorState = dict[@"PJS"]?:@"";
        _televisionState = dict[@"TVS"]?:@"";
        _phoneState = dict[@"PS"]?:@"";
        _meetingRoomOccupancyRation = dict[@"MROR"]?:@"";
        [self setRowHeigthWithName:_meetingRoomName];
        
        if (dict[@"HBMIS"])
        {
            NSMutableArray *arrM = [[NSMutableArray alloc] init];
            for (NSDictionary *dict1 in dict[@"HBMIS"])
            {
                MeetingInfo *model = [[MeetingInfo alloc] initWithDictionary:dict1];
                if(model){
                     [arrM addObject:model];
                }
            }
            _haveBookingMeetingInfos = [NSMutableArray arrayWithArray:arrM];
        }
    }
    return self;
}

-(NSString *)returnTelevisionString:(NSString *)televisionState{
    if ([@"0" isEqualToString:televisionState]) {
        return @"没有";
    }else if([@"1" isEqualToString:televisionState]){
        return @"有";
    }
    return @"";
}

-(NSString *)returnPhoneString:(NSString *)phoneState{
    if ([@"0" isEqualToString:phoneState]) {
        return @"没有";
    }else if([@"1" isEqualToString:phoneState]){
        return @"POLYCOM";
    }else if([@"2" isEqualToString:phoneState]){
        return @"USB-Phone";
    }
    return @"";
}

-(NSString *)returnProjectorString:(NSString *)projectorState{
    if ([@"0" isEqualToString:projectorState]) {
        return @"没有";
    }else if([@"1" isEqualToString:projectorState]){
        return @"不支持双流";
    }else if([@"2" isEqualToString:projectorState]){
        return @"支持双流接收";
    }else if([@"3" isEqualToString:projectorState]){
        return @"支持双流接收发送";
    }
    return @"";
}

- (void)setRowHeigthWithName:(NSString *)roomName
{
    CGFloat width = 0;
    
    if (IPHONE6)
    {
        width = APPW - 70 - 70 - 8;
    }else if(IPHONE6P)
    {
        width = APPW - 70 - 80 - 8;
    }else
    {
        width = APPW - 70 - 60 - 8;
    }
    
    CGFloat heigth = [Tools heightOfString:roomName font:[UIFont systemFontOfSize:16] width:width];
 
    if (heigth > 30)
    {
        _rowHeigth = 95 + heigth -20;
    }else
    {
        _rowHeigth = 95;
    }
}

@end
