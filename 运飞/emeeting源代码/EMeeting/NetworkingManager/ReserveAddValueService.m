//
//  ReserveAddValueService.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ReserveAddValueService.h"

@implementation ReserveAddValueService
+ (NSString *)commandName
{
    return @"ReserveAddValueService";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithServiceReserveData:(ServiceReserveData *)serviceReserveData Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *serviceDateObj=[[FunctionObject alloc]init];
    serviceDateObj.k=@"serviceDate";
    serviceDateObj.v=serviceReserveData.serviceDate;
    
    FunctionObject *serviceRegionIDObj=[[FunctionObject alloc]init];
    serviceRegionIDObj.k=@"serviceRegionID";
    serviceRegionIDObj.v=serviceReserveData.serviceRegionID;
    
    FunctionObject *serviceAddressObj=[[FunctionObject alloc]init];
    serviceAddressObj.k=@"serviceAddress";
    serviceAddressObj.v=serviceReserveData.serviceAddress;
    
    FunctionObject *phoneObj=[[FunctionObject alloc]init];
    phoneObj.k=@"phone";
    phoneObj.v=serviceReserveData.phone;
    
    FunctionObject *foodIdsObj=[[FunctionObject alloc]init];
    foodIdsObj.k=@"foodIds";
    foodIdsObj.v=serviceReserveData.foodIds;
    self.requestObj.f=@[serviceDateObj,serviceRegionIDObj,serviceAddressObj,phoneObj,foodIdsObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
        success(response);
     }failure:failure];
}

@end
