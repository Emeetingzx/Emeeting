//
//  MyAddValueInfo.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MyAddValueInfo.h"

@implementation MyAddValueInfo
-(instancetype)initWithDictionary:(NSDictionary *)dic{
    if (self=[super init]) {
        _orderId=dic[@"ID"]?:@"";
        _orderNumber=dic[@"ON"]?:@"";
        _orderState=dic[@"OS"]?:@"";
        _serviceAddress=dic[@"SA"]?:@"";
        _serviceDate=dic[@"SD"]?:@"";
        _foodAndRefreshmentsInfoArr=[self foodInfoArr:dic];
        _actionItems=dic[@"AIS"]?:@"";
    }
    return self;
}

-(NSMutableArray *)foodInfoArr:(NSDictionary *)dic{
    
    NSMutableArray *arr=[[NSMutableArray alloc]init];
    if (dic[@"FARIS"]) {
        for (NSDictionary * dict in dic[@"FARIS"]) {
            FoodAndRefreshmentsInfo *foodAndRefreshmentsInfo=[[FoodAndRefreshmentsInfo alloc]initWithDictionary:dict];
            [arr addObject:foodAndRefreshmentsInfo];
        }
        
    }
    return arr;
}
@end
