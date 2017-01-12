//
//  MyServicesTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MyServicesTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *service_headImage;
@property (weak, nonatomic) IBOutlet UILabel *service_headlable;
@property (weak, nonatomic) IBOutlet UILabel *serviceState;
@property (weak, nonatomic) IBOutlet UILabel *placeLable;
@property (weak, nonatomic) IBOutlet UILabel *serviceOrderNumberLable;

@property (weak, nonatomic) IBOutlet UILabel *timeLable;
@property (weak, nonatomic) IBOutlet UILabel *addValueLable;
@property (weak, nonatomic) IBOutlet UIButton *orderTakingBtn;

@property (weak, nonatomic) IBOutlet UIButton *rescindBtn;
-(void)cellStyle:(int) state;
-(CGFloat)cellHeight:(NSString *)serviceAddress;
-(CGFloat)cellHeight1:(NSString *)serviceAddress;
@end
