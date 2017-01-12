//
//  MeetingScheduleTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingScheduleTableViewCell.h"
#import "Tools.h"

@implementation MeetingScheduleTableViewCell

- (void)awakeFromNib
{
    // Initialization code
    
    if (IPHONE6)
    {
        self.buttonWidthConstraint.constant = 70;
    }else if (IPHONE6P)
    {
        self.buttonWidthConstraint.constant = 80;
    }
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(void)setMeetingRoomInfo:(MeetingRoomInfo *)meetingRoomInfo
{
    _meetingRoomInfo = meetingRoomInfo;
    
    _meetingAdressLabel.text = meetingRoomInfo.meetingRoomName;
    
    _numberLabel.text = meetingRoomInfo.meetingRoomScale;
    
    if ([@"0" isEqualToString:meetingRoomInfo.projectorState])
    {
        _projectorImageView.image = [UIImage imageNamed:@"homepage_projector2"];
    }else
    {
        _projectorImageView.image = [UIImage imageNamed:@"homepage_projector"];
    }
    
    if ([@"0" isEqualToString:meetingRoomInfo.televisionState])
    {
        _televisionImageView.image = [UIImage imageNamed:@"homepage_television2"];
    }else
    {
        _televisionImageView.image = [UIImage imageNamed:@"homepage_television"];
    }
    
    if ([@"0" isEqualToString:meetingRoomInfo.phoneState])
    {
        _phoneImageView.image = [UIImage imageNamed:@"homepage_phone2"];
    }else
    {
       _phoneImageView.image = [UIImage imageNamed:@"homepage_phone"]; 
    }
    
    if (meetingRoomInfo.meetingRoomOccupancyRation.length > 0)
    {
        _presentView.percentValue = [meetingRoomInfo.meetingRoomOccupancyRation floatValue];
        
        _rationLabel.text = [NSString stringWithFormat:@"%d%%",(int)([meetingRoomInfo.meetingRoomOccupancyRation floatValue]*100)];
    }else
    {
        _presentView.percentValue = 0;
        _rationLabel.text = @"0%";
    }
}


- (IBAction)scheduleMeeting:(UIButton *)sender
{
    if ([self.delegate respondsToSelector:@selector(MeetingScheduleTableViewCell:didClickRow:)])
    {
        [self.delegate MeetingScheduleTableViewCell:self didClickRow:sender.tag - ScheduleButtonBeginTag];
    }
}
@end
