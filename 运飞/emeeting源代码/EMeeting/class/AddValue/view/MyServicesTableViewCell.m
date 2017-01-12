//
//  MyServicesTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MyServicesTableViewCell.h"
#import "Help.h"
#import "Tools.h"
#define btnColor  [UIColor colorWithRed:255/255.0 green:150/255.0 blue:0/255.0 alpha:1]
@implementation MyServicesTableViewCell

- (void)awakeFromNib {
    // Initialization code
    self.orderTakingBtn.layer.cornerRadius=4;
    self.rescindBtn.layer.cornerRadius=4;
}

-(void)cellStyle:(int) state{
    switch (state) {
            //我的增值服务页面
        case 0:
            self.service_headImage.hidden=YES;
            self.service_headlable.hidden=YES;
            self.orderTakingBtn.hidden=YES;
            self.rescindBtn.backgroundColor=btnColor;
            [self.rescindBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            break;
             //管理员完成订单页面
        case 1:
            self.orderTakingBtn.hidden=YES;
            self.rescindBtn.hidden=YES;
            self.serviceState.hidden=NO;
            self.rescindBtn.backgroundColor=[UIColor clearColor];
            [self.rescindBtn setTitleColor:btnColor forState:UIControlStateNormal];
            break;
            //管理员新订单和已接订单页面
        case 2:
            self.orderTakingBtn.hidden=NO;
            self.rescindBtn.hidden=NO;
            self.serviceState.hidden=YES;
            self.rescindBtn.backgroundColor=[UIColor clearColor];
            [self.rescindBtn setTitleColor:btnColor forState:UIControlStateNormal];
            break;

        default:
            break;
    }
    
}
-(CGFloat)cellHeight:(NSString *)serviceAddress{
    
    //设置label的最大行数self.activityName.numberOfLines = 0;
    self.placeLable.numberOfLines = 0;
    CGFloat placeLableHeight;
   
    //计算出自适应的高度
    placeLableHeight=[Help heightOfString:serviceAddress font:self.placeLable.font width:APPW-20-39];
    if (placeLableHeight>30) {
        placeLableHeight=30;
    }
    
    return  placeLableHeight+113-15;
}

-(CGFloat)cellHeight1:(NSString *)serviceAddress{
    
    //设置label的最大行数self.activityName.numberOfLines = 0;
    self.placeLable.numberOfLines = 0;
    CGFloat placeLableHeight;
    
    //计算出自适应的高度
    placeLableHeight=[Help heightOfString:serviceAddress font:self.placeLable.font width:APPW-20-39];
    if (placeLableHeight>30) {
        placeLableHeight=30;
    }
    
    return  placeLableHeight+135-15;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
