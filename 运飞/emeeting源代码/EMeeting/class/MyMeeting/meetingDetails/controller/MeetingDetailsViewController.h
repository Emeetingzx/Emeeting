//
//  MeetingDetailsViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingInfo.h"
#import <objc/runtime.h>
#import "MeetingDominateView.h"
#import "MeetingDetailsTableViewCell.h"
#import "MeetingPlaceTableViewCell.h"
#import "GetMeetingInfo.h"
#import "CancelMeetingRoom.h"
#import "EndMeetingRoom.h"
#import "CustomAlertView.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "ProgressView.h"
#import "MarkedWords.h"
#import "TouchRefreshView.h"
#import "MeetingRoomInfo.h"
#import "FMDBManager.h"
#import "MeetingInfoManager.h"
#import "MeetingDominateView.h"
#import "GetMeetingJoinInfo.h"
#import "ScanCodePersonnelViewController.h"
#import "MeetingJoinInfo.h"
#import "GetMeetingProlongInfo.h"
#import "GetMeetingProlong.h"
#import "MeetingProlong.h"
#import "MeetingOperation.h"
#import "MeetingDominateViewController.h"
#import "AttendanceOperation.h"
#import "NSDate+LJFDate.h"
typedef void(^RefreshDataBlock)();
@interface MeetingDetailsViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,UIAlertViewDelegate>
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *underlinewidth;//线条标示的位置
@property (weak, nonatomic) IBOutlet UIButton *meetingDetailBtn;//会议详情按钮
@property (strong, nonatomic)  UIButton *endBtn;//退订按钮
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *scrollBottom;

@property (weak, nonatomic) IBOutlet UIButton *boardroomDetailBtn;//会议室详情按钮
@property (weak, nonatomic) IBOutlet UIButton *meetingDominateBtn;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic)  NSString* emeetingId;//会议id

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *meetingDetailBtnWidth;
@property (strong, nonatomic)  MeetingInfo *meetingInfo;//会议对象
@property (copy, nonatomic) RefreshDataBlock block;//返回刷新上一页数据
@property(nonatomic,strong) MeetingDominateView *meetingDominateView;
 @property (strong, nonatomic)  NSArray *meetingJoinInfoList;
@end
