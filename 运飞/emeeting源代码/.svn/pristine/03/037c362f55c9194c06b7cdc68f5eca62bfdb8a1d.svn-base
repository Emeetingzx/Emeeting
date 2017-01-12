//
//  AddValueServiceOperate.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AddValueServiceOperate.h"

@implementation AddValueServiceOperate
+ (NSString *)commandName
{
    return @"AddValueServiceOperate";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithServiceOrderId:(NSString *)serviceOrderId OperateUserType:(NSString *)operateUserType OperateType:(NSString *)operateType Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *serviceOrderIdObj=[[FunctionObject alloc]init];
    serviceOrderIdObj.k=@"serviceOrderId";
    serviceOrderIdObj.v=serviceOrderId;
    
    FunctionObject *operateUserTypeObj=[[FunctionObject alloc]init];
    operateUserTypeObj.k=@"operateUserType";
    operateUserTypeObj.v=operateUserType;
    
    FunctionObject *operateTypeObj=[[FunctionObject alloc]init];
    operateTypeObj.k=@"operateType";
    operateTypeObj.v=operateType;
    
    self.requestObj.f=@[serviceOrderIdObj,operateUserTypeObj,operateTypeObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
