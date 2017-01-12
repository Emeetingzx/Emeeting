//
//  AttendanceOperation.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AttendanceOperation.h"

@implementation AttendanceOperation
+ (NSString *)commandName
{
    return @"AttendanceOperation";
}

+ (NSString *)path
{
    return MeetingDominate;
}
- (void)sendJSONRequestWithMeetingId:(NSString *) meetingId CodeInfo:(NSString *) codeInfo Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *meetingIdObj=[[FunctionObject alloc]init];
    meetingIdObj.k=@"meetingID";
    meetingIdObj.v=meetingId;
    
    FunctionObject *codeInfoObj=[[FunctionObject alloc]init];
    codeInfoObj.k=@"CodeInfo";
    codeInfoObj.v=codeInfo;
    
    self.requestObj.f=@[meetingIdObj,codeInfoObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}
@end
