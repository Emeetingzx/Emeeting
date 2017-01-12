//
//  GetAdminAddValueServiceInfos.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetAdminAddValueServiceInfos.h"

@implementation GetAdminAddValueServiceInfos
+ (NSString *)commandName
{
    return @"GetAdminAddValueServiceInfos";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithRelatedItemType:(NSString *)relatedItemType  PageRequestObject:(PageRequestObject *) pageRequestObject Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *relatedItemTypeObj=[[FunctionObject alloc]init];
    relatedItemTypeObj.k=@"relatedItemType";
    relatedItemTypeObj.v=relatedItemType;
    
    self.requestObj.f=@[relatedItemTypeObj];
    self.requestObj.p=pageRequestObject;
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
