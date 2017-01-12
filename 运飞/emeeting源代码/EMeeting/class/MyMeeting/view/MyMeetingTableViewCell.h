//
//  MyMeetingTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MyMeetingTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *meetingImage;
@property (weak, nonatomic) IBOutlet UIButton *unsubscribeBtn;
@property (weak, nonatomic) IBOutlet UILabel *meetingName;
@property (weak, nonatomic) IBOutlet UILabel *meetingPlace;
@property (weak, nonatomic) IBOutlet UILabel *meetingTime;
@property (weak, nonatomic) IBOutlet UILabel *organizePeopleChineseName;
-(CGFloat)cellHeightServiceAddress:(NSString *)serviceAddress MeetingName:(NSString *) meetingName;
@end
