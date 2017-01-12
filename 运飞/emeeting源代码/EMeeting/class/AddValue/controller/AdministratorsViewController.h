//
//  AdministratorsViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/18.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AdminAddValueInfo.h"
#import "GetAdminAddValueServiceInfos.h"
#import "CustomAlertView.h"
#import "TouchRefreshView.h"
@interface AdministratorsViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,UIScrollViewDelegate,UIAlertViewDelegate,CustomAlertViewDelegate,TouchRefreshViewDelegate>
@property (weak, nonatomic) IBOutlet UIButton *newsOrderBtn;//新订单

@property (weak, nonatomic) IBOutlet UIButton *receivedBtn;//已接订单
@property (weak, nonatomic) IBOutlet UIButton *finishBtn;//完成订单

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *singViewLead;//标示条

@property (strong, nonatomic) UITableView *currentTableView;//当前tableview
@property (strong, nonatomic) NSMutableArray<AdminAddValueInfo *> *newsOrderArr;//新订单数组
@property (strong, nonatomic)  PageRequestObject *newsOrderPage;//新订单分页对象
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL newsOrderisDown;


//已接订单
@property (strong, nonatomic) NSMutableArray<AdminAddValueInfo *> *receivedArr;
@property (strong, nonatomic)  PageRequestObject *receivedPage;
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL receivedisDown;

//完成订单
@property (strong, nonatomic) NSMutableArray<AdminAddValueInfo *> *finishArr;
@property (strong, nonatomic)  PageRequestObject *finishPage;
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL finishisDown;
@property (strong, nonatomic) TouchRefreshView *touchRefreshView;//没有数据时的刷新控件
@end
