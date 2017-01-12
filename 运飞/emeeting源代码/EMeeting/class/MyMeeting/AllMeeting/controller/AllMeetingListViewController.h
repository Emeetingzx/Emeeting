//
//  AllMeetingListViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GetUserRelevantMeetingInfo.h"
#import "MeetingInfo.h"
#import "CustomAlertView.h"
#import "RMCalendarModel.h"
#import "TouchRefreshView.h"
typedef void(^RefreshDataBlock)();
@interface AllMeetingListViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,CustomAlertViewDelegate,TouchRefreshViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UILabel *dateTitle;//标题
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL isDown;
/*
 * 分页参数对象
 */
@property (nonatomic, strong) PageRequestObject *p;
@property (strong, nonatomic) NSMutableArray<MeetingInfo *> *myAllAddValueInfoArr;//数据源
@property (strong, nonatomic) RMCalendarModel *dateModel;//点击的日期对象
@property (strong, nonatomic) TouchRefreshView *touchRefreshView;//没有数据时的刷新控件
@property (copy, nonatomic) RefreshDataBlock block;//刷新上一页的数据
@end
