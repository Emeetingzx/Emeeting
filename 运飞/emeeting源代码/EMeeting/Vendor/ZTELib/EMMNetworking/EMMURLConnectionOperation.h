//
//  EMMURLConnectionOperation.h
//  EMMNetWork
//
//  Created by clement on 14-4-23.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

//typedef enum {
//    EMMSSLPinningModeNone,
//    EMMSSLPinningModePublicKey,
//    EMMSSLPinningModeCertificate,
//} EMMURLConnectionOperationSSLPinningMode;

@interface EMMURLConnectionOperation : NSOperation<NSURLConnectionDelegate, NSURLConnectionDataDelegate, NSCoding, NSCopying>

/**
 *  默认包含 NSRunLoopCommonModes
 */
@property (nonatomic, strong) NSSet *runLoopModes;

/**
 *  urlconection 请求
 */
@property (readonly, nonatomic, strong) NSURLRequest *request;

/**
 *  urlconnection 应答
 */
@property (readonly, nonatomic, strong) NSURLResponse *response;

/**
 *  urlconnection 错误
 */
@property (readonly, nonatomic, strong) NSError *error;

/**
 *  网络返回数据（NSData）
 */
@property (readonly, nonatomic, strong) NSData *responseData;

/**
 *  网络返回数据（NSString）
 */
@property (readonly, nonatomic, copy) NSString *responseString;

/**
 *  网络返回数据（JSON）
 */
@property (readonly, nonatomic, copy) id responseJSON;

/**
 *  网络返回数据（NSString）编码格式 默认为 NSUTF8StringEncoding
 */
@property (readonly, nonatomic, assign) NSStringEncoding responseStringEncoding;

@property (nonatomic, assign) BOOL allowsInvalidSSLCertificate;

/**
 *  默认为 YES
 */
@property (nonatomic, assign) BOOL shouldUseCredentialStorage;

//@property (nonatomic, assign) EMMURLConnectionOperationSSLPinningMode SSLPinningMode;

@property (nonatomic, strong) NSURLCredential *credential;
/**
 *  发送请求时读取数据
 */
@property (nonatomic, strong) NSInputStream *inputStream;

/**
 *  如果没有设置输出流，默认情况数据是存储在responseData
 */
@property (nonatomic, strong) NSOutputStream *outputStream;

/**
 *  解密content(如果需要解密的话)
 *
 *  @param content 需解密内容
 *
 *  @return 解密后内容
 */
- (NSString *)decryptContent:(NSString *)content;

/**
 *  初始化方法
 *
 *  @param urlRequest 网络请求
 *
 *  @return 网络operation
 */
- (instancetype)initWithRequest:(NSURLRequest *)urlRequest;

/**
 *  暂停执行网络请求操作
 */
- (void)pause;

/**
 Whether the request operation is currently paused.
 
 @return `YES` if the operation is currently paused, otherwise `NO`.
 */
/**
 *  是否网络请求操作被暂停
 *
 *  @return 如果操作被暂停 返回 YES 否则 NO
 */
- (BOOL)isPaused;

/**
 *  恢复网络请求操作
 */
- (void)resume;

/**
 *  赋值一个上传进度回调
 *
 *  @param block 上传进度回调
 */
- (void)setUploadProgressBlock:(void (^)(NSUInteger bytesWritten, long long totalBytesWritten, long long totalBytesExpectedToWrite))block;

/**
 *  赋值一个下载进度回调
 *
 *  @param block 下载进度回调
 */
- (void)setDownloadProgressBlock:(void (^)(NSUInteger bytesRead, long long totalBytesRead, long long totalBytesExpectedToRead))block;

@end

//通知

/**
 *  当开始网络请求操作时 通知
 */
extern NSString * const EMMNetworkingOperationDidStartNotification;

/**
 *  当网络请求操作完成时 通知
 */
extern NSString * const EMMNetworkingOperationDidFinishNotification;

