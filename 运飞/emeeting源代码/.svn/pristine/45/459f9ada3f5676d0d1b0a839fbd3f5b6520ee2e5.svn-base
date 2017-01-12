//
//  ReserveView.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FoodAndRefreshmentsInfo.h"
#import "TimeSelectView.h"
#import "AddValueRegionInfo.h"
typedef void(^selectTimeBlock)();
typedef void(^selectAddressBlock)();
@interface ReserveView : UIView<UICollectionViewDataSource,UICollectionViewDelegate,UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UILabel *regionLable;//
@property (weak, nonatomic) IBOutlet UITextField *detailedAddress;//详细地址
@property (weak, nonatomic) IBOutlet UITextField *iphoneNum;//联系电话
@property (weak, nonatomic) IBOutlet UIButton *timeBtn;//选择时间
@property (weak, nonatomic) IBOutlet UICollectionView *collectionView;//增值服务项
@property (strong, nonatomic) NSMutableArray<FoodAndRefreshmentsInfo *> *foodInfoArr;//服务项数组的数据源
@property (strong, nonatomic) NSMutableArray<FoodAndRefreshmentsInfo *> *selectFoodInfoArr;//选中的服务项数组的数据源
@property (copy, nonatomic) selectTimeBlock block;//弹出选择时间控件的block
@property (copy, nonatomic) selectAddressBlock addressBlock;//弹出选择地区控件的block
@property (nonatomic, strong) UITextField *selectText;//当前操作的输入框
@property (strong, nonatomic) NSString *timeSring;//当前时间

-(void)calculationCurrentlyTime;
@end
