//
//  MeetingInfoManager.m
//  EMeeting
//
//  Created by efutureinfo on 16/3/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingInfoManager.h"
#import "MeetingRoomInfo.h"

@implementation MeetingInfoManager

+ (instancetype)shareInstance
{
    static MeetingInfoManager *manager = nil;
    
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^
                  {
                      manager = [[MeetingInfoManager alloc] init];
                  });
    return manager;
}

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        NSString *filePath = [NSHomeDirectory() stringByAppendingString:@"/Documents/MeetingInfo.db"];
        
       // NSLog(@"filePath==%@",filePath);
        
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

- (void)addSysMeetingRoomInfoWithDataArr:(NSArray *)dataArr susscess:(void (^)(void))susscess faiure:(void (^)(void))faiure
{
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomInfo" dictionary:@{@"RID":@"text",@"MRN":@"text",@"IVID":@"text",@"MRS":@"text",@"PJS":@"text",@"TVS":@"text",@"PS":@"text"}];
    
    __weak typeof(self) wself = self;
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(queue, ^
    {
        for (NSDictionary *dict in dataArr)
        {
            if (![wself.dataBase executeUpdate:@"insert into SysMeetingRoomInfo(RID,MRN,IVID,MRS,PJS,TVS,PS) values(?,?,?,?,?,?,?)",dict[@"ID"],dict[@"MRN"],dict[@"IVID"],dict[@"MRS"],dict[@"PJS"],dict[@"TVS"],dict[@"PS"]])
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


- (NSArray *)selectSysMeetingRoomInfoWithRoomId:(NSString *)roomId
{
    [self creatTableOfDataBaseWithTableName:@"SysMeetingRoomInfo" dictionary:@{@"RID":@"text",@"MRN":@"text",@"IVID":@"text",@"MRS":@"text",@"PJS":@"text",@"TVS":@"text",@"PS":@"text"}];
    
    FMResultSet *resultset = [self selectFromTableName:@"SysMeetingRoomInfo" andkeystring:[NSString stringWithFormat:@"where RID = %@ and IVID = %@",roomId,@"1"]];
    
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
