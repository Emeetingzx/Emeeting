//
//  EMMLogDefine.h
//  EMMLog
//
//  Created by itp on 15/9/8.
//  Copyright (c) 2015年 itp. All rights reserved.
//

#ifndef EMMLog_EMMLogDefine_h
#define EMMLog_EMMLogDefine_h

typedef enum {
    EMMLogFlagError      = (1 << 0),
    EMMLogFlagWarning    = (1 << 1),
    EMMLogFlagInfo       = (1 << 2),
    EMMLogFlagDebug      = (1 << 3),
    EMMLogFlagVerbose    = (1 << 4)
}EMMLogFlag;

typedef enum {
    EMMLogLevelOff       = 0,
    EMMLogLevelError     = (EMMLogFlagError),                       // 0...00001
    EMMLogLevelWarning   = (EMMLogLevelError   | EMMLogFlagWarning), // 0...00011
    EMMLogLevelInfo      = (EMMLogLevelWarning | EMMLogFlagInfo),    // 0...00111
    EMMLogLevelDebug     = (EMMLogLevelInfo    | EMMLogFlagDebug),   // 0...01111
    EMMLogLevelVerbose   = (EMMLogLevelDebug   | EMMLogFlagVerbose), // 0...11111
    EMMLogLevelAll       = 0xff
}EMMLogLevel;

typedef enum {
    EMMLogTypeFileLogger  = (1 << 0), //日志语句写入至文件
    EMMLogTypeTTYLogger  = (1 << 1),  //日志语句输出到Xcode控制台
    EMMLogTypeASLLogger = (1 << 2)    //日志语句到苹果的日志系统，以便它们显示在Console.app上
}EMMLogType;

#endif
