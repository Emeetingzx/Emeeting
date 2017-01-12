//
//  DefaultDataModel.m
//  EMeeting
//
//  Created by efutureinfo on 16/3/11.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "DefaultDataModel.h"


@implementation DefaultDataModel

+ (instancetype)shareInstance
{
    static DefaultDataModel *manager = nil;
    
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^
                  {
                      manager = [[DefaultDataModel alloc] init];
                  });
    return manager;
}

@end
