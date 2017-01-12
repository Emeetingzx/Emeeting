//
//  FMDBManager.m
//  RedPacket
//
//  Created by efutureinfo on 15/11/20.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "FMDBManager.h"
#import "MeetingRoomModel.h"
#import "MeetingRoomInfo.h"

@implementation FMDBManager

+ (instancetype)shareInstance
{
    static FMDBManager *manager = nil;
    
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^
                  {
                      manager = [[FMDBManager alloc] init];
                  });
    return manager;
}

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        NSString *filePath = [NSHomeDirectory() stringByAppendingString:@"/Documents/EMeeting.db"];
        
        NSLog(@"filePath==%@",filePath);
        
        self.dataBase = [FMDatabase databaseWithPath:filePath];
        
        if (![self.dataBase open])
        {
            NSLog(@"数据库打开失败");
        }
    }
    return self;
}

-(void)creatTableOfDataBaseWithTableName:(NSString *)tableName dictionary:(NSDictionary *)dict
{
    NSMutableArray *arr = [[NSMutableArray alloc] init];
    
    for (NSString *key in dict)
    {
        NSString *str = [NSString stringWithFormat:@"%@ %@",key,dict[key]];
        [arr addObject:str];
    }
    
    NSString *dictString = [arr componentsJoinedByString:@","];
    
    NSString *keyString = [NSString stringWithFormat:@"create table if not exists %@(id integer primary key autoincrement,%@)",tableName,dictString];
    
    if (![self.dataBase executeUpdate:keyString])
    {
        NSLog(@"创建表失败");
    }
}

-(void)deleteTableDataOfDBWithName:(NSString *)name
{
    NSString *keyString = [NSString stringWithFormat:@"delete from %@",name];
    
    if(![self.dataBase executeUpdate:keyString])
    {
        NSLog(@"删除失败");
    }
}

-(void)deleteTableDataOfDBWithName:(NSString *)name andkeyString:(NSString *)key
{
    NSString *keyString = nil;
    
    if (key)
    {
        keyString = [NSString stringWithFormat:@"delete from %@ %@",name,key];
    }
    else
    {
        keyString = [NSString stringWithFormat:@"delete from %@",name];
    }
    
    if(![self.dataBase executeUpdate:keyString])
    {
        NSLog(@"删除失败");
    }
}

-(FMResultSet *)selectFromTableName:(NSString *)name
{
    NSString *keyString = [NSString stringWithFormat:@"select * from %@",name];
    
    return [self.dataBase executeQuery:keyString];
}

-(FMResultSet *)selectFromTableName:(NSString *)name andkeystring:(NSString *)key
{
    NSString *keyString = nil;
    
    if (key)
    {
        keyString = [NSString stringWithFormat:@"select * from %@ %@",name,key];
    }
    else
    {
        keyString = [NSString stringWithFormat:@"select * from %@",name];
    }
    //NSLog(@"keyStr == %@",keyString);
    
    return [self.dataBase executeQuery:keyString];
}

#pragma mark - 数据库操作,数据加入数据库
- (void)addSysMeetingRoomAddressInfoWithDataArr:(NSArray *)dataArr susscess:(void (^)(void))susscess faiure:(void (^)(void))faiure
{
    
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomAddress" dictionary:@{@"RID":@"text",@"PID":@"text",@"ASC":@"text",@"OID":@"text",@"LID":@"text",@"IVID":@"text",@"LASC":@"text"}];
    
    __weak typeof(self) wself = self;
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(queue, ^
    {
        for (NSDictionary *dict in dataArr)
        {
            NSArray *arr = [wself selectSysMeetingRoomAddressInfoWithRoomId:dict[@"ID"]];
            if (arr.count > 0)
            {
                continue;
            }
            
            if (![wself.dataBase executeUpdate:@"insert into SysMeetingRoomAddress(RID,PID,ASC,OID,LID,IVID,LASC) values(?,?,?,?,?,?,?)",dict[@"ID"],dict[@"PID"],dict[@"ASC"],dict[@"OID"],dict[@"LID"],dict[@"IVID"],dict[@"LASC"]])
            {
                NSLog(@"插入失败");
                               
                dispatch_sync(dispatch_get_main_queue(), ^()
                {
                    faiure();
                });
                return;
            }
        }
        dispatch_sync(dispatch_get_main_queue(), ^()
        {
            susscess();
        });
    });
}

