//
//  AddValueViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FoodAndRefreshmentsInfo.h"
#import "ReserveView.h"
#import "ServeTimeSelectView.h"
#import "AddValueRegionInfo.h"
#import "AlertTableView.h"
#import "MyAddValueInfo.h"
#import "MyServicesTableViewCell.h"
#import "UIViewController+MMDrawerController.h"
#import "Tools.h"
#import "AdministratorsViewController.h"
#import "AlertTableView.h"
#import "GetFoodAndRefreshmentsInfos.h"
#import "GetAddValueServiceRegionInfos.h"
#import "serviceReserveData.h"
#import "ReserveAddValueService.h"
#import "GetMyAddValueServiceInfos.h"
#import "CustomAlertView.h"
#import "UIColor+LJFColor.h"
#import "AddValueServiceOperate.h"
#import "TouchRefreshView.h"
@interface AddValueViewController : UIViewController<UIScrollViewDelegate,UITableViewDelegate,UITableViewDataSource,ServeTimeSelectViewDelegate,UITextFieldDelegate,TouchRefreshViewDelegate>
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIButton *serviceReserveBtn;//服务预定
@property (weak, nonatomic) IBOutlet UIButton *myServicesBtn;//我的服务
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *singViewLead;
@property (strong, nonatomic) UITableView *myServicesTableView;
@property (strong, nonatomic) NSMutableArray<FoodAndRefreshmentsInfo *> *foodInfoArr;//服务项数组的数据源
@property (strong, nonatomic) NSMutableArray<AddValueRegionInfo *> *addValueRegionInfoArr;//服务地区的数据源
@property (strong, nonatomic) NSMutableArray<MyAddValueInfo *> *myAddValueInfoArr;//我的服务的数据源
@property (strong, nonatomic)  ReserveView  *reserveView;//
/*
 * 是否下拉标志，YES表示下拉刷新
 */
@property (nonatomic, assign) BOOL isDown;
/*
 * 分页参数对象
 */
@property (nonatomic, strong) PageRequestObject *p;
@property (weak, nonatomic) IBOutlet UIButton *allServicesBtn;//所有会议按钮
@property (strong, nonatomic) TouchRefreshView *touchRefreshView;//没有数据时的刷新控件
@property (copy, nonatomic) NSString *valueErverNotifyType;
@end
