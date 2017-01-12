//
//  SubAreaTableView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingRoomModel.h"

@class SubAreaTableView;

@protocol SubAreaTableViewDelegate <NSObject>

@optional
- (void)SubAreaTableView:(SubAreaTableView *)areaView didSelectedInfo:(MeetingRoomModel *)selectedInfo;

@end

@interface SubAreaTableView : UIView

@property (nonatomic, copy) NSString *pid;

@property (nonatomic, strong) UIView *supView;

@property (nonatomic, weak) id<SubAreaTableViewDelegate> delegate;

@property (nonatomic, copy) NSString *secondId;

@property (nonatomic, strong) MeetingRoomModel *defaultModel;

@end
