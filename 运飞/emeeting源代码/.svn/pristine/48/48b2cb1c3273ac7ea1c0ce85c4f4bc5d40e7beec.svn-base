//
//  GetRecentBuildingAddressInfoHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetRecentBuildingAddressInfoHandle.h"

@implementation GetRecentBuildingAddressInfoHandle

+ (NSString *)commandName
{
    return @"GetRecentBuildingAddressInfo";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *latitudeAndLongitudeObj=[[FunctionObject alloc]init];
    latitudeAndLongitudeObj.k=@"LatitudeAndLongitude";
    latitudeAndLongitudeObj.v=latitudeAndLongitude;
    self.requestObj.f=@[latitudeAndLongitudeObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
