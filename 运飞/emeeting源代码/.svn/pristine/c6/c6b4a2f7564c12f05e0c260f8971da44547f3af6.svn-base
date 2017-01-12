//
//  BaseNetworkingObject.m
//  RedPacket
//
//  Created by itp on 15/10/30.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "BaseNetworkingObject.h"

@implementation BaseNetworkingObject

- (id)initWithDictionary:(NSDictionary *)dict
{
    self = [super init];
    if (self) {
        
    }
    return self;
}

- (NSDictionary *)networkingDictionary
{
    return nil;
}

@end

@implementation NSArray(networking)

- (NSArray *)networkingArray
{
    NSMutableArray *array = [NSMutableArray array];
    for (BaseNetworkingObject *object in self)
    {
        [array addObject:[object networkingDictionary]];
    }
    return array;
}

@end

