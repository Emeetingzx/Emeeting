//
//  ContactsListTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ContactsListTableViewCell.h"

@implementation ContactsListTableViewCell

- (void)awakeFromNib {
    // Initialization code
    _headImage.layer.cornerRadius = 25;
    _headImage.layer.masksToBounds=YES;
    _invitationBtn.layer.cornerRadius=5;
    _invitationBtn.layer.masksToBounds=YES;
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
