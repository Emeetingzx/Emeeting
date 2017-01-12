//
//  EMMHTTPRequestOperation.h
//  EMMNetWork
//
//  Created by clement on 14-4-23.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import "EMMURLConnectionOperation.h"

@interface EMMHTTPRequestOperation : EMMURLConnectionOperation

/**
 *  httpConnection 应答
 */
@property (readonly, nonatomic, strong) NSHTTPURLResponse *response;

/**
 *  成功回调的执行队列 如果为空则在主线程队列执行
 */
@property (nonatomic, assign) dispatch_queue_t successCallbackQueue;

/**
 *  失败回调的执行队列 如果为空则在主线程队列执行
 */
@property (nonatomic, assign) dispatch_queue_t failureCallbackQueue;

/**
 *  赋值httpRequest操作成功、失败回调
 *
 *  @param success 成功后的回调
 *  @param failure 失败后的回调
 */
- (void)setCompletionBlockWithSuccess:(void (^)(EMMHTTPRequestOperation *operation, id responseObject))success
                              failure:(void (^)(EMMHTTPRequestOperation *operation, NSError *error))failure;

@end

