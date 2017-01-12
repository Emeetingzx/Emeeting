//
//  EMMLoggerManager.h
//  EMMLoggerManager
//
//  Created by itp on 15/8/27.
//  Copyright (c) 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EMMLogDefine.h"

@interface EMMLoggerManager : NSObject

+ (dispatch_queue_t)loggingQueue;

+ (void)log:(BOOL)asynchronous
      level:(EMMLogLevel)level
       flag:(EMMLogFlag)flag
    context:(NSInteger)context
       file:(const char *)file
   function:(const char *)function
       line:(NSUInteger)line
        tag:(id)tag
     format:(NSString *)format, ... NS_FORMAT_FUNCTION(9,10);

+ (void)log:(BOOL)asynchronous
      level:(EMMLogLevel)level
       flag:(EMMLogFlag)flag
    context:(NSInteger)context
       file:(const char *)file
   function:(const char *)function
       line:(NSUInteger)line
        tag:(id)tag
     format:(NSString *)format
       args:(va_list)argList;

+ (void)log:(BOOL)asynchronous
    message:(NSString *)message
      level:(EMMLogLevel)level
       flag:(EMMLogFlag)flag
    context:(NSInteger)context
       file:(const char *)file
   function:(const char *)function
       line:(NSUInteger)line
        tag:(id)tag;

/**
 *  修改fileLogger 路径
 *
 *  @param directory 日志文件路径
 */
+ (void)modifyFileLoggerDirectory:(NSString *)directory;

+ (NSString *)fileLoggerDirectory;

@end



