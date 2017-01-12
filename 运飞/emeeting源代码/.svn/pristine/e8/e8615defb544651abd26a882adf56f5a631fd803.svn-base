//
//  MeetingJoinInfo.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingJoinInfo.h"
#import "Tools.h"
@implementation MeetingJoinInfo
-(instancetype)initWithDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _meetingRoomID=dic[@"ID"]?:@"";
        _state=dic[@"ST"]?:@"";
        _terminalName=dic[@"TN"]?:@"";
        _terminalNumber=dic[@"NO"]?:@"";
        _endTime=dic[@"ET"]==NULLObject?nil:dic[@"ET"];
        _terminalType=dic[@"TT"]?:@"";
        _cellHeight=[Tools heightOfString:_terminalName font:[UIFont systemFontOfSize:17.0] width:APPW-80]-21+74;
        _showTerminalName=[self backShowTerminalName:dic];
        
    }
    return self;

}

-(NSString *)backShowTerminalName:(NSDictionary *)dic{
    if ([@"2" isEqualToString:dic[@"TT"]]) {
        if([_terminalName isEqualToString:[_terminalNumber substringFromIndex:1]] || [_terminalName isEqualToString:[_terminalNumber substringFromIndex:2]]){
            return dic[@"TN"];
        }else{
            if ([@"90" isEqualToString: [_terminalNumber substringToIndex:2]]) {
                return [NSString stringWithFormat:@"%@ %@",_terminalName,[_terminalNumber substringFromIndex:2]];
            }else if([@"9" isEqualToString: [_terminalNumber substringToIndex:1]]){
               return [NSString stringWithFormat:@"%@ %@",_terminalName,[_terminalNumber substringFromIndex:1]];
            }
            
        }
    }else if([@"1" isEqualToString:dic[@"TT"]]){
      return dic[@"TN"];
    }
    return dic[@"TN"];
}
@end
