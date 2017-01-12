//
//  GetMyAddValueServiceInfos.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetMyAddValueServiceInfos.h"

@implementation GetMyAddValueServiceInfos
+ (NSString *)commandName
{
    return @"GetMyAddValueServiceInfos";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithPage:(PageRequestObject *) page Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    self.requestObj.p=page;
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
