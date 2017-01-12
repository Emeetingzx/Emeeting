//
//  MeetingInfoManager.h
//  EMeeting
//
//  Created by efutureinfo on 16/3/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
#import "NetworkingManager.h"

@interface MeetingInfoManager : NSObject

@property (nonatomic, strong) FMDatabase *dataBase;

/*
 * 创建数据库管理单例
 */
+ (instancetype)shareInstance;

- (void)addSysMeetingRoomInfoWithDataArr:(NSArray *)dataArr susscess:(void (^)(void))susscess faiure:(void (^)(void))faiure;

- (NSArray *)selectSysMeetingRoomInfoWithRoomId:(NSString *)roomId;

@end
