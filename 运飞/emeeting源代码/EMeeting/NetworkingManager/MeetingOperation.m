//
//  MeetingOperation.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/18.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingOperation.h"

@implementation MeetingOperation
+ (NSString *)commandName
{
    return @"MeetingOperation";
}

+ (NSString *)path
{
    return MeetingDominate;
}
- (void)sendJSONRequestWithMeetingId:(NSString *) meetingId TermId:(NSString *) termId Number:(NSString *) number  OperationType:(NSString *) operationType  Type:(NSString *) type Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *meetingIdObj=[[FunctionObject alloc]init];
    meetingIdObj.k=@"ID";
    meetingIdObj.v=meetingId;
    
    FunctionObject *numberObj=[[FunctionObject alloc]init];
    numberObj.k=@"Number";
    numberObj.v=number;
    
    FunctionObject *termIdObj=[[FunctionObject alloc]init];
    termIdObj.k=@"TermId";
    termIdObj.v=termId;
    
    FunctionObject *operationTypeObj=[[FunctionObject alloc]init];
    operationTypeObj.k=@"OperationType";
    operationTypeObj.v=operationType;
    
    FunctionObject *typeObj=[[FunctionObject alloc]init];
    typeObj.k=@"Type";
    typeObj.v=type;
    
    self.requestObj.f=@[meetingIdObj,termIdObj,numberObj,operationTypeObj,typeObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
