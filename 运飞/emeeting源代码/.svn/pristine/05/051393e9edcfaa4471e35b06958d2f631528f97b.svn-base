//
//  EMMLogMacros.h
//  EMMLog
//
//  Created by itp on 15/8/28.
//  Copyright (c) 2015年 itp. All rights reserved.
//

#ifndef EMMLog_EMMLogMacros_h
#define EMMLog_EMMLogMacros_h

#import "EMMLoggerManager.h"
#import "EMMLogDefine.h"

#ifndef EMMLOG_LEVEL_DEF
#define EMMLOG_LEVEL_DEF EMMLogLevelDebug
#endif

#ifndef EMMLOG_ASYNC_ENABLED
#define EMMLOG_ASYNC_ENABLED YES
#endif

#define EMMLOG_MACRO(isAsynchronous, lvl, flg, ctx, atag, fnct, frmt, ...)     \
    [EMMLoggerManager log : isAsynchronous                                     \
                    level : lvl                                                \
                     flag : flg                                                \
                  context : ctx                                                \
                     file : __FILE__                                           \
                 function : fnct                                               \
                     line : __LINE__                                           \
                      tag : atag                                               \
                   format : (frmt), ## __VA_ARGS__]


#define EMMLOG_MAYBE(async, lvl, flg, ctx, tag, fnct, frmt, ...) \
    do { if(lvl & flg) EMMLOG_MACRO(async, lvl, flg, ctx, tag, fnct, frmt, ##__VA_ARGS__); } while(0)

/**
 * Ready to use log macros with no context or tag.
 **/
#define EMMLogError(frmt, ...)   EMMLOG_MAYBE(NO, EMMLOG_LEVEL_DEF, EMMLogFlagError,   0, nil, __PRETTY_FUNCTION__, frmt, ##__VA_ARGS__)
#define EMMLogWarn(frmt, ...)    EMMLOG_MAYBE(EMMLOG_ASYNC_ENABLED, EMMLOG_LEVEL_DEF, EMMLogFlagWarning, 0, nil, __PRETTY_FUNCTION__, frmt, ##__VA_ARGS__)
#define EMMLogInfo(frmt, ...)    EMMLOG_MAYBE(EMMLOG_ASYNC_ENABLED, EMMLOG_LEVEL_DEF, EMMLogFlagInfo,    0, nil, __PRETTY_FUNCTION__, frmt, ##__VA_ARGS__)
#define EMMLogDebug(frmt, ...)   EMMLOG_MAYBE(EMMLOG_ASYNC_ENABLED, EMMLOG_LEVEL_DEF, EMMLogFlagDebug,   0, nil, __PRETTY_FUNCTION__, frmt, ##__VA_ARGS__)
#define EMMLogVerbose(frmt, ...) EMMLOG_MAYBE(EMMLOG_ASYNC_ENABLED, EMMLOG_LEVEL_DEF, EMMLogFlagVerbose, 0, nil, __PRETTY_FUNCTION__, frmt, ##__VA_ARGS__)

#endif
