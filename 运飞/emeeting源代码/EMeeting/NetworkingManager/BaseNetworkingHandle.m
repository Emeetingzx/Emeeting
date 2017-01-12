//
//  BaseNetworkingHandle.m
//  RedPacket
//
//  Created by itp on 15/11/11.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "BaseNetworkingHandle.h"
#import "EMMNetworking.h"
#import "EMMURLConnectionOperation+SOAP.h"
#import "EMMHTTPManager+SOAP.h"
#import "NetworkingManager.h"

@implementation BaseNetworkingHandle

- (id)init
{
    self = [super init];
    if (self) {
        _requestObj = [[RequestObject alloc] initWithCommandName:[[self class] commandName]];
    }
    return self;
}

+ (NSString *)path
{
    return @"";
}

+ (NSString *)commandName
{
    return @"";
}

- (void)sendJSONRequestWithSuccess:(void(^)(ResponseObject *response))success failure:(void(^)(NSError *error))failure
{
    NetworkingManager *manager = [NetworkingManager share];
    NSString *postPath = [manager.urlManager path:[[self class] path]];
    
    NSDictionary *postDict = [self.requestObj requestDictionary];
    NSLog(@"%@",postDict);
//    NSLog(@"postDict == %@",[self.requestObj soapString]);
    
    [manager.httpManager postPath:postPath
                       parameters:postDict
             postDataEncodingType:PostDataEncodingTypeJSON
                          success:^(EMMHTTPRequestOperation *operation, id responseObject) {
                              if (!([self.requestObj.c isEqualToString:@"SysMeetingRoomInfo"]||[self.requestObj.c isEqualToString:@"SysMeetingRoomAddress"]))
                              {
                                   NSLog(@"json success %@",operation.responseString);
                              }
                             
                              NSDictionary *responseDict = [operation responseJSON];
                              if ([responseDict isKindOfClass:[NSDictionary class]])
                              {
                                  ResponseObject *result = [[ResponseObject alloc] initWithDictionary:responseDict];
                                  success(result);
                              }
                              else
                              {
                                  success(nil);
                              }
                          }
                          failure:^(EMMHTTPRequestOperation *operation, NSError *error) {
                              NSLog(@"json failure");
                              failure(error);
                          }];
}

- (void)sendSoapRequestWithSuccess:(void(^)(ResponseObject *response))success failure:(void(^)(NSError *error))failure
{
    NetworkingManager *manager = [NetworkingManager share];
    
    NSString *postPath = [manager.urlManager path:[[self class] path]];
    
    NSMutableURLRequest *urlRequest = [manager.httpManager requestWithMethod:@"POST"
                                                                        path:postPath
                                                                  jsonString:[self.requestObj soapString]
                                                                  soapMethod:@"WSService"];
    EMMHTTPRequestOperation *op = [manager.httpManager HTTPRequestOperationWithRequest:urlRequest
                                                                               success:^(EMMHTTPRequestOperation *operation, id responseObject){
                                                                                   NSLog(@"soap Success! %@",[operation soapJSON]);
                                                                                   NSDictionary *responseDict = [operation soapJSON];
                                                                                   if ([responseDict isKindOfClass:[NSDictionary class]])
                                                                                   {
                                                                                       ResponseObject *result = [[ResponseObject alloc] initWithDictionary:responseDict];
                                                                                       success(result);
                                                                                   }
                                                                                   else
                                                                                   {
                                                                                       success(nil);
                                                                                   }
                                                                               }
                                                                               failure:^(EMMHTTPRequestOperation *operation, NSError *error) {
                                                                                   NSLog(@"soap Failure!");
                                                                                   failure(error);
                                                                               }];
    [manager.httpManager enqueueHTTPRequestOperation:op];
}


@end
