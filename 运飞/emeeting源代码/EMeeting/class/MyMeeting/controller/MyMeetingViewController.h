//
//  MyMeetingViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingInfo.h"
#import "GetUserRelevantMeetingInfo.h"
#import "MJRefresh.h"
#import "CustomAlertView.h"
#import "TouchRefreshView.h"
@interface MyMeetingViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,UIScrollViewDelegate,UIAlertViewDelegate,CustomAlertViewDelegate,TouchRefreshViewDelegate>
@property (weak, nonatomic) IBOutlet UIButton *myReservebtn;//我预定的

@property (weak, nonatomic) IBOutlet UIButton *myOrganizationalbtn;//我组织的
@property (weak, nonatomic) IBOutlet UIButton *myJoinbtn;//我参加的
@property (weak, nonatomic) IBOutlet UIButton *allMeetingBtn;//所有的会议
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *singViewLead;//标示条


@property (strong, nonatomic) UITableView *currentTableView;//当前tableview
@property (strong, nonatomic) NSMutableArray<MeetingInfo *> *myReserveArr;//我预定的数组
@property (strong, nonatomic)  PageRequestObject *myReservePage;//我预定的分页对象
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL myReserveisDown;


//我组织的
@property (strong, nonatomic) NSMutableArray<MeetingInfo *> *myOrganizationalArr;
@property (strong, nonatomic)  PageRequestObject *myOrganizationalPage;
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL myOrganizationalisDown;

//我参加的
@property (strong, nonatomic) NSMutableArray<MeetingInfo *> *myJoinArr;
@property (strong, nonatomic)  PageRequestObject *myJoinPage;
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL myJoinisDown;
@property (strong, nonatomic) TouchRefreshView *touchRefreshView;
@property (copy, nonatomic) NSString *meeingId;
@property (copy, nonatomic) NSString *meeingType;
@end
