//
//  EMMFileManager.h
//  EMMFileCache
//
//  Created by itp on 15/8/17.
//  Copyright (c) 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EMMFileCache.h"

typedef void(^EMMFileDownloadProgressBlock)(NSInteger receivedSize, NSInteger expectedSize);

typedef void(^EMMFileCompletionWithFinishedBlock)(NSData *fileData, NSError *error, EMMFileCacheType cacheType, BOOL finished, NSURL *imageURL);

typedef NSString *(^EMMFileCacheKeyFilterBlock)(NSURL *url);

@interface EMMFileManager : NSObject

@property (strong, nonatomic, readonly) EMMFileCache *fileCache;

@property (nonatomic, copy) EMMFileCacheKeyFilterBlock cacheKeyFilter;

/**
 *  Returns 全局 EMMFileManager 实例.
 *
 *  @return EMMFileManager 共享实例
 */
+ (EMMFileManager *)sharedManager;

/**
 *  初始化 制定缓存名字空间
 *
 *  @param ns 缓存命名空间
 */
- (id)initWithNamespace:(NSString *)ns;

/**
 * 初始化 缓存命名空间，路径
 *
 *  @param ns        缓存命名空间
 *  @param directory 缓存路径
 */
- (id)initWithNamespace:(NSString *)ns diskCacheDirectory:(NSString *)directory;

/**
 *  设置http头的指定字段值
 *
 *  @param header 指定字段
 *  @param value  指定值
 */
- (void)setDefaultHeader:(NSString *)header value:(NSString *)value;

/**
 *  通过url地址获取文件缓存
 *
 *  @param url            文件网络url地址
 *  @param parameters     下载参数
 *  @param progressBlock  进度反馈block
 *  @param completedBlock 完成block
 *
 *  @return NSOperation 网络操作对象
 */
- (NSOperation *)downloadFileWithURL:(NSURL *)url
                          parameters:(NSDictionary *)parameters
                            progress:(EMMFileDownloadProgressBlock)progressBlock
                           completed:(EMMFileCompletionWithFinishedBlock)completedBlock;

/**
 *  保存文件数据到指定的url
 *
 *  @param fileData 文件数据
 *  @param url   文件url地址
 *
 */

- (void)saveFileDataToCache:(NSData *)fileData forURL:(NSURL *)url;

/**
 *  取消所有当前的文件下载操作
 */
- (void)cancelAll;

/**
 *  是否有文件下载操作在运行
 */
- (BOOL)isRunning;

/**
 *  是否已经缓存对应url的文件 可能是缓存在内存 或 本地
 *
 *  @param url 文件url地址
 *
 *  @return
 */
- (BOOL)fileExistsForURL:(NSURL *)url;

/**
 *  是否已经缓存对应url的文件 本地缓存
 *
 *  @param url 文件url地址
 *
 *  @return
 */
- (BOOL)fileExistsFromDiskCacheForURL:(NSURL *)url;

/**
 *  异步返回是否缓存对应url的文件 可能是缓存在内存 或 本地
 *
 *  @param url              文件url地址
 *  @param completionBlock  异步回调block
 *
 *  @note completionBlock 在主线程执行
 */
- (void)fileExistsForURL:(NSURL *)url completion:(EMMFileCheckCacheCompletionBlock)completionBlock;

/**
 *  异步返回是否缓存对应url的文件 本地缓存
 *
 *  @param url              文件url地址
 *  @param completionBlock  异步回调block
 *
 *  @note completionBlock 在主线程执行
 */
- (void)fileExistsFromDiskCacheForURL:(NSURL *)url completion:(EMMFileCheckCacheCompletionBlock)completionBlock;

/**
 *  根据url返回cacheKey
 */
- (NSString *)cacheKeyForURL:(NSURL *)url;

@end


@interface EMMFileManager (Networking)

@property (strong, nonatomic, readonly) NSURL *internetBaseUrl;

@property (strong, nonatomic, readonly) NSURL *intranetBaseUrl;

/**
 *  初始化 缓存命名空间，路径，图片基础路径
 *
 *  @param internetUrl 外网baseUrl
 *  @param intranetUrl 内网baseUrl
 */
- (id)initWithInternetBaseUrl:(NSURL *)internetUrl intranetBaseUrl:(NSURL *)intranetUrl;

/**
 *  初始化 缓存命名空间，路径，图片基础路径
 *
 *  @param ns           缓存命名空间
 *  @param directory    缓存路径
 *  @param internetUrl 外网baseUrl
 *  @param intranetUrl 内网baseUrl
 */
- (id)initWithNamespace:(NSString *)ns diskCacheDirectory:(NSString *)directory internetBaseUrl:(NSURL *)internetUrl intranetBaseUrl:(NSURL *)intranetUrl;

/**
 *  通过相对地址获取文件缓存，需要设置内外网baseUrl
 *
 *  @param path           相对路径
 *  @param parameters     下载参数
 *  @param progressBlock  进度反馈block
 *  @param completedBlock 完成block
 *
 *  @return NSOperation 网络操作对象
 */
- (NSOperation *)downloadFileWithPath:(NSString *)path
                           parameters:(NSDictionary *)parameters
                             progress:(EMMFileDownloadProgressBlock)progressBlock
                            completed:(EMMFileCompletionWithFinishedBlock)completedBlock;

/**
 *  保存文件数据到相对路径
 *
 *  @param fileData 文件数据
 *  @param path   文件相对地址
 *
 */

- (void)saveFileDataToCache:(NSData *)fileData forPath:(NSString *)path;

/**
 *  是否已经缓存对应相对path的文件 可能是缓存在内存 或 本地
 *
 *  @param path 文件相对path
 *
 *  @return
 */
- (BOOL)fileExistsForPath:(NSString *)path;

/**
 *  是否已经缓存对应path的文件 本地缓存
 *
 *  @param path 文件相对path
 *
 *  @return
 */
- (BOOL)fileExistsFromDiskCacheForPath:(NSString *)path;

/**
 *  异步返回是否缓存对应path的文件 可能是缓存在内存 或 本地
 *
 *  @param path             文件相对path
 *  @param completionBlock  异步回调block
 *
 *  @note completionBlock 在主线程执行
 */
- (void)fileExistsForPath:(NSString *)path completion:(EMMFileCheckCacheCompletionBlock)completionBlock;

/**
 *  异步返回是否缓存对应path的文件 本地缓存
 *
 *  @param path              文件相对path
 *  @param completionBlock  异步回调block
 *
 *  @note completionBlock 在主线程执行
 */
- (void)fileExistsFromDiskCacheForPath:(NSString *)path completion:(EMMFileCheckCacheCompletionBlock)completionBlock;

@end

