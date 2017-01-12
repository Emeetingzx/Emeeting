//
//  FMDBManager.h
//  RedPacket
//
//  Created by efutureinfo on 15/11/20.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
#import "NetworkingManager.h"

@interface FMDBManager : NSObject

@property (nonatomic, strong) FMDatabase *dataBase;

/*
 * 创建数据库管理单例
 */
+ (instancetype)shareInstance;

/*
 * 创建数据库子表
 * @prame tableName 表名
 * @prame dict 存放的字段和字段类型
 */
-(void)creatTableOfDataBaseWithTableName:(NSString *)tableName dictionary:(NSDictionary *)dict;

/*
 * 删除数据库某个子表的数据
 * @prame name 要删除的表名
 */
-(void)deleteTableDataOfDBWithName:(NSString *)name;

/*
 * 删除数据库某个子表的某个字段的数据
 * @prame name 要删除的表名
 * @prame key  字段 eg:@"where id ＝ 8888888"
 */
-(void)deleteTableDataOfDBWithName:(NSString *)name andkeyString:(NSString *)key;

/*
 * 查找数据库某个子表的数据
 * @prame name 要查找的表名
 */
-(FMResultSet *)selectFromTableName:(NSString *)name;

/*
 * 查找数据库某个子表的某个字段的数据
 * @prame name 要查找的表名
 * @prame key  字段 eg:@"where id ＝ 8888888"
 */
-(FMResultSet *)selectFromTableName:(NSString *)name andkeystring:(NSString *)key;

//- (void)addSysMeetingRoomAddressInfoWithDataArr:(NSArray *)dataArr;

- (void)addSysMeetingRoomAddressInfoWithDataArr:(NSArray *)dataArr susscess:(void (^)(void))susscess faiure:(void (^)(void))faiure;

- (NSArray *)selectSysMeetingRoomAddressInfoLevelId:(NSString *)LevelId andPID:(NSString *)pid;

- (NSArray *)selectSysMeetingRoomAddressInfoWithRoomId:(NSString *)roomId;

//- (void)addSysMeetingRoomInfoWithDataArr:(NSArray *)dataArr;
- (void)addSysMeetingRoomInfoWithDataArr:(NSArray *)dataArr susscess:(void (^)(void))susscess faiure:(void (^)(void))faiure;

- (NSArray *)selectSysMeetingRoomInfoWithRoomId:(NSString *)roomId;

@end
