//
//  MeetingDominateView.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/4/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <objc/runtime.h>
#import "ExtendView.h"
#import "MeetingJoinInfo.h"
typedef void(^RequestMeetingProlongBlock)();
typedef void(^RequestMeetingProlongInfoBlock)();
typedef void(^SkipMeetingDominateBlock)();
typedef void(^RequestMeetingOperationBlock)(NSString *,MeetingJoinInfo *);
@interface MeetingDominateView : UIView<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableview;
@property (weak, nonatomic) IBOutlet UITextField *textFieldView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *tableViewBottom;
@property (weak, nonatomic) IBOutlet UIButton *dominateBtn;
@property (nonatomic, strong) NSArray *meetingJoinInfoList;
@property (nonatomic, copy) NSString *meetingId;
@property (nonatomic, copy) ExtendView *extendView;
@property (nonatomic, copy) RequestMeetingProlongBlock requestMeetingProlongBlock;
@property (nonatomic, copy)RequestMeetingProlongInfoBlock requestMeetingProlongInfoBlock;
@property (nonatomic, copy) SkipMeetingDominateBlock skipMeetingDominateBlock;
@property (nonatomic, copy) RequestMeetingOperationBlock requestMeetingOperationBlock;
@property (nonatomic, strong) NSArray *meetingProLongList;
-(void)dominate:(NSArray *)meetingProLongList;
@end
