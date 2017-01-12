//
//  EMMFileCache.h
//  EMMFileCache
//
//  Created by itp on 15/8/17.
//  Copyright (c) 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, EMMFileCacheType) {
    /**
     * The image wasn't available the EMMFile caches, but was downloaded from the web.
     */
    EMMFileCacheTypeNone,
    /**
     * The image was obtained from the disk cache.
     */
    EMMFileCacheTypeDisk,
    /**
     * The image was obtained from the memory cache.
     */
    EMMFileCacheTypeMemory
};

typedef void(^EMMFileNoParamsBlock)();

typedef void(^EMMFileCheckCacheCompletionBlock)(BOOL isInCache);

typedef void(^EMMFileCalculateSizeBlock)(NSUInteger fileCount, NSUInteger totalSize);

@interface EMMFileCache : NSObject

/**
 * use memory cache [defaults to YES]
 */
@property (assign, nonatomic) BOOL shouldCacheFilesInMemory;

/**
 * The maximum "total cost" of the in-memory image cache. The cost function is the number of pixels held in memory.
 */
@property (assign, nonatomic) NSUInteger maxMemoryCost;

/**
 * The maximum number of objects the cache should hold.
 */
@property (assign, nonatomic) NSUInteger maxMemoryCountLimit;

/**
 * 缓存文件最长时间 单位：秒
 */
@property (assign, nonatomic) NSInteger maxCacheAge;

/**
 * 缓存文件最大容量, 单位： bytes.
 */
@property (assign, nonatomic) NSUInteger maxCacheSize;

/**
 * 返回全局 FileCache对象
 *
 * @return FileCache 全局对象
 */
+ (EMMFileCache *)sharedFileCache;

/**
 * 初始化 制定缓存名字空间
 *
 * @param ns 缓存命名空间
 */
- (id)initWithNamespace:(NSString *)ns;

/**
 * 初始化 缓存命名空间，路径
 *
 * @param ns        缓存命名空间
 * @param directory 缓存路径
 */
- (id)initWithNamespace:(NSString *)ns diskCacheDirectory:(NSString *)directory;


/**
 *  创建缓存命名空间对应缓存路径
 *
 *  @param fullNamespace 缓存命名空间
 *
 *  @return 缓存路径
 */
-(NSString *)makeDiskCachePath:(NSString*)fullNamespace;

/**
 * 添加只读缓存路径
 *
 * @param path 只读缓存路径
 */
- (void)addReadOnlyCachePath:(NSString *)path;

- (NSString *)cachePathForKey:(NSString *)key inPath:(NSString *)path;

- (NSString *)defaultCachePathForKey:(NSString *)key;

- (void)storeFileData:(NSData *)fileData forKey:(NSString *)key;

- (void)storeFileData:(NSData *)fileData forKey:(NSString *)key toDisk:(BOOL)toDisk;

- (BOOL)fileExistsFromDiskCacheWithKey:(NSString *)key;

- (void)fileExistsFromDiskCacheWithKey:(NSString *)key completion:(EMMFileCheckCacheCompletionBlock)completionBlock;

- (NSData *)fileDataFromMemoryCacheForKey:(NSString *)key;

- (NSData *)fileDataFromDiskCacheForKey:(NSString *)key;

- (NSData *)fileDataBySearchingAllPathsForKey:(NSString *)key;

- (NSData *)fileDataForKey:(NSString *)key;

- (void)removeFileDataForKey:(NSString *)key;

- (void)removeFileDataForKey:(NSString *)key withCompletion:(EMMFileNoParamsBlock)completion;

- (void)removeFileDataForKey:(NSString *)key fromDisk:(BOOL)fromDisk;

- (void)removeFileDataForKey:(NSString *)key fromDisk:(BOOL)fromDisk withCompletion:(EMMFileNoParamsBlock)completion;

/**
 * 清除所有本地文件缓存，异步回调.
 * @param completion   清除完成回调block 可为nil
 */
- (void)clearDiskOnCompletion:(EMMFileNoParamsBlock)completion;

/**
 * 清除所有缓存文件
 * @see clearDiskOnCompletion:
 */
- (void)clearDisk;

/**
 * 清除过期缓存文件，异步回调.
 * @param completionBlock 清除完成回调block 可为nil
 */
- (void)cleanDiskWithCompletionBlock:(EMMFileNoParamsBlock)completionBlock;

/**
 * 清除过期缓存文件
 * @see cleanDiskWithCompletionBlock:
 */
- (void)cleanDisk;

/**
 * 获取缓存文件总大小
 */
- (NSUInteger)getSize;

/**
 * 获取缓存文件个数
 */
- (NSUInteger)getDiskCount;

/**
 * 异步计算缓存文件总大小
 */
- (void)calculateSizeWithCompletionBlock:(EMMFileCalculateSizeBlock)completionBlock;

@end
