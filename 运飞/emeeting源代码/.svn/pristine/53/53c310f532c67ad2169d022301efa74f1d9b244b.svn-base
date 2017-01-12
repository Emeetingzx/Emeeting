//
//  PhoneOrVideoMeetingDeailsViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingInfo.h"
#import "PhoneOrVideoMeetingDeailsView.h"
typedef void(^RefreshDataBlock)();
@interface PhoneOrVideoMeetingDeailsViewController : UIViewController<UIScrollViewDelegate>
//@property (strong, nonatomic)  UITableView *tableView;
//@property (weak, nonatomic)  NSString* emeetingId;//会议id
@property (strong, nonatomic) NSMutableArray *meetingDeails;//显示的会议信息
@property (copy, nonatomic)  NSString *meetingId;//会议对象
@property (strong, nonatomic) MeetingInfo *meetInfo;
@property (copy, nonatomic) RefreshDataBlock block;//刷新上一页数据
@property (strong, nonatomic) PhoneOrVideoMeetingDeailsView *pOrVideoDeailsView;
 @property (strong, nonatomic)  NSArray *meetingJoinInfoList;
@end
