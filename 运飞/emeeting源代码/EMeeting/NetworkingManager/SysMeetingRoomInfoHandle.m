//
//  SysMeetingRoomInfoHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "SysMeetingRoomInfoHandle.h"

@implementation SysMeetingRoomInfoHandle

+ (NSString *)commandName
{
    return @"SysMeetingRoomInfo";
}

+ (NSString *)path
{
    return BaseDataPath;
}

- (void)sendJSONRequestWithLastUpDateTime:(NSString *)lastUpdateTime Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
//    if (![lastUpdateTime isEqualToString:@""])
//    {
        FunctionObject *LastUpdateDateObj=[[FunctionObject alloc]init];
        LastUpdateDateObj.k=@"LastUpdateDate";
        LastUpdateDateObj.v=lastUpdateTime;
        self.requestObj.f=@[LastUpdateDateObj];
//    }
    
    PageRequestObject *page = [[PageRequestObject alloc] init];
    page.psize = 100000000;
    self.requestObj.p = page;
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
