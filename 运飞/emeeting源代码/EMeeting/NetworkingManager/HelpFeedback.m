//
//  HelpFeedback.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/7.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "HelpFeedback.h"

@implementation HelpFeedback
+ (NSString *)commandName
{
    return @"HelpFeedback";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithFeedbackInfo:(NSString *) feedbackInfo Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *feedbackInfoObj=[[FunctionObject alloc]init];
    feedbackInfoObj.k=@"FeedbackInfo";
    feedbackInfoObj.v=feedbackInfo;
    
    self.requestObj.f=@[feedbackInfoObj];
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
