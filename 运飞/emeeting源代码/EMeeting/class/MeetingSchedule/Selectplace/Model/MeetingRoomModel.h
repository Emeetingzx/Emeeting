//
//  MeetingRoomModel.h
//  EMeeting
//
//  Created by efutureinfo on 16/3/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MeetingRoomModel : NSObject

@property (nonatomic, copy) NSString *iD;
@property (nonatomic, copy) NSString *pID;
@property (nonatomic, copy) NSString *addessChinese;
@property (nonatomic, copy) NSString *orderID;
@property (nonatomic, copy) NSString *levelId;
@property (nonatomic, copy) NSString *isValidId;
@property (nonatomic, copy) NSString *longAddessChinese;
@property (nonatomic, assign) BOOL isSelected;

-(id)initWithDictionary:(NSDictionary *)dict;

@end
