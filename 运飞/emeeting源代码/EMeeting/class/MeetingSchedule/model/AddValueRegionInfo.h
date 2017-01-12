//
//  AddValueRegionInfo.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AddValueRegionInfo : NSObject
@property (strong,nonatomic) NSString *addValueServiceRegionId;
@property (strong,nonatomic) NSString *addValueServiceRegionName;
-(instancetype)initWithDictionary:(NSDictionary *)dic;
@end