- (void)addSysMeetingRoomInfoWithDataArr:(NSArray *)dataArr susscess:(void (^)(void))susscess faiure:(void (^)(void))faiure
{
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomInfo" dictionary:@{@"RID":@"text",@"MRN":@"text",@"IVID":@"text",@"MRS":@"text",@"PJS":@"text",@"TVS":@"text",@"PS":@"text"}];
    
    for (NSDictionary *dict in dataArr)
    {
        if (![self.dataBase executeUpdate:@"insert into SysMeetingRoomInfo(RID,MRN,IVID,MRS,PJS,TVS,PS) values(?,?,?,?,?,?,?)",dict[@"ID"],dict[@"MRN"],dict[@"IVID"],dict[@"MRS"],dict[@"PJS"],dict[@"TVS"],dict[@"PS"]])
        {
            NSLog(@"插入失败");
        }
    }
}


#pragma mark - 数据库操作,在数据库查找
- (NSArray *)selectSysMeetingRoomAddressInfoLevelId:(NSString *)LevelId andPID:(NSString *)pid
{
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomAddress" dictionary:@{@"RID":@"text",@"PID":@"text",@"ASC":@"text",@"OID":@"text",@"LID":@"text",@"IVID":@"text",@"LASC":@"text"}];
    
    FMResultSet *resultset = [self selectFromTableName:@"SysMeetingRoomAddress" andkeystring:[NSString stringWithFormat:@"where LID = %@ and PID = %@ and IVID = %@",LevelId,pid,@"1"]];
    
    NSMutableArray *arrM = [[NSMutableArray alloc] init];
    while ([resultset next])
    {
        MeetingRoomModel *roomAddress = [[MeetingRoomModel alloc] init];
        roomAddress.iD = [resultset stringForColumn:@"RID"];
        roomAddress.pID = [resultset stringForColumn:@"PID"];
        roomAddress.addessChinese = [resultset stringForColumn:@"ASC"];
        roomAddress.orderID = [resultset stringForColumn:@"OID"];
        roomAddress.levelId = [resultset stringForColumn:@"LID"];
        roomAddress.isValidId = [resultset stringForColumn:@"IVID"];
        roomAddress.longAddessChinese = [resultset stringForColumn:@"LASC"];
        
        [arrM addObject:roomAddress];
    }
    
    return arrM;
}

- (NSArray *)selectSysMeetingRoomAddressInfoWithRoomId:(NSString *)roomId
{
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomAddress" dictionary:@{@"RID":@"text",@"PID":@"text",@"ASC":@"text",@"OID":@"text",@"LID":@"text",@"IVID":@"text",@"LASC":@"text"}];
    
    FMResultSet *resultset = [self selectFromTableName:@"SysMeetingRoomAddress" andkeystring:[NSString stringWithFormat:@"where RID = %@ and IVID = %@",roomId,@"1"]];
    
    NSMutableArray *arrM = [[NSMutableArray alloc] init];
    while ([resultset next])
    {
        MeetingRoomModel *roomAddress = [[MeetingRoomModel alloc] init];
        roomAddress.iD = [resultset stringForColumn:@"RID"];
        roomAddress.pID = [resultset stringForColumn:@"PID"];
        roomAddress.addessChinese = [resultset stringForColumn:@"ASC"];
        roomAddress.orderID = [resultset stringForColumn:@"OID"];
        roomAddress.levelId = [resultset stringForColumn:@"LID"];
        roomAddress.isValidId = [resultset stringForColumn:@"IVID"];
        roomAddress.longAddessChinese = [resultset stringForColumn:@"LASC"];
        
        [arrM addObject:roomAddress];
    }
    
    return arrM;
}

- (NSArray *)selectSysMeetingRoomInfoWithRoomId:(NSString *)roomId
{
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomInfo" dictionary:@{@"RID":@"text",@"MRN":@"text",@"IVID":@"text",@"MRS":@"text",@"PJS":@"text",@"TVS":@"text",@"PS":@"text"}];
    
    FMResultSet *resultset = [self selectFromTableName:@"SysMeetingRoomInfo" andkeystring:[NSString stringWithFormat:@"where RID = %@",roomId]];
    
    NSMutableArray *arrM = [[NSMutableArray alloc] init];
    while ([resultset next])
    {
        MeetingRoomInfo *roomInfo = [[MeetingRoomInfo alloc] init];
        roomInfo.meetingRoomId = [resultset stringForColumn:@"RID"];
        roomInfo.meetingRoomName = [resultset stringForColumn:@"MRN"];
        roomInfo.meetingRoomScale = [resultset stringForColumn:@"MRS"];
        roomInfo.projectorState = [resultset stringForColumn:@"PJS"];
        roomInfo.televisionState = [resultset stringForColumn:@"TVS"];
        roomInfo.phoneState = [resultset stringForColumn:@"PS"];

        [arrM addObject:roomInfo];
    }
    
    return arrM;
}







@end
