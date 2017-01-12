//
//  MyMeetingTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MyMeetingTableViewCell.h"
#import "Help.h"
#import "Tools.h"
@implementation MyMeetingTableViewCell

- (void)awakeFromNib {
    // Initialization code
    self.unsubscribeBtn.layer.cornerRadius=4;
}

-(CGFloat)cellHeightServiceAddress:(NSString *)serviceAddress MeetingName:(NSString *) meetingName{
    
    //设置label的最大行数self.activityName.numberOfLines = 0;
    self.meetingName.numberOfLines = 2;
    self.meetingPlace.numberOfLines=2;
    CGFloat meetingNameHeight;
    CGFloat meetingPlaceHeight;
    //计算出自适应的高度
    meetingNameHeight=[Help heightOfString:meetingName font:self.meetingName.font width:APPW-20-70];
    meetingPlaceHeight=[Help heightOfString:serviceAddress font:self.meetingPlace.font width:APPW-28-94];
    if (meetingNameHeight>40) {
        meetingNameHeight=40;
    }
    NSLog(@"%f",meetingPlaceHeight+meetingNameHeight+125-30);
    return  meetingPlaceHeight+meetingNameHeight+128-30;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
