//
//  PhoneOrVideoMeetingDeailsView.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/4/15.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingInfo.h"
#import "MeetingDominateView.h"
typedef void (^UnsubscribeActionBlock)();
typedef void (^GotoScanCodeListBlock)();
typedef void (^RequestMeetingJoinInfoBlock)();
@interface PhoneOrVideoMeetingDeailsView : UIView<UITableViewDelegate,UITableViewDataSource>
@property(nonatomic,strong) UITableView *meetingTableView;
@property (weak, nonatomic) IBOutlet UIButton *meetingDetailsBtn;
@property (weak, nonatomic) IBOutlet UIButton *meetingDominateBtn;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *underlinewidth;//线条标示的位置
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *scrollViewTop;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *scrollViewBottom;
@property(nonatomic,strong) MeetingDominateView *meetingDominateView;
@property (strong, nonatomic)  UIButton *cancelBtn;
@property (strong, nonatomic) NSMutableArray *meetingDeails;//显示的会议信息
@property (strong, nonatomic)  MeetingInfo *meetingInfo;//会议对象
@property (nonatomic, copy) UnsubscribeActionBlock unsubscribeBlock;
@property (nonatomic, copy) GotoScanCodeListBlock  gotoScanCodeListBlock;
@property (nonatomic, copy) RequestMeetingProlongBlock requestMeetingProlongBlock;
@property (nonatomic, copy)RequestMeetingProlongInfoBlock requestMeetingProlongInfoBlock;
@property (nonatomic, copy) RequestMeetingJoinInfoBlock requestMeetingJoinInfoBlock;
@property (nonatomic, copy) SkipMeetingDominateBlock skipMeetingDominateBlock;
@property (nonatomic, copy) RequestMeetingOperationBlock requestMeetingOperationBlock;
-(void)initialView;
-(void)loadMeetingData:(MeetingInfo *)meetInfo;
-(void)hiddenCancelBtn;
@end
