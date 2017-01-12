//
//  LocationTableView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingRoomModel.h"

@class LocationTableView;

@protocol LocationTableViewDelegate <NSObject>

@optional
- (void)locationTableView:(LocationTableView *)locationView didSelectMeetingRoomModel:(MeetingRoomModel *)selectLocationModel;

@end

@interface LocationTableView : UIView

@property (nonatomic, copy) NSString *pid;

@property (nonatomic, weak) id<LocationTableViewDelegate> delegate;

@property (nonatomic, copy) NSString *defaultId;

@end
