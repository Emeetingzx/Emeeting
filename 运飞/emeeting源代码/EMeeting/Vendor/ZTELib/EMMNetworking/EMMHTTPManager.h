//
//  EMMHTTPManager.h
//  EMMNetWork
//
//  Created by clement on 14-4-23.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

@class EMMHTTPRequestOperation;
@protocol EMMMultipartFormData;

typedef enum {
    PostDataEncodingTypeURL = 0, // default
    PostDataEncodingTypeJSON,
    PostDataEncodingTypePlist
} PostDataEncodingType;


@interface EMMHTTPManager : NSObject<NSCoding, NSCopying>

/**
 *  internetBaseUrl
 */
@property (readonly, nonatomic, strong) NSURL *internetBaseURL;

/**
 *  intranetBaseUrl
 */
@property (readonly, nonatomic, strong) NSURL *intranetBaseURL;

/**
 *  网络返回数据（NSString）编码格式 默认为 NSUTF8StringEncoding
 */
@property (nonatomic, assign) NSStringEncoding stringEncoding;

@property (nonatomic, assign, readonly) NSInteger runningOperations;

/**
 *  类方法 默认无baseUrl地址
 *
 *  @return 返回无baseUrl地址EMMHTTPManager对象
 */
+ (instancetype)defaultManager;

/**
 *  类方法设置internetBaseUrl,intranetBaseUrl
 *
 *  @param internetBaseUrl  internetBaseUrl
 *  @Param intranetBaseUrl  intranetBaseUrl
 *
 *  @return 返回EMMHTTPManager对象
 */
+ (instancetype)managerWithInternetBaseURL:(NSURL *)internetBaseUrl intranetBaseUrl:(NSURL *)intranetBaseUrl;

/**
 *  初始化方法设置internetBaseUrl,intranetBaseUrl
 *
 *  @param internetBaseUrl  internetBaseUrl
 *  @Param intranetBaseUrl  intranetBaseUrl
 *
 *  @return 返回EMMHTTPManager对象
 */
- (id)initWithInternetBaseURL:(NSURL *)internetBaseUrl intranetBaseUrl:(NSURL *)intranetBaseUrl;

/**
 *  当前网络baseUrl
 *
 *  @return 内网或外网baseUrl
 */
- (NSURL *)currentNetworkingBaseUrl;

/**
 *  返回http头 指定的集
 *
 *  @param header 指定的集字段
 *
 *  @return http头指定字段的值
 */
- (NSString *)defaultValueForHeader:(NSString *)header;

/**
 *  设置http头的指定字段值
 *
 *  @param header 指定字段
 *  @param value  指定值
 */
- (void)setDefaultHeader:(NSString *)header value:(NSString *)value;

/**
 *  对content进行加密（如果需要加密的话）
 *
 *  @param content 需要加密的内容
 *  @param request 网络请求
 *
 *  @return 返回加密的content
 */
- (NSString *)encryptContent:(NSString *)content request:(NSMutableURLRequest *)request;

/**
 *  创建 NSMutableURLRequest 指定 http method 、path、param
 *
 *  @param method     http method `GET`, `POST`,`PUT`, or `DELETE`
 *  @param path       http 路径
 *  @param parameters 参数字典
 *
 *  @return 一个 NSMutableURLRequest
 */
- (NSMutableURLRequest *)requestWithMethod:(NSString *)method
                                      path:(NSString *)path
                                parameters:(NSDictionary *)parameters;

/**
 *  创建 NSMutableURLRequest 指定 http method 、path、param、postDataEncodingType
 *
 *  @param method     http method `GET`, `POST`,`PUT`, or `DELETE`
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param postDataEncodingType  Content-Type 头字段类型 url json plist 默认为url形式
 *
 *  @return 一个 NSMutableURLRequest
 */
- (NSMutableURLRequest *)requestWithMethod:(NSString *)method
                                      path:(NSString *)path
                                parameters:(NSDictionary *)parameters
                      postDataEncodingType:(PostDataEncodingType)postDataEncodingType;

/**
 *  创建 NSMutableURLRequest 文件上传请求
 *
 *  @param method     http method `GET`, `POST`,`PUT`, or `DELETE`
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param block      文件参数处理block
 *
 *  @return 一个 NSMutableURLRequest 对象
 */
