//
//  InvitaMeeting.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "InvitaMeeting.h"

@implementation InvitaMeeting
+ (NSString *)commandName
{
    return @"InvitaMeeting";
}

+ (NSString *)path
{
    return MeetingDominate;
}

- (void)sendJSONRequestWithMeetingId:(NSString *) meetingId Type:(NSString *) type Number:(NSString *) number  Name:(NSString *) name Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *typeObj=[[FunctionObject alloc]init];
    typeObj.k=@"Type";
    typeObj.v=type;
    
    FunctionObject *meetingIdObj=[[FunctionObject alloc]init];
    meetingIdObj.k=@"ID";
    meetingIdObj.v=meetingId;
    
    FunctionObject *numberObj=[[FunctionObject alloc]init];
    numberObj.k=@"Number";
    numberObj.v=number;
    
    FunctionObject *nameObj=[[FunctionObject alloc]init];
    nameObj.k=@"Name";
    nameObj.v=name;
    
    self.requestObj.f=@[meetingIdObj,typeObj,numberObj,nameObj];

    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
