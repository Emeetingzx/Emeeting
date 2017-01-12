//
//  AllMeetingViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RMCalendarLogic.h"
typedef void(^RefreshDataBlock)();
@interface AllMeetingViewController : UIViewController<UICollectionViewDataSource, UICollectionViewDelegate>

@property (weak, nonatomic) IBOutlet UILabel *dateTitle;//标题
@property (weak, nonatomic) IBOutlet UICollectionView *collectionView;
/**
 *  用于存放日期模型数组
 */
@property(nonatomic ,strong) NSMutableArray *calendarMonth;
/**
 *  逻辑处理
 */
@property(nonatomic ,strong) RMCalendarLogic *calendarLogic;

/**
 *  展示类型
 */
@property(nonatomic, assign) CalendarShowType type;

@property(nonatomic ,strong) UILabel *promptLabel;//显示所有会议的个数
@property(nonatomic ,strong) UIView *promptView;//显示所有会议的view
@property (copy, nonatomic) RefreshDataBlock block;//刷新上一页数据
@end