- (NSMutableURLRequest *)multipartFormRequestWithMethod:(NSString *)method
                                                   path:(NSString *)path
                                             parameters:(NSDictionary *)parameters
                              constructingBodyWithBlock:(void (^)(id <EMMMultipartFormData> formData))block;

/**
 *  创建 一个 EMMHTTPRequestOperation
 *
 *  @param urlRequest 一个 NSURLRequest
 *  @param success    成功回调
 *  @param failure    失败回调
 *
 *  @return 一个 EMMHTTPRequestOperation
 */
- (EMMHTTPRequestOperation *)HTTPRequestOperationWithRequest:(NSURLRequest *)urlRequest
                                                     success:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
                                                     failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

/**
 *  将一个 EMMHTTPRequestOperation 对象插入操作队列
 *
 *  @param operation 一个EMMHTTPRequestOperation
 */
- (void)enqueueHTTPRequestOperation:(EMMHTTPRequestOperation *)operation;

/**
 *  取消操作队列里所有符合 path method 的操作对象
 *
 *  @param method     http method `GET`, `POST`,`PUT`, or `DELETE`
 *  @param path       http 路径
 */
- (void)cancelAllHTTPOperationsWithMethod:(NSString *)method path:(NSString *)path;

/**
 *  取消操作队列里所有的操作对象
 */
- (void)cancelAllHttpOperations;

/**
 *  创建 一个 `GET` 请求 `EMMHTTPRequestOperation`, 并插入到操作队列.
 *
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param success    成功回调
 *  @param failure    失败回调
 */
- (void)getPath:(NSString *)path
     parameters:(NSDictionary *)parameters
        success:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
        failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

/**
 *  创建 一个 `POST` 请求 `EMMHTTPRequestOperation`, 并插入到操作队列.
 *
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param success    成功回调
 *  @param failure    失败回调
 */
- (void)postPath:(NSString *)path
      parameters:(NSDictionary *)parameters
         success:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
         failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

/**
 *  创建 一个 `POST` 请求 `EMMHTTPRequestOperation`, 并插入到操作队列.
 *
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param postDataEncodingType Content-Type 头字段类型 url json plist 默认为url形式
 *  @param success    成功回调
 *  @param failure    失败回调
 */
- (void)postPath:(NSString *)path
      parameters:(NSDictionary *)parameters
postDataEncodingType:(PostDataEncodingType)postDataEncodingType
         success:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
         failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

/**
 *  创建 一个 `PUT` 请求 `EMMHTTPRequestOperation`, 并插入到操作队列.
 *
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param success    成功回调
 *  @param failure    失败回调
 */
- (void)putPath:(NSString *)path
     parameters:(NSDictionary *)parameters
        success:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
        failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

/**
 *  创建 一个 `DELETE` 请求 `EMMHTTPRequestOperation`, 并插入到操作队列.
 *
 *  @param path       http 路径
 *  @param parameters 参数字典
 *  @param success    成功回调
 *  @param failure    失败回调
 */
- (void)deletePath:(NSString *)path
        parameters:(NSDictionary *)parameters
           success:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
           failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

@end

@protocol EMMMultipartFormData


- (BOOL)appendPartWithFileURL:(NSURL *)fileURL
                         name:(NSString *)name
                        error:(NSError * __autoreleasing *)error;


- (BOOL)appendPartWithFileURL:(NSURL *)fileURL
                         name:(NSString *)name
                     fileName:(NSString *)fileName
                     mimeType:(NSString *)mimeType
                        error:(NSError * __autoreleasing *)error;

- (void)appendPartWithInputStream:(NSInputStream *)inputStream
                             name:(NSString *)name
                         fileName:(NSString *)fileName
                           length:(unsigned long long)length
                         mimeType:(NSString *)mimeType;

- (void)appendPartWithFileData:(NSData *)data
                          name:(NSString *)name
                      fileName:(NSString *)fileName
                      mimeType:(NSString *)mimeType;

- (void)appendPartWithFormData:(NSData *)data
                          name:(NSString *)name;

- (void)appendPartWithHeaders:(NSDictionary *)headers
                         body:(NSData *)body;

- (void)throttleBandwidthWithPacketSize:(NSUInteger)numberOfBytes
                                  delay:(NSTimeInterval)delay;

@end

