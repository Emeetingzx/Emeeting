//
//  MeetingPlaceTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomAlertView.h"
#import "MeetingInfo.h"
typedef void (^RefreshBlock)();
@interface MeetingPlaceTableViewCell : UITableViewCell<UITableViewDelegate,UITableViewDataSource,UIAlertViewDelegate,CustomAlertViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic, copy) RefreshBlock refreshBlock;
@property (nonatomic, strong) NSArray *bookingsMeetingRoomArr;
@property (nonatomic, strong) UIView *view;
@property (strong, nonatomic)  MeetingInfo *meetingInfo;
@end
